package Pera.Back.CU.Banco;

import Pera.Back.CU.MisBancos.DTOMisBancos;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import Pera.Back.Entities.Usuario;
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
public class ExpertoBanco {

    @Autowired
    private final BancoRepository bancoRepository;

    @Autowired
    private final CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final ConfiguracionRolRepository configuracionRolRepository;

    public DTOBanco obtenerDatosBanco(Long nroBanco) throws Exception{
        String estado = "Deshabilitado";
        String usaHabilitacionAutomatica = "No";
        String usaPassword = "Si";

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        dueno = usuarioRepository.findById(dueno.getId()).get();

        Rol rolDueno = dueno.getRolActual();
        ArrayList<String> permisos = new ArrayList<>();
        for ( Permiso permiso : configuracionRolRepository.getPermisos(rolDueno) ) {
            permisos.add(permiso.getNombrePermiso());
        };

        if(bancoRepository.obtenerIsHabilitadoPorNroBanco(nroBanco)){
            estado = "Habilitado";
        }

        if(bancoRepository.obtenerIsHabilitacionAutomaticaPorNroBanco(nroBanco)){
            usaHabilitacionAutomatica = "Si";
        }

        if(bancoRepository.obtenerPasswordPorNroBanco(nroBanco).equals("")){
            usaPassword = "No";
        }

        DTOBanco dto = DTOBanco.builder()
                .nroBanco(nroBanco)
                .nombre(bancoRepository.obtenerNombreBancoPorNroBanco(nroBanco))
                .estado(estado)
                .simboloMoneda(bancoRepository.obtenerSimboloMonedaPorNroBanco(nroBanco))
                .usaHabilitacionAutomatica(usaHabilitacionAutomatica)
                .usaPassword(usaPassword)
                .nombreUsuario(dueno.getNombreUsuario())
                .baseMonetaria(cuentaBancariaRepository.montoDineroPorNroBanco(nroBanco))
                .build();

        return dto;

    }


}
