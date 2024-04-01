package Pera.Back.CU.CU15_AdministrarDatosDelUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/AdministrarDatosDelUsuario")
public class ControladorAdministrarDatosDelUsuario {

    @Autowired
    ExpertoAdministrarDatosDelUsuario experto;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        try {
            DTOAdminDatosUsuario ret = experto.get();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> get(@RequestBody DTOAdminDatosUsuario dto) {
        try {
            DTOAdminDatosUsuario ret = experto.modificar(dto);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
