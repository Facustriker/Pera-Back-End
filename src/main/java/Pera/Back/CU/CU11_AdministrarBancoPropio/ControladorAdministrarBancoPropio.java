package Pera.Back.CU.CU11_AdministrarBancoPropio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/AdministrarBancoPropio")
public class ControladorAdministrarBancoPropio {

    @Autowired
    ExpertoAdministrarBancoPropio experto;

    @GetMapping("/obtenerDatos/{idBanco}")
    public ResponseEntity<?> obtenerDatos(@PathVariable Long idBanco) {
        try {
            DTODatosBanco ret = experto.obtenerDatos(idBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody DTODatosBanco dto) {
        try {
            experto.modificar(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cambiarHabilitacion/{idBanco}")
    public ResponseEntity<?> cambiarHabilitacion(@PathVariable Long idBanco) {
        try {
            experto.cambiarHabilitacion(idBanco);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/baja/{idBanco}")
    public ResponseEntity<?> baja(@PathVariable Long idBanco) {
        try {
            experto.baja(idBanco);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
