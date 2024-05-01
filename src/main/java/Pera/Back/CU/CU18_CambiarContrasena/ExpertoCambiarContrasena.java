package Pera.Back.CU.CU18_CambiarContrasena;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Usuario;
import Pera.Back.Repositories.RepositorioAuthUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoCambiarContrasena {

    @Autowired
    private final RepositorioAuthUsuario repositorioAuthUsuario;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public void enviarCodigo(String mail) throws Exception {

        AuthUsuario authUsuario = getAuthUsuario(mail);

        Usuario usuario = authUsuario.getUsuario();

        if(usuario.getFhaUsuario().after(new Date()) || usuario.getFhbUsuario() != null && usuario.getFhaUsuario().before(new Date())) {
            throw new Exception("El usuario no se encuentra vigente");
        }

        authUsuario.setVerificationCode(123456);

        //Enviar código por mail
    }

    public void ingresarCodigo(String mail, int codigo) throws Exception {

        AuthUsuario authUsuario = getAuthUsuario(mail);

        if (authUsuario.getVerificationCode() != codigo) {
            throw new Exception("Código incorrecto. Inténtelo nuevamente");
        }
    }

    public void cambiarContrasena(String mail, int codigo, String contrasena) throws Exception {

        AuthUsuario authUsuario = getAuthUsuario(mail);

        if (authUsuario.getVerificationCode() != codigo) {
            throw new Exception("Código incorrecto. Inténtelo nuevamente");
        }

        authUsuario.setPassword(passwordEncoder.encode(contrasena));

        repositorioAuthUsuario.save(authUsuario);
    }

    private AuthUsuario getAuthUsuario(String mail) throws Exception {
        Optional<AuthUsuario> optAuth = repositorioAuthUsuario.findByUsername(mail);
        if (optAuth.isEmpty()) {
            throw new Exception("No se encontró al usuario");
        }

        AuthUsuario authUsuario = optAuth.get();

        if (!authUsuario.isEnabled()) {
            throw new Exception("El usuario no se encuentra habilitado");
        }

        return authUsuario;
    }
}
