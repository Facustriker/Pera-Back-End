package Pera.Back.CU.CU26_VerMovimientos;

import Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias.DTOAdministrarHabilitacionDeCuentasBancarias;
import Pera.Back.CU.CU27_VerMovimientosDeBanco.DTODetallesMovimientosBancoSeleccionado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/VerMovimientos")
public class ControladorVerMovimientos {

    @Autowired
    protected ExpertoVerMovimientos experto;

    @PostMapping("/{nroCB}")
    public ResponseEntity<?> getMovimientosCuentas(@PathVariable("nroCB") Long nroCB, @RequestBody DTOFiltrosVerMovimientos dtoFiltros) {
        try {
            DTOVerMovimientos ret = experto.getMovimientosCuentas(nroCB, dtoFiltros);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDetalle/{nroTransferencia}")
    public ResponseEntity<?> getDetalle(@PathVariable Long nroTransferencia) {
        try {
            DTODetallesVerMovimientosSeleccionado ret = experto.getDetalle(nroTransferencia);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
