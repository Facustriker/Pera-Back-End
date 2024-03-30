package Pera.Back.CU.MisBancos;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.BancoRepository;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.CuentaBancariaRepository;
import Pera.Back.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoMisBancos {

    @Autowired
    private final BancoRepository bancoRepository;

    @Autowired
    private final CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final ConfiguracionRolRepository configuracionRolRepository;

    public Collection<DTOMisBancos> obtenerBancos() throws Exception{

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        Collection<DTOMisBancos> dtos = bancoRepository.obtenerBancos(usuario);

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
        banquero = usuarioRepository.findById(banquero.getId()).get();

        Rol rolDueno = banquero.getRolActual();
        ArrayList<String> permisos = new ArrayList<>();
        for ( Permiso permiso : configuracionRolRepository.getPermisos(rolDueno) ) {
            permisos.add(permiso.getNombrePermiso());
        };

        Banco banco = bancoRepository.getBancoPorNumeroBanco(nroBanco);
        Collection<CuentaBancaria> cbs = cuentaBancariaRepository.obtenerCuentasBancariasVigentesPorUsuarioYBanco(banquero, banco);
        if (cbs.isEmpty()) {
            throw new Exception("No se encontró una cuenta bancaria con permiso para acceder a esta información");
        }
        CuentaBancaria cbBanquero = cbs.iterator().next();

        if(!cbBanquero.isEsBanquero() || !cbBanquero.isHabilitada()) {
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
        for (CuentaBancaria cuentaBancaria : cuentaBancariaRepository.getCuentasVigentesPorBanco(banco)) {
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
                .build();

        return dto;

    }

}
