package Pera.Back.Auth;

import Pera.Back.CU.Entities.AuthUsuario;
import Pera.Back.CU.Entities.Rol;
import Pera.Back.CU.Entities.Usuario;
import Pera.Back.CU.Repositories.UsuarioRepository;
import Pera.Back.JWT.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = usuarioRepository.findByMail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        long timeNow = System.currentTimeMillis();

        Rol rol = Rol.builder()
                .nombreRol("user")
                .build();

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombre())
                .mail(request.getEmail())
                .fhaUsuario(new Date(timeNow))
                .build();

        AuthUsuario authUsuario = AuthUsuario.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        usuario.AgregarRol(rol);
        authUsuario.setUsuario(usuario);


        if("admin@gmail.com".equals(usuario.getMail())){
            Rol rolAdmin = Rol.builder()
                            .nombreRol("ADMIN")
                            .build();
            usuario.AgregarRol(rolAdmin);
        }else{
            Rol rolUser = Rol.builder()
                    .nombreRol("USER")
                    .build();
            usuario.AgregarRol(rolUser);
        }

        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();


    }
}

