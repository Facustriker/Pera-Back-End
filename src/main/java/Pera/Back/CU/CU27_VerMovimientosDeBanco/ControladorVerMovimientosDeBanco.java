package Pera.Back.CU.CU27_VerMovimientosDeBanco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/VerMovimientosDeBanco")
public class ControladorVerMovimientosDeBanco {

    @Autowired
    protected ExpertoVerMovimientosDeBanco experto;

    @PostMapping("/filtrar/{idBanco}")
    public ResponseEntity<?> filtrar(@PathVariable("idBanco") Long idBanco, @RequestBody DTOFiltrosMovimientosBanco filtro) {
        try {
            DTOMovimientosBanco ret = experto.filtrar(idBanco, filtro);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDetalle/{nroTransferencia}")
    public ResponseEntity<?> getDetalle(@PathVariable Long nroTransferencia) {
        try {
            DTODetallesMovimientosBancoSeleccionado ret = experto.getDetalle(nroTransferencia);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/anular/{nroTransferencia}")
    public ResponseEntity<?> anular(@PathVariable Long nroTransferencia) {
        try {
            experto.anular(nroTransferencia);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
