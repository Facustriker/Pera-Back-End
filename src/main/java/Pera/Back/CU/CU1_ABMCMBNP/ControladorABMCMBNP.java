package Pera.Back.CU.CU1_ABMCMBNP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMCMBNP")
public class ControladorABMCMBNP {

    @Autowired
    protected ExpertoABMCMBNP experto;

    @GetMapping("/getCantidades")
    public ResponseEntity<?> getCantidadesBNP() {
        try {
            Collection<DTOABMCMBNP> ret = experto.getCantidadesBNP();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarBNP(@RequestBody DTOABMCMBNP dto) {
        try {
            experto.confirmarBNP(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
