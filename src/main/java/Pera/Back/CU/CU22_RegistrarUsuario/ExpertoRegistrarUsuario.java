package Pera.Back.CU.CU22_RegistrarUsuario;

import Pera.Back.Entities.*;
import Pera.Back.JWT.JwtService;
import Pera.Back.Repositories.RepositorioAuthUsuario;
import Pera.Back.Repositories.RepositorioConfiguracionRol;
import Pera.Back.Repositories.RepositorioRol;
import Pera.Back.Repositories.RepositorioUsuarioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoRegistrarUsuario {

    private final RepositorioAuthUsuario repositorioAuthUsuario;
    private final RepositorioUsuarioRol repositorioUsuarioRol;
    private final RepositorioConfiguracionRol repositorioConfiguracionRol;
    private final RepositorioRol repositorioRol;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(DTORegisterRequest request) throws Exception {
        Optional<AuthUsuario> prev = repositorioAuthUsuario.findByUsername(request.getEmail());
        if (prev.isPresent()){
            if (prev.get().isEnabled()) {
                throw new Exception("El mail ya está registrado a nombre de otro usuario");
            } else {
                repositorioAuthUsuario.delete(prev.get());
            }
        }

        long timeNow = System.currentTimeMillis();

        if(request.getNombre().equals("")){
            throw new Exception("Debe ingresar un nombre");
        }

        if(request.getEmail().equals("")){
            throw new Exception("Debe ingresar un email");
        }

        if(request.getPassword().equals("")){
            throw new Exception("Debe ingresar una contrasena");
        }

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombre())
                .mail(request.getEmail())
                .fhaUsuario(new Date(timeNow))
                .build();

        int codigo = 123456;    //Generar código random y enviar por mail

        AuthUsuario authUsuario = AuthUsuario.builder()
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .usuario(usuario)
                .enabled(false)
                .verificationCode(codigo)
                .build();

        Rol rol;
        if("admin@gmail.com".equals(usuario.getMail())){
            rol = repositorioRol.obtenerRolPorNombre("Administrador del Sistema");
        }else{
            rol = repositorioRol.obtenerRolPorNombre("No Premium");
        }
        usuario.setRolActual(rol);

        repositorioAuthUsuario.save(authUsuario);

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .fhaUsuarioRol(new Date(timeNow))
                .rol(rol)
                .usuario(usuario)
                .build();

        repositorioUsuarioRol.save(usuarioRol);


        return "";

    }

    public DTOAuthResponse ingresarCodigo(String username, int codigo) throws Exception {
        Optional<AuthUsuario> optAuthUsuario = repositorioAuthUsuario.findByUsername(username);
        if (!optAuthUsuario.isPresent()) {
            throw new Exception("No se encontró la solicitud de generación de cuenta");
        }
        AuthUsuario authUsuario = optAuthUsuario.get();
        if (authUsuario.getVerificationCode() != codigo) {
            throw new Exception("El código no es correcto");
        }

        authUsuario.setEnabled(true);

        repositorioAuthUsuario.save(authUsuario);

        Collection<String> permisos = new ArrayList<>();

        for (Permiso permiso : repositorioConfiguracionRol.getPermisos(authUsuario.getUsuario().getRolActual())) {
            permisos.add(permiso.getNombrePermiso());
        }

        return DTOAuthResponse.builder()
                .token(jwtService.getToken(authUsuario))
                .permisos(permisos)
                .build();
    }

}
