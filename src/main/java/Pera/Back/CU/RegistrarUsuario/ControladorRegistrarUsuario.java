package Pera.Back.CU.RegistrarUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/RegistrarUsuario")
public class ControladorRegistrarUsuario {
    @Autowired
    protected ExpertoRegistrarUsuario experto;

    @PostMapping(value = "/register")
    public ResponseEntity<DTOAuthResponse> register(@RequestBody DTORegisterRequest request){
        return ResponseEntity.ok(experto.register(request));
    }
}
