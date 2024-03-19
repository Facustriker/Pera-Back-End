package Pera.Back.CU.CU22_RegistrarUsuario;

import Pera.Back.Entities.*;
import Pera.Back.JWT.JwtService;
import Pera.Back.Repositories.AuthUsuarioRepository;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.RolRepository;
import Pera.Back.Repositories.UsuarioRolRepository;
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

    private final AuthUsuarioRepository authUsuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final ConfiguracionRolRepository configuracionRolRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public String register(DTORegisterRequest request) throws Exception {
        Optional<AuthUsuario> prev = authUsuarioRepository.findByUsername(request.getEmail());
        if (prev.isPresent()){
            if (prev.get().isEnabled()) {
                throw new Exception("El mail ya está registrado a nombre de otro usuario");
            } else {
                authUsuarioRepository.delete(prev.get());
            }
        }

        long timeNow = System.currentTimeMillis();

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
            rol = rolRepository.obtenerRolPorNombre("Administrador del Sistema");
        }else{
            rol = rolRepository.obtenerRolPorNombre("No Premium");
        }
        usuario.setRolActual(rol);

        authUsuarioRepository.save(authUsuario);

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .fhaUsuarioRol(new Date(timeNow))
                .rol(rol)
                .usuario(usuario)
                .build();

        usuarioRolRepository.save(usuarioRol);


        return "";

    }

    public DTOAuthResponse ingresarCodigo(String username, int codigo) throws Exception {
        Optional<AuthUsuario> optAuthUsuario = authUsuarioRepository.findByUsername(username);
        if (!optAuthUsuario.isPresent()) {
            throw new Exception("No se encontró la solicitud de generación de cuenta");
        }
        AuthUsuario authUsuario = optAuthUsuario.get();
        if (authUsuario.getVerificationCode() != codigo) {
            throw new Exception("El código no coincide");
        }

        authUsuario.setEnabled(true);

        authUsuarioRepository.save(authUsuario);

        Collection<String> permisos = new ArrayList<>();

        for (Permiso permiso : configuracionRolRepository.getPermisos(authUsuario.getUsuario().getRolActual())) {
            permisos.add(permiso.getNombrePermiso());
        }

        return DTOAuthResponse.builder()
                .token(jwtService.getToken(authUsuario))
                .permisos(permisos)
                .build();
    }

}
