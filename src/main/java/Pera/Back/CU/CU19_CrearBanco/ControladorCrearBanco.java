package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.CU.CU22_RegistrarUsuario.DTORegisterRequest;
import Pera.Back.Entities.Banco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/CrearBanco")
public class ControladorCrearBanco {
    @Autowired
    protected ExpertoCrearBanco experto;

    @PostMapping(value = "/crear")
    public ResponseEntity<String> crear(@RequestBody DTOCrearBanco request) {
        try {
            String ret = experto.crear(request);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
