package Pera.Back.CU.CU18_CambiarContrasena;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Usuario;
import Pera.Back.Repositories.RepositorioAuthUsuario;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

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

        Random rand = new Random();
        int codigo = rand.nextInt(1000000);

        authUsuario.setVerificationCode(codigo);

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ogas.sebastian.5e@gmail.com", "gmvd xrcc arho dycz");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ogas.sebastian.5e@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(usuario.getMail()));
        message.setSubject("Cambio de contraseña de Pera");

        String msg = "<h1>Pera</h1>" +
                "<p>Alguien está intentando cambiar la contraseña de su cuenta de Pera. " +
                "Si es usted, ingrese el siguiente código. Si no, puede ignorar este correo.</p>" +
                "<h3>" + codigo +"</h3>";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
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
