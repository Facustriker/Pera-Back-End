package Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias;

import Pera.Back.CU.CU24_TransferirDinero.DTODatosIngresarMonto;
import Pera.Back.CU.CU24_TransferirDinero.DTOMontoMotivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/AdministrarHabilitacionDeCuentasBancarias")
public class ControladorAdministrarHabilitacionDeCuentasBancarias {

    @Autowired
    protected ExpertoAdministrarHabilitacionDeCuentasBancarias experto;

    @GetMapping("/{idBanco}")
    public ResponseEntity<?> getDatosCuentasBancarias(@PathVariable Long idBanco, @RequestParam String filtro) {
        try {
            DTOAdministrarHabilitacionDeCuentasBancarias ret = experto.getDatosCuentasBancarias(idBanco, filtro);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam boolean confirmacion, @RequestBody DTOAdministrarHabilitacionDeCuentasBancarias dto) {
        try {
            experto.confirmar(confirmacion, dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
