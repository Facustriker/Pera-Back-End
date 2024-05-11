package Pera.Back.CU.CU9_ABMPSM;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMPSM")
public class ControladorABMPSM {

    @Autowired
    protected ExpertoABMPSM experto;

    @GetMapping("/getSimbolosMoneda")
    public ResponseEntity<?> getSimbolosMoneda() {
        try {
            Collection<DTOABMPSM> ret = experto.getSimbolosMoneda();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/altaPSM")
    public ResponseEntity<?> altaPSM(String simbolo) {
        try {
            experto.altaPSM(simbolo);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
