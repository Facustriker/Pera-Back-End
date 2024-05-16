package Pera.Back.CU.CU4_ABMConfiguracionRol;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMConfiguracionRol")
public class ControladorABMConfiguracionRol {

    @Autowired
    protected ExpertoABMConfiguracionRol experto;

    @GetMapping("/getConfiguraciones")
    public ResponseEntity<?> getConfiguraciones() {
        try {
            Collection<DTOABMConfiguracionRol> ret = experto.getConfiguraciones();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/altaConfiguracion")
    public ResponseEntity<?> altaConfiguracion() {
        try {
            DTODetallesAltaConfiguracionRol ret = experto.altaConfiguracion();
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestBody DTOAltaConfiguracionRol dto) {
        try {
            experto.confirmar(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDetalleConfiguracion/{nroConfig}")
    public ResponseEntity<?> getDetalleConfiguracion(@PathVariable Long nroConfig) {
        try {
            DTOAltaConfiguracionRol ret = experto.getDetalleConfiguracion(nroConfig);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
