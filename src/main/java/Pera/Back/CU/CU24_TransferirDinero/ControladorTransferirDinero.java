package Pera.Back.CU.CU24_TransferirDinero;


import Pera.Back.CU.CU20_EmitirDinero.DTOIDEMListaCB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/TransferirDinero")
public class ControladorTransferirDinero {

    @Autowired
    protected ExpertoTransferirDinero experto;

    @PostMapping("/{nroCB}")
    public ResponseEntity<?> almacenarCBOrigen(@RequestBody Long nroCB) {
        try {
            experto.recordarCBOrigen(nroCB);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerCB")
    public ResponseEntity<?> obtenerCB(@RequestParam("nroCB") Long nroCB) {
        try {
            DTOObtenerCB ret = experto.obtenerCB(nroCB);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerCBAlias")
    public ResponseEntity<?> obtenerCBAlias(@RequestParam("alias") String alias) {
        try {
            DTOObtenerCB ret = experto.obtenerCBAlias(alias);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/establecerDetalles")
    public ResponseEntity<?> establecerDetalles(@RequestBody DTODetallesTransferencia request) {
        try {
            experto.establecerDetalles(request);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerDetalles")
    public ResponseEntity<?> obtenerDetalles() {
        try {
            DTOConfirmacionTransferencia ret = experto.obtenerDetalles();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("valor") Boolean valor) {
        try {
            DTOConfirmacionTransferencia ret = experto.confirmar(valor);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

