package Pera.Back.CU.CU24_TransferirDinero;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Transferencia;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioTransferencia;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoTransferirDinero {

    @Autowired
    private final MemoriaTransferirDinero memoria;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioTransferencia repositorioTransferencia;

    public void almacenarCBOrigen(Long nroCB) throws Exception{
        memoria.setTransferencia(null);
        Optional<CuentaBancaria> cuentaOrigen = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        if (cuentaOrigen.isEmpty()) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }

        Transferencia transferencia = Transferencia.builder()
                .origen(cuentaOrigen.get())
                .build();

        memoria.setTransferencia(transferencia);
    }

    public void ingresarCB(Long nroCB) throws Exception{
        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos");
        }

        Optional<CuentaBancaria> cuentaDestino = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        if (cuentaDestino.isEmpty()) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }

        if(transferencia.getOrigen().getId().longValue() == cuentaDestino.get().getId().longValue()) {
            throw new Exception("No puede transferir a la misma cuenta");
        }
        
        transferencia.setDestino(cuentaDestino.get());
        memoria.setTransferencia(transferencia);

    }

    public void ingresarAlias(String alias) throws Exception{
        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos");
        }

        if (!alias.isEmpty()) {
            Optional<CuentaBancaria> cuentaDestino = repositorioCuentaBancaria.obtenerCuentaVigentePorAliasUsuario(alias);
            if (cuentaDestino.isEmpty()) {
                throw new Exception("Error: No se encontró la cuenta bancaria con alias: " + alias);
            }

            if (transferencia.getOrigen().getId().longValue() == cuentaDestino.get().getId().longValue()) {
                throw new Exception("No puede transferir a la misma cuenta");
            }
            transferencia.setDestino(cuentaDestino.get());
        } else {
            transferencia.setDestino(null);
        }



        memoria.setTransferencia(transferencia);
    }

    public DTODatosIngresarMonto getDatosIngresarMonto() throws Exception{

        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos");
        }
        
        String alias = transferencia.getDestino() != null ? transferencia.getDestino().getAlias() : "Banco " + transferencia.getOrigen().getBanco().getNombreBanco();
        Long nroCBDestino = transferencia.getDestino() != null ? transferencia.getDestino().getId() : null;

        DTODatosIngresarMonto dto = DTODatosIngresarMonto.builder()
                .aliasCuentaDestino(alias)
                .nroCuentaDestino(nroCBDestino)
                .build();

        return dto;
    }

    public void ingresarMontoYMotivo(DTOMontoMotivo montoMotivo) throws Exception{

        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos y de destino");
        }
        
        Double montoDineroOrigen = transferencia.getOrigen().getMontoDinero();
        Double montoIngresado = montoMotivo.getMonto();
        String motivoIngresado = montoMotivo.getMotivo();

        if(montoIngresado <= 0.0){
            throw new Exception("Error: El monto no puede ser negativo o 0");
        }

        if(montoIngresado > montoDineroOrigen){
            throw new Exception("Error: No tiene suficiente dinero disponible en la cuenta");
        }

        transferencia.setMontoTransferencia(montoIngresado);
        transferencia.setMotivo(motivoIngresado);
        
        memoria.setTransferencia(transferencia);

    }

    public DTOConfirmacionTransferencia getDatosConfirmacionTransferencia() throws Exception {

        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null || transferencia.getMontoTransferencia() == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos, de destino y el monto");
        }
        
        CuentaBancaria cuentaOrigen = transferencia.getOrigen();
        Long nroCBOrigen = cuentaOrigen.getId();
        CuentaBancaria cuentaDestino = transferencia.getDestino();
        Long nroCBDestino = cuentaDestino != null ? cuentaDestino.getId() : null;
        String motivo = transferencia.getMotivo();
        Double monto = transferencia.getMontoTransferencia();

        DTOConfirmacionTransferencia dto = DTOConfirmacionTransferencia.builder()
                .nroCBOrigen(nroCBOrigen)
                .nroCBDestino(nroCBDestino)
                .monto(monto)
                .motivo(motivo)
                .build();

        return dto;

    }

    public Long confirmar(Boolean confirmacion) throws Exception {
        if (!confirmacion) {
            memoria.setTransferencia(null);
            return null;
        }

        Transferencia transferencia = memoria.getTransferencia();
        if (transferencia == null || transferencia.getMontoTransferencia() == null) {
            throw new Exception("Ingrese la cuenta bancaria de origen de fondos, de destino y el monto");
        }
        
        transferencia.setAnulada(false);
        transferencia.setFhTransferencia(new Date());

        Double montoTransferencia = transferencia.getMontoTransferencia();
        Double montoDineroCBOrigen = transferencia.getOrigen().getMontoDinero();

        Optional<CuentaBancaria> optC = repositorioCuentaBancaria.findById(transferencia.getOrigen().getId());

        if(optC.isEmpty()) {
            memoria.setTransferencia(null);
            throw new Exception("La cuenta bancaria de origen no es válida");
        }

        transferencia.setOrigen(optC.get());

        if(transferencia.getDestino() != null) {
            optC = repositorioCuentaBancaria.findById(transferencia.getDestino().getId());

            if (optC.isEmpty()) {
                memoria.setTransferencia(null);
                throw new Exception("La cuenta bancaria de destino no es válida");
            }
            transferencia.setDestino(optC.get());
        }



        transferencia.getOrigen().setMontoDinero((montoDineroCBOrigen)-(montoTransferencia));
        if(transferencia.getDestino() != null) {
            Double montoDineroCBDestino = transferencia.getDestino().getMontoDinero();
            transferencia.getDestino().setMontoDinero((montoDineroCBDestino) + (montoTransferencia));
        }
        transferencia = repositorioTransferencia.save(transferencia);

        memoria.setTransferencia(null);

        return transferencia.getId();
    }
}


