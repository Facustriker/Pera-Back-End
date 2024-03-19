package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.CU.CU22_RegistrarUsuario.DTORegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/Banco")
public class ControladorCrearBanco {

    @Autowired
    protected ExpertoCrearBanco experto;

    @PostMapping(value = "/crearBanco")
    public ResponseEntity<String> register(@RequestBody DTOCrearBanco request) {
        try {
            String ret = experto.crear(request);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
