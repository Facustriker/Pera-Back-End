package Pera.Back.CU.MisCuentasBancarias;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoMisCuentasBancarias {

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    public Collection<DTOMisCuentasBancarias> obtenerCuentasBancarias() throws Exception{

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        return repositorioCuentaBancaria.obtenerCuentasBancariasUsuario(usuario);

    }

    public DTOCuentaBancariaPropia obtenerCuentaBancariaPropia(Long id) throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        Optional<CuentaBancaria> ocb = repositorioCuentaBancaria.findById(id);

        if (!ocb.isPresent()) {
            throw new Exception("No se encontró la cuenta bancaria");
        }

        CuentaBancaria cb = ocb.get();
        if(cb.getTitular().getId().longValue() != usuario.getId().longValue()) {
            throw new Exception("Usted no es el titular de esta cuenta bancaria");
        }

        Banco banco = cb.getBanco();

        if (banco.getFhbBanco() != null && banco.getFhbBanco().before(new Date()) || !banco.getHabilitado()) {
            throw new Exception("El banco de esta cuenta bancaria ha sido dado de baja o está deshabilitado");
        }

        usuario = cb.getTitular();

        String estado = "";
        if (cb.getFhbCB() != null && cb.getFhbCB().after(new Date())) {
            estado = "Baja";
        } else if (cb.isHabilitada()) {
            estado = "Habilitada";
        } else {
            estado = "Deshabilitada";
        }

        String estadoBanco = "";
        if (banco.getFhbBanco() != null && banco.getFhbBanco().after(new Date())) {
            estadoBanco = "Baja";
        } else if (banco.getHabilitado()) {
            estadoBanco = "Habilitado";
        } else {
            estadoBanco = "Deshabilitado";
        }

        DTOCuentaBancariaPropia dto = DTOCuentaBancariaPropia.builder()
                .id(cb.getId())
                .nombreUsuario(usuario.getNombreUsuario())
                .monto(cb.getMontoDinero())
                .moneda(banco.getSimboloMoneda())
                .fha(cb.getFhaCB())
                .fhb(cb.getFhbCB())
                .estado(estado)
                .alias(cb.getAlias())
                .nroBanco(banco.getId())
                .nombreBanco(banco.getNombreBanco())
                .estadoBanco(estadoBanco)
                .build();

        return dto;
    }
}
