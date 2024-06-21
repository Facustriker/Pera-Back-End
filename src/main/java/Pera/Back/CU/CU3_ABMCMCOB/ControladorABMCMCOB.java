package Pera.Back.CU.CU3_ABMCMCOB;

import Pera.Back.CU.CU2_ABMCMCBP.DTOABMCMCBP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMCMCOB")
public class ControladorABMCMCOB {

    @Autowired
    protected ExpertoABMCMCOB experto;

    @GetMapping("/getCantidades")
    public ResponseEntity<?> getCantidadesCOB() {
        try {
            Collection<DTOABMCMCOB> ret = experto.getCantidadesCOB();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCOB(@RequestBody DTOABMCMCOB dto) {
        try {
            experto.confirmarCOB(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
