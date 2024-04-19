package Pera.Back.CU.MisBancos;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioBanco;
import Pera.Back.Repositories.RepositorioConfiguracionRol;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpertoMisBancos {

    @Autowired
    private final RepositorioBanco repositorioBanco;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    private final RepositorioConfiguracionRol repositorioConfiguracionRol;

    public Collection<DTOMisBancos> obtenerBancos() throws Exception{

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        Collection<DTOMisBancos> dtos = repositorioBanco.obtenerBancos(usuario);

        ArrayList<Long> ids = new ArrayList<>();

        dtos.removeIf(dto -> {
           if (ids.contains(dto.getId())) {
               return true;
           }
           ids.add(dto.getId());
           return false;
        });

        return dtos;

    }



    public DTOBanco obtenerDatosBanco(Long nroBanco) throws Exception{
        String estado = "Deshabilitado";
        String usaHabilitacionAutomatica = "No";
        String usaPassword = "Si";

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario banquero = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        banquero = repositorioUsuario.findById(banquero.getId()).get();

        Rol rolDueno = banquero.getRolActual();
        ArrayList<String> permisos = new ArrayList<>();
        for ( Permiso permiso : repositorioConfiguracionRol.getPermisos(rolDueno) ) {
            permisos.add(permiso.getNombrePermiso());
        };

        Banco banco = repositorioBanco.getBancoPorNumeroBanco(nroBanco);

        if (banco.getFhbBanco() != null && banco.getFhbBanco().before(new Date())) {
            throw new Exception("El banco ha sido dado de baja");
        }

        Collection<CuentaBancaria> cbs = repositorioCuentaBancaria.obtenerCuentasBancariasVigentesPorUsuarioYBanco(banquero, banco);
        if (cbs.isEmpty()) {
            throw new Exception("No se encontró una cuenta bancaria con permiso para acceder a esta información");
        }

        CuentaBancaria cbBanquero = null;
        for (CuentaBancaria cb : cbs) {
            if (cb.isEsBanquero() && cb.isHabilitada()) {
                cbBanquero = cb;
                break;
            }
        }

        if(cbBanquero == null) {
            throw new Exception("Debe ser un banquero habilitado para ver esta información");
        }

        if(banco.getHabilitado()){
            estado = "Habilitado";
        }

        if(banco.getHabilitacionAutomatica()){
            usaHabilitacionAutomatica = "Si";
        }

        if(banco.getPassword().isEmpty()){
            usaPassword = "No";
        }

        Double baseMonetaria = 0.0;
        for (CuentaBancaria cuentaBancaria : repositorioCuentaBancaria.getCuentasVigentesPorBanco(banco)) {
            baseMonetaria += cuentaBancaria.getMontoDinero();
        }

        DTOBanco dto = DTOBanco.builder()
                .nroBanco(banco.getId())
                .nombre(banco.getNombreBanco())
                .estado(estado)
                .simboloMoneda(banco.getSimboloMoneda())
                .usaHabilitacionAutomatica(usaHabilitacionAutomatica)
                .usaPassword(usaPassword)
                .nombreDueno(banco.getDueno().getNombreUsuario())
                .emailDueno(banco.getDueno().getMail())
                .baseMonetaria(baseMonetaria)
                .nroCB(cbBanquero.getId())
                .esDueno(banquero.getId().longValue() == banco.getDueno().getId().longValue())
                .build();

        return dto;

    }

}
