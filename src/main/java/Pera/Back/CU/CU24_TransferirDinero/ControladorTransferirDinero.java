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
            experto.almacenarCBOrigen(nroCB);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ingresarCB")
    public ResponseEntity<?> ingresarCB(@RequestParam("nroCB") Long nroCB) {
        try {
            experto.ingresarCB(nroCB);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ingresarAlias")
    public ResponseEntity<?> ingresarAlias(@RequestParam("alias") String alias) {
        try {
            experto.ingresarAlias(alias);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDatosIngresarMonto")
    public ResponseEntity<?> getDatosIngresarMonto() {
        try {
            DTODatosIngresarMonto ret = experto.getDatosIngresarMonto();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ingresarMontoYMotivo")
    public ResponseEntity<?> ingresarMontoYMotivo(@RequestParam("montoMotivo") DTOMontoMotivo montoMotivo) {
        try {
            experto.ingresarMontoYMotivo(montoMotivo);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDatosConfirmacionTransferencia")
    public ResponseEntity<?> getDatosConfirmacionTransferencia() {
        try {
            DTOConfirmacionTransferencia ret = experto.getDatosConfirmacionTransferencia();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("confirmacion") Boolean confirmacion) {
        try {
            Long ret = experto.confirmar(confirmacion);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

