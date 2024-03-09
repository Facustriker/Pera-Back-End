package Pera.Back.CU.CU22_RegistrarUsuario;

import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<String> register(@RequestBody DTORegisterRequest request) {
        try {
            String ret = experto.register(request);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/ingresarCodigo")
    public ResponseEntity<DTOAuthResponse> ingresarCodigo(@RequestParam String mail, @RequestParam int codigo){
        try {
            return ResponseEntity.ok(experto.ingresarCodigo(mail, codigo));
        } catch (Exception e) {
            DTOAuthResponse dto = new DTOAuthResponse();
            dto.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
    }
}
