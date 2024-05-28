package Pera.Back.CU.CU6_ABMMDP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMMDP")
public class ControladorABMMDP {

    @Autowired
    protected ExpertoABMMDP experto;

    @GetMapping("/getMediosPago")
    public ResponseEntity<?> getMediosPago() {
        try {
            Collection<DTOABMMDP> ret = experto.getMediosPago();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/altaMDP")
    public ResponseEntity<?> altaMDP(String nombreMDP) {
        try {
            experto.altaMDP(nombreMDP);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/darBajaMDP")
    public ResponseEntity<?> darBajaMDP(Long nroMDP) {
        try {
            experto.darBajaMDP(nroMDP);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
