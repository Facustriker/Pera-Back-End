package Pera.Back.CU.CU20_EmitirDinero;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Emision;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.CuentaBancariaRepository;
import Pera.Back.Repositories.TransferenciaRepository;
import Pera.Back.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoEmitirDinero {

    private final MemoriaEmitirDinero memoria;

    private final CuentaBancariaRepository cuentaBancariaRepository;

    private final TransferenciaRepository transferenciaRepository;

    private final UsuarioRepository usuarioRepository;

    public DTOIDEMListaCB obtenerListaCB(Long nroCB) throws Exception {
        Optional<CuentaBancaria> optCB = cuentaBancariaRepository.findById(nroCB);
        if (!optCB.isPresent()) {
            String errorMessage = "No se encontró la cuenta bancaria del banquero";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }
        CuentaBancaria cuentaBanquero = optCB.get();
        memoria.setCuentaBanquero(cuentaBanquero);

        if (!cuentaBanquero.isEsBanquero()) {
            String errorMessage = "La cuenta bancaria no corresponde a un banquero";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }

        if (!cuentaBanquero.isHabilitada()) {
            String errorMessage = "La cuenta bancaria no se encuentra habilitada";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }

        if (cuentaBanquero.getFhbCB() != null && cuentaBanquero.getFhbCB().before(new Date())) {
            String errorMessage = "La cuenta bancaria ya ha sido dada de baja";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }

        if (cuentaBanquero.getFhaCB().after(new Date())) {
            String errorMessage = "La cuenta bancaria aún no ha sido dada de alta";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuarioActual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        if (cuentaBanquero.getTitular().getId().longValue() != usuarioActual.getId().longValue()) {
            String errorMessage = "Usted no es el titular de esta cuenta bancaria";
            memoria.setErrorMessage(errorMessage);
            throw new Exception(errorMessage);
        }

        memoria.setOk(true);

        Banco banco = cuentaBanquero.getBanco();
        
        DTOIDEMListaCB dto = DTOIDEMListaCB.builder()
                .nombreBanco(banco.getNombreBanco())
                .monto(0.0)
                .moneda(banco.getSimboloMoneda())
                .motivo("")
                .build();

        for (CuentaBancaria destino : cuentaBancariaRepository.getCuentasVigentesPorBanco(banco)) {
            Usuario usuario = destino.getTitular();

            DTOIDEMCB detalle = DTOIDEMCB.builder()
                    .nroCB(destino.getId())
                    .nombreUsuario(usuario.getNombreUsuario())
                    .mail(usuario.getMail())
                    .checked(false)
                    .build();

            dto.addDetalle(detalle);
        }

        return dto;
    }

    public void establecerDetalles(DTOIDEMListaCB dto) throws Exception {
        if (!memoria.isOk()) {
            throw new Exception(memoria.getErrorMessage());
        }

        dto.getDetalles().removeIf(d -> !d.isChecked());

        if(dto.getDetalles().isEmpty()) {
            throw new Exception("Debe seleccionar al menos una cuenta bancaria");
        }

        if (dto.getMonto() <= 0.0) {
            throw new Exception("Debe ingresar un monto positivo");
        }

        memoria.setDto(dto);
    }

    public DTOIDEMListaCB obtenerDetalles() throws Exception {
        if (!memoria.isOk()) {
            throw new Exception(memoria.getErrorMessage());
        }

        DTOIDEMListaCB dto = memoria.getDto();

        if (dto == null) {
            throw new Exception("No se pudo recuperar los detalles de la emisión en progreso");
        }

        return dto;

    }

    public DTOIDEMListaCB confirmar(Boolean valor) throws Exception {
        if (!valor) {
            memoria.setDto(null);
            memoria.setCuentaBanquero(null);
            return null;
        }

        if (!memoria.isOk()) {
            throw new Exception(memoria.getErrorMessage());
        }

        DTOIDEMListaCB dto = memoria.getDto();
        CuentaBancaria cuentaBanquero = memoria.getCuentaBanquero();

        Usuario banquero = cuentaBanquero.getTitular();
        banquero = usuarioRepository.findById(banquero.getId()).get();

        for (DTOIDEMCB detalle : dto.getDetalles()) {
            Emision emision = new Emision();

            emision.setResponsable(banquero);
            emision.setAnulada(false);
            emision.setMontoTransferencia(dto.getMonto());
            emision.setMotivo(dto.getMotivo());
            emision.setOrigen(null);

            Long nroCB = detalle.getNroCB();
            Optional<CuentaBancaria> optCB = cuentaBancariaRepository.getCuentaVigentePorNumeroCuenta(nroCB);
            if (!optCB.isPresent()) {
                throw new Exception("No se encontró la cuenta bancaria N.° " + nroCB);
            }
            CuentaBancaria destino = optCB.get();
            destino.setMontoDinero(destino.getMontoDinero() + dto.getMonto());

            emision.setFhTransferencia(new Date());

            destino = cuentaBancariaRepository.save(destino);
            emision.setDestino(destino);
            emision = transferenciaRepository.save(emision);

            detalle.setNroTransferencia(emision.getId());

        }

        return dto;

    }
}
