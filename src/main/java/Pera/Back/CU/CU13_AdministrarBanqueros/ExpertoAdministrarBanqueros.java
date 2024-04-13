package Pera.Back.CU.CU13_AdministrarBanqueros;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarBanqueros {

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;
    public DTOAdministrarBanqueros getDatosCB(Long idCB, String busqueda) throws Exception {
        CuentaBancaria dueno = getCB(idCB);
        Banco banco = dueno.getBanco();

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuarioActual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        checkDueno(usuarioActual, banco);

        DTOAdministrarBanqueros dto = DTOAdministrarBanqueros.builder()
                .nombreBanco(banco.getNombreBanco())
                .idCBDueno(idCB)
                .idBanco(banco.getId())
                .build();

        for (CuentaBancaria cuenta : repositorioCuentaBancaria.buscarCuentasVigentesPorBanco(banco, busqueda)) {
            DTOCuentasAdministrarBanqueros aux = DTOCuentasAdministrarBanqueros.builder()
                    .nombreUsuario(cuenta.getTitular().getNombreUsuario())
                    .nroCB(cuenta.getId())
                    .checked(cuenta.isEsBanquero())
                    .build();

            dto.getDetallesCuentas().add(aux);
        }

        

        return dto;
    }

    public void confirmar(boolean confirmacion, DTOAdministrarBanqueros dto) throws Exception {
        if(!confirmacion) return;

        CuentaBancaria dueno = getCB(dto.getIdCBDueno());

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuarioActual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        checkDueno(usuarioActual, dueno.getBanco());


        for (DTOCuentasAdministrarBanqueros cuenta : dto.getDetallesCuentas()) {
            CuentaBancaria cb = getCB(cuenta.getNroCB());
            cb.setEsBanquero(cuenta.isChecked());
            repositorioCuentaBancaria.save(cb);
        }

        CuentaBancaria cuentaReservadaDueno = null;
        boolean foundCBBanquero = false;

        for (CuentaBancaria cuenta : repositorioCuentaBancaria.obtenerCuentasBancariasVigentesPorUsuarioYBanco(dueno.getTitular(), dueno.getBanco())) {
            if (cuenta.isEsBanquero()) {
                foundCBBanquero = true;
                break;
            } else {
                if (cuentaReservadaDueno == null) cuentaReservadaDueno = cuenta;
            }
        }

        if(!foundCBBanquero) {
            cuentaReservadaDueno.setEsBanquero(true);
            repositorioCuentaBancaria.save(cuentaReservadaDueno);
        }

    }



    private CuentaBancaria getCB(Long id) throws Exception {
        Optional<CuentaBancaria> opt = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(id);
        if(opt.isEmpty()) throw new Exception("No se encontró una cuenta bancaria vigente con esa id");
        return opt.get();
    }

    private void checkDueno(Usuario u, Banco b) throws Exception {
        if (b.getDueno().getId().longValue() != u.getId().longValue()) throw new Exception("Solo el dueño del banco puede realizar esta acción");
    }
}
