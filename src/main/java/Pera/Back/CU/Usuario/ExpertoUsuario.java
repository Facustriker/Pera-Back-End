package Pera.Back.CU.Usuario;

import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Usuario;
import Pera.Back.Entities.UsuarioRol;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.UsuarioRolRepository;
import Pera.Back.Singletons.SingletonObtenerUsuarioActual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoUsuario {

    private final ConfiguracionRolRepository configuracionRolRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    public DTODatosUsuario getDatos() throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();

        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        Collection<String> permisos = new ArrayList<>();

        for (Permiso permiso : configuracionRolRepository.getPermisos(usuario.getRolActual())) {
            permisos.add(permiso.getNombrePermiso());
        }

        UsuarioRol usuarioRol = usuarioRolRepository.getActualByUsuario(usuario);

        DTODatosUsuarioRol dtoRol = DTODatosUsuarioRol.builder()
                .nombre(usuario.getRolActual().getNombreRol())
                .permisos(permisos)
                .vencimiento(usuarioRol.getFhbUsuarioRol())
                .build();

        DTODatosUsuario dto = DTODatosUsuario.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombreUsuario())
                .email(usuario.getMail())
                .rol(dtoRol)
                .build();

        return dto;

    }

}
