package Pera.Back.CU.RegistrarUsuario;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Rol;
import Pera.Back.Entities.Usuario;
import Pera.Back.JWT.JwtService;
import Pera.Back.Repositories.AuthUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Scope("session")
public class ExpertoRegistrarUsuarioImpl {

    private final AuthUsuarioRepository authUsuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    DTORegisterRequest request;
    Integer codigo;

    public String register(DTORegisterRequest request) throws Exception {

        if (authUsuarioRepository.findByUsername(request.getEmail()).isPresent()){
            throw new Exception("El mail ya está registrado a nombre de otro usuario");
        }

        request = this.request;
        codigo = 123456;    //Generar código random y enviar por mail
        return "";

    }

    public DTOAuthResponse ingresarCodigo(int codigo) throws Exception {
        if (request == null) {
            throw new Exception("Error de entrada de datos");
        }
        if (this.codigo != codigo) {
            throw new Exception("El código no coincide");
        }
        long timeNow = System.currentTimeMillis();

        Rol rol = Rol.builder()
                .nombreRol("USER")
                .build();

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombre())
                .mail(request.getEmail())
                .fhaUsuario(new Date(timeNow))
                .build();

        usuario.setRol(rol);

        AuthUsuario authUsuario = AuthUsuario.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .usuario(usuario)
                .build();


        if("admin@gmail.com".equals(usuario.getMail())){
            Rol rolAdmin = Rol.builder()
                    .nombreRol("ADMIN")
                    .build();
            usuario.setRol(rolAdmin);
        }else{
            Rol rolUser = Rol.builder()
                    .nombreRol("USER")
                    .build();
            usuario.setRol(rolUser);
        }

        authUsuarioRepository.save(authUsuario);

        return DTOAuthResponse.builder()
                .token(jwtService.getToken(authUsuario))
                .build();
    }

}
