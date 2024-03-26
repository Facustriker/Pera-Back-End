package Pera.Back.CU.Usuario;

import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Usuario;
import Pera.Back.Entities.UsuarioRol;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.UsuarioRepository;
import Pera.Back.Repositories.UsuarioRolRepository;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoUsuario {

    private final ConfiguracionRolRepository configuracionRolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioRepository usuarioRepository;

    public DTODatosUsuario getDatos() throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();

        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        usuario = usuarioRepository.findById(usuario.getId()).get();

        Collection<String> permisos = new ArrayList<>();

        for (Permiso permiso : configuracionRolRepository.getPermisos(usuario.getRolActual())) {
            permisos.add(permiso.getNombrePermiso());
        }

        UsuarioRol usuarioRol = usuarioRolRepository.getActualByUsuario(usuario);

        DTODatosUsuarioRol dtoRol = DTODatosUsuarioRol.builder()
                .nombre(usuarioRol.getRol().getNombreRol())
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
