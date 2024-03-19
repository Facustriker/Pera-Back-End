package Pera.Back.CU.CU21_LoguearUsuario;

import Pera.Back.CU.CU22_RegistrarUsuario.DTOAuthResponse;
import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import Pera.Back.Functionalities.ActualizarRol.SingletonActualizarRol;
import Pera.Back.JWT.JwtService;
import Pera.Back.Repositories.AuthUsuarioRepository;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoLoguearUsuario {

    private final AuthUsuarioRepository authUsuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UsuarioRolRepository usuarioRolRepository;
    private final ConfiguracionRolRepository configuracionRolRepository;

    public DTOAuthResponse login(DTOLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = authUsuarioRepository.findByUsername(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);

        Collection<String> permisos = new ArrayList<>();

        SingletonActualizarRol singletonActualizarRol = SingletonActualizarRol.getInstancia();

        Rol rol = singletonActualizarRol.actualizarRol(usuarioRolRepository, ((AuthUsuario)user).getUsuario());

        for (Permiso permiso : configuracionRolRepository.getPermisos(rol)) {
            permisos.add(permiso.getNombrePermiso());
        }

        return DTOAuthResponse.builder()
                .token(token)
                .permisos(permisos)
                .build();
    }

}
