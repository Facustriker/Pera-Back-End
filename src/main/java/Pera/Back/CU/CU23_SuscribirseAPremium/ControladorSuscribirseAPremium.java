package Pera.Back.CU.CU23_SuscribirseAPremium;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/SuscribirseAPremium")
public class ControladorSuscribirseAPremium {
    @Autowired
    protected ExpertoSuscribirseAPremium experto;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/obtenerPlanes")
    public ResponseEntity<?> obtenerPlanes(){
        try {
            return ResponseEntity.ok(experto.obtenerPlanes());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/obtenerMediosDePago/{idPlan}")
    public ResponseEntity<?> obtenerMediosDePago(@PathVariable Long idPlan){
        try {
            return ResponseEntity.ok(experto.obtenerMediosDePago(idPlan));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/realizarPago")
    public ResponseEntity<?> realizarPago(@RequestBody DTODatosPagoSuscripcionPremium dto){
        try {
            return ResponseEntity.ok(experto.realizarPago(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
