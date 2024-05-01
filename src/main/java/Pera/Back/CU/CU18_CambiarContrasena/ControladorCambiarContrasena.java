package Pera.Back.CU.CU18_CambiarContrasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/CambiarContrasena")
public class ControladorCambiarContrasena {

    @Autowired
    protected ExpertoCambiarContrasena experto;

    @PostMapping("/enviarCodigo/{mail}")
    public ResponseEntity<?> enviarCodigo(@PathVariable String mail) {
        try {
            experto.enviarCodigo(mail);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ingresarCodigo/{codigo}")
    public ResponseEntity<?> ingresarCodigo(@PathVariable int codigo, @RequestParam("mail") String mail) {
        try {
            experto.ingresarCodigo(mail, codigo);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/cambiarContrasena/{contrasena}")
    public ResponseEntity<?> cambiarContrasena(@PathVariable String contrasena, @RequestParam("mail") String mail, @RequestParam("codigo") int codigo) {
        try {
            experto.cambiarContrasena(mail, codigo, contrasena);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
