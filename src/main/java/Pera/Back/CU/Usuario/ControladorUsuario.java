package Pera.Back.CU.Usuario;

import Pera.Back.CU.CU22_RegistrarUsuario.DTOAuthResponse;
import Pera.Back.CU.CU22_RegistrarUsuario.DTORegisterRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/Usuario")
public class ControladorUsuario {
    @Autowired
    protected ExpertoUsuario experto;

    @GetMapping(value = "/get")
    public ResponseEntity<?> getDatos() {
        try {
            return ResponseEntity.ok(experto.getDatos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
