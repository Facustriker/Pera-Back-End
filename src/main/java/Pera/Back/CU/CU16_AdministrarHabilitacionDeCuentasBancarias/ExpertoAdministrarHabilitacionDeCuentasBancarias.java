package Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioBanco;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarHabilitacionDeCuentasBancarias {

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioBanco repositorioBanco;

    public DTOAdministrarHabilitacionDeCuentasBancarias getDatosCuentasBancarias(Long idBanco, String filtro) throws Exception {

        Optional<Banco> bancoActual = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if(bancoActual.isEmpty()){
            throw new Exception("No se ha encontrado el banco seleccionado");
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuarioActual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        checkDueno(usuarioActual, bancoActual.get());

        if(Objects.equals(filtro, "")) {

            DTOAdministrarHabilitacionDeCuentasBancarias dto = DTOAdministrarHabilitacionDeCuentasBancarias.builder()
                    .nombreBanco(bancoActual.get().getNombreBanco())
                    .idBanco(idBanco)
                    .build();

            for(CuentaBancaria cuentaBancaria: repositorioCuentaBancaria.getCuentasVigentesPorBanco(bancoActual.get())){
                DTODetallesAdministrarHabilitacionCuentasBancarias aux = DTODetallesAdministrarHabilitacionCuentasBancarias.builder()
                        .nroCB(cuentaBancaria.getId())
                        .nombreTitular(cuentaBancaria.getTitular().getNombreUsuario())
                        .monto(cuentaBancaria.getMontoDinero())
                        .fechaBaja(cuentaBancaria.getFhbCB())
                        .habilitada(cuentaBancaria.isHabilitada())
                        .build();

                dto.getDetallesCuentas().add(aux);
            }
            return dto;
        }else{

            DTOAdministrarHabilitacionDeCuentasBancarias dto = DTOAdministrarHabilitacionDeCuentasBancarias.builder()
                    .nombreBanco(bancoActual.get().getNombreBanco())
                    .idBanco(idBanco)
                    .build();

            Optional<CuentaBancaria> cuentaFiltrada = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(Long.parseLong(filtro));

            if(cuentaFiltrada.isEmpty()){
                throw new Exception("No se ha encontrado la cuenta ingresada");
            }

            DTODetallesAdministrarHabilitacionCuentasBancarias detalle = DTODetallesAdministrarHabilitacionCuentasBancarias.builder()
                    .nroCB(cuentaFiltrada.get().getId())
                    .nombreTitular(cuentaFiltrada.get().getTitular().getNombreUsuario())
                    .monto(cuentaFiltrada.get().getMontoDinero())
                    .fechaBaja(cuentaFiltrada.get().getFhbCB())
                    .habilitada(cuentaFiltrada.get().isHabilitada())
                    .build();

            dto.getDetallesCuentas().add(detalle);

            return dto;
        }
    }

    public void confirmar(boolean confirmacion, DTOAdministrarHabilitacionDeCuentasBancarias dto) throws Exception {
        if(!confirmacion) return;

        Optional<Banco> bancoActual = repositorioBanco.getBancoPorNumeroBanco(dto.getIdBanco());

        if(bancoActual.isEmpty()){
            throw new Exception("No se ha encontrado el banco seleccionado");
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuarioActual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        checkDueno(usuarioActual, bancoActual.get());

        for (DTODetallesAdministrarHabilitacionCuentasBancarias cuenta : dto.getDetallesCuentas()) {
            CuentaBancaria cb = getCB(cuenta.getNroCB());
            cb.setHabilitada(cuenta.isHabilitada());
            repositorioCuentaBancaria.save(cb);
        }

        CuentaBancaria cuentaReservadaDueno = null;
        boolean foundCB = false;

        for (CuentaBancaria cuenta : repositorioCuentaBancaria.obtenerCuentasBancariasPorUsuarioYBanco(usuarioActual, bancoActual.get())) {
            if (cuenta.isEsBanquero() && cuenta.isHabilitada()) {
                foundCB = true;
                break;
            } else {
                if (cuentaReservadaDueno == null) cuentaReservadaDueno = cuenta;
            }
        }

        if(!foundCB) {
            cuentaReservadaDueno.setEsBanquero(true);
            cuentaReservadaDueno.setHabilitada(true);
            repositorioCuentaBancaria.save(cuentaReservadaDueno);
        }
    }

    private void checkDueno(Usuario u, Banco b) throws Exception {
        if (b.getDueno().getId().longValue() != u.getId().longValue()) throw new Exception("Solo el dueño del banco puede realizar esta acción");
    }

    private CuentaBancaria getCB(Long id) throws Exception {
        Optional<CuentaBancaria> opt = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(id);
        if(opt.isEmpty()) throw new Exception("No se encontró una cuenta bancaria vigente con esa id");
        return opt.get();
    }

}
