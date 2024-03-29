package Pera.Back.CU.CU20_EmitirDinero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/EmitirDinero")
public class ControladorEmitirDinero {

    @Autowired
    protected ExpertoEmitirDinero experto;

    @GetMapping("/obtenerListaCB")
    public ResponseEntity<?> obtenerListaCB(@RequestParam("nroCB") Long nroCB) {
        try {
            DTOIDEMListaCB ret = experto.obtenerListaCB(nroCB);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/establecerDetalles")
    public ResponseEntity<?> establecerDetalles(@RequestBody DTOIDEMListaCB dto) {
        try {
            experto.establecerDetalles(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerDetalles")
    public ResponseEntity<?> obtenerDetalles() {
        try {
            DTOIDEMListaCB ret = experto.obtenerDetalles();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("valor") Boolean valor) {
        try {
            DTOIDEMListaCB ret = experto.confirmar(valor);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
