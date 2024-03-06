package Pera.Back.CU.LoguearUsuario;

import Pera.Back.CU.RegistrarUsuario.DTOAuthResponse;
import Pera.Back.JWT.JwtService;
import Pera.Back.Repositories.AuthUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertoLoguearUsuarioImpl implements ExpertoLoguearUsuario {

    private final AuthUsuarioRepository authUsuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public DTOAuthResponse login(DTOLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = authUsuarioRepository.findByUsername(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return DTOAuthResponse.builder()
                .token(token)
                .build();
    }

}
