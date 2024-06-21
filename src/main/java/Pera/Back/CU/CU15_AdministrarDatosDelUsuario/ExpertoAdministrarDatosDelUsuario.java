package Pera.Back.CU.CU15_AdministrarDatosDelUsuario;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioAuthUsuario;
import Pera.Back.Repositories.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarDatosDelUsuario {

    private final RepositorioUsuario repositorioUsuario;

    private final RepositorioAuthUsuario repositorioAuthUsuario;

    public DTOAdminDatosUsuario get() throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        Optional<Usuario> optUs = repositorioUsuario.findById(usuario.getId());
        if(optUs.isEmpty()) {
            throw new Exception("No se encontró al usuario actual");
        }
        usuario = optUs.get();

        return DTOAdminDatosUsuario.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombreUsuario())
                .email(usuario.getMail())
                .build();
    }

    public DTOAdminDatosUsuario modificar(DTOAdminDatosUsuario dto) throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        if (dto.getId().longValue() != usuario.getId().longValue()) {
            throw new Exception("No puede modificar los datos de otro usuario");
        }

        usuario = repositorioUsuario.findById(usuario.getId()).get();
        if (dto.getNombre().isEmpty()) {
            throw new Exception("Debe ingresar su nombre");
        }

        if (dto.getEmail().isEmpty()) {
            throw new Exception("Debe ingresar su mail");
        }

        if(!Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*" +
                        "@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(dto.getEmail())
                .matches()) {
            throw new Exception("Email inválido");
        }

        Optional<AuthUsuario> prev = repositorioAuthUsuario.findByUsername(dto.getEmail());
        if (prev.isPresent()){
            if (prev.get().getUsuario().getId().longValue() != dto.getId().longValue()) {
                if (prev.get().isEnabled()) {
                    throw new Exception("El mail ya está registrado a nombre de otro usuario");
                } else {
                    repositorioAuthUsuario.delete(prev.get());
                }
            }
        }

        Optional<AuthUsuario> optAuth = repositorioAuthUsuario.findByUsuario(usuario);
        if(optAuth.isEmpty()) {
            throw new Exception("No se encontró al usuario actual");
        }
        AuthUsuario auth = optAuth.get();

        usuario.setNombreUsuario(dto.getNombre());
        usuario.setMail(dto.getEmail());

        auth.setUsername(dto.getEmail());

        repositorioAuthUsuario.save(auth);

        return dto;

    }
}
