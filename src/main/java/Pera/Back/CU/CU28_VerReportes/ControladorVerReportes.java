package Pera.Back.CU.CU28_VerReportes;

import Pera.Back.CU.CU26_VerMovimientos.DTODetallesVerMovimientosSeleccionado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/VerReportes")
public class ControladorVerReportes {

    @Autowired
    protected ExpertoVerReportes experto;

    @GetMapping("/getCuentasPorBanco")
    public ResponseEntity<?> getCuentasPorBanco(@RequestParam("filtro") String filtro) {
        try {
            DTOCuentasBancos ret = experto.getCuentasPorBanco(filtro);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getBancosAbiertosCerrados")
    public ResponseEntity<?> getBancosAbiertosCerrados() {
        try {
            DTOCantBancosAbiertosCerrados ret = experto.getBancosAbiertosCerrados();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
