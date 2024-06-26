package Pera.Back.CU.CU21_LoguearUsuario;

import Pera.Back.CU.CU22_RegistrarUsuario.DTOAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/LoguearUsuario")
public class ControladorLoguearUsuario {
    @Autowired
    protected ExpertoLoguearUsuario experto;

    @PostMapping(value = "/login")
    public ResponseEntity<DTOAuthResponse> login(@RequestBody DTOLoginRequest request){
        try {
            return ResponseEntity.ok(experto.login(request));
        } catch (Exception e) {
            DTOAuthResponse dto = new DTOAuthResponse();
            dto.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
    }
}
