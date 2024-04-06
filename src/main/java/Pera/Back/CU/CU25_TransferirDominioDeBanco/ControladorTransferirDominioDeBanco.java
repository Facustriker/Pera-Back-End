package Pera.Back.CU.CU25_TransferirDominioDeBanco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/TransferirDominioDeBanco")
public class ControladorTransferirDominioDeBanco {

    @Autowired
    ExpertoTransferirDominioDeBanco experto;

    @GetMapping("/obtenerPosiblesDuenos/{idBanco}")
    public ResponseEntity<?> obtenerPosiblesDuenos(@PathVariable Long idBanco) {
        try {
            DTOBancoTransferirDominio ret = experto.obtenerPosiblesDuenos(idBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obtenerUsuario/{idUsuario}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long idUsuario) {
        try {
            DTOUsuarioPosibleDueno ret = experto.obtenerUsuario(idUsuario);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("confirmacion") boolean confirmacion) {
        try {
            experto.confirmar(confirmacion);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
