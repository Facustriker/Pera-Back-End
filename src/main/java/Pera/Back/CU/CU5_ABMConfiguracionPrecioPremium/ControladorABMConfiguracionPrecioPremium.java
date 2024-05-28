package Pera.Back.CU.CU5_ABMConfiguracionPrecioPremium;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMConfiguracionPrecioPremium")
public class ControladorABMConfiguracionPrecioPremium {

    @Autowired
    protected ExpertoABMConfiguracionPrecioPremium experto;

    @GetMapping("/getConfiguraciones")
    public ResponseEntity<?> getConfiguraciones() {
        try {
            Collection<DTOABMCPP> ret = experto.getConfiguraciones();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/altaConfiguracion")
    public ResponseEntity<?> altaConfiguracion(@RequestBody DTODetallesCPP dto) {
        try {
            experto.altaConfiguracion(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDetalleConfiguracion/{nroConfig}")
    public ResponseEntity<?> getDetalleConfiguracion(@PathVariable Long nroConfig) {
        try {
            DTODetallesCPP ret = experto.getDetalleConfiguracion(nroConfig);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
