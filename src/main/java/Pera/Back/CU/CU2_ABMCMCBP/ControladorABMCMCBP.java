package Pera.Back.CU.CU2_ABMCMCBP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMCMCBP")
public class ControladorABMCMCBP {

    @Autowired
    protected ExpertoABMCMCBP experto;

    @GetMapping("/getCantidades")
    public ResponseEntity<?> getCantidadesCBP() {
        try {
            Collection<DTOABMCMCBP> ret = experto.getCantidadesCBP();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCBP(@RequestBody DTOABMCMCBP dto) {
        try {
            experto.confirmarCBP(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
