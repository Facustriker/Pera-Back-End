package Pera.Back.CU.CU8_ABMRol;

import Pera.Back.CU.CU9_ABMPSM.DTOABMPSM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMRol")
public class ControladorABMRol {

    @Autowired
    protected ExpertoABMRol experto;

    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles() {
        try {
            Collection<DTOABMRol> ret = experto.getRoles();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/altaRol")
    public ResponseEntity<?> altaRol(String nombreRol) {
        try {
            experto.altaRol(nombreRol);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/modificarRol/{nroRol}")
    public ResponseEntity<?> getRol(@PathVariable Long nroRol){
        try {
            DTOModificarRol ret = experto.getRol(nroRol);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestBody DTOModificarRol dto) {
        try {
            experto.confirmar(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/darBaja")
    public ResponseEntity<?> darBaja(Long nroRol) {
        try {
            experto.darBaja(nroRol);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
