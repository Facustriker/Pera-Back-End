package Pera.Back.CU.LoguearUsuario;

import Pera.Back.CU.RegistrarUsuario.DTOAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/LoguearUsuario")
public class ControladorLoguearUsuario {
    @Autowired
    protected ExpertoLoguearUsuario experto;

    @CrossOrigin(origins = "*")
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
