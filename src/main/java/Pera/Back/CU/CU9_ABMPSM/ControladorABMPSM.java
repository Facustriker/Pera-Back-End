package Pera.Back.CU.CU9_ABMPSM;

import Pera.Back.CU.CU28_VerReportes.DTOCuentasBancos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ABMPSM")
public class ControladorABMPSM {

    @Autowired
    protected ExpertoABMPSM experto;

    @GetMapping("/getSimbolosMoneda")
    public ResponseEntity<?> getSimbolosMoneda() {
        try {
            DTOABMPSM ret = experto.getSimbolosMoneda();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
