package Pera.Back.CU.CU14_AdministrarCuentaBancaria;

import Pera.Back.CU.CU19_CrearBanco.DTOCrearBanco;
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
public class ExpertoAdministrarCuentaBancaria {

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    public DTOAdministrarCuentaBancaria getCuentaBancaria(Long nroCB) throws Exception{
        CuentaBancaria cuenta = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);

        DTOAdministrarCuentaBancaria dto = DTOAdministrarCuentaBancaria.builder()
                .nroCB(cuenta.getId())
                .alias(cuenta.getAlias())
                .build();

        return dto;
    }


    public Long modificarCuenta(DTOAdministrarCuentaBancaria request) throws Exception{

        Optional<CuentaBancaria> entidad = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(request.getNroCB());
        Collection<String> aliasCuentas = repositorioCuentaBancaria.obtenerAliasCuentasBancariasVigentes();
        CuentaBancaria cuenta = entidad.get();

        for(String alias: aliasCuentas){
            if (alias.equals(request.getAlias())) {
                throw new Exception("Error, el alias ingresado ya existe");
            }
        }

        cuenta.setAlias(request.getAlias());

        repositorioCuentaBancaria.save(cuenta);

        return cuenta.getId();

    }

    public Long darBajaCuenta(DTOAdministrarCuentaBancaria request) throws Exception{

        Optional<CuentaBancaria> entidad = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(request.getNroCB());
        CuentaBancaria cuenta = entidad.get();

        cuenta.setFhbCB(new Date());
        cuenta.setHabilitada(false);

        repositorioCuentaBancaria.save(cuenta);

        return cuenta.getId();

    }


}
