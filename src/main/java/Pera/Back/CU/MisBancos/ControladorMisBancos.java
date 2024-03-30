package Pera.Back.CU.MisBancos;

import Pera.Back.Entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/MisBancos")
public class ControladorMisBancos {

    @Autowired
    protected ExpertoMisBancos experto;

    @GetMapping(value = "/bancos")
    public ResponseEntity<?> obtenerBancos(){
        try {
            return ResponseEntity.ok(experto.obtenerBancos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/{nroBanco}")
    public ResponseEntity<?> obtenerBanco(@PathVariable Long nroBanco){
        try {
            return ResponseEntity.ok(experto.obtenerDatosBanco(nroBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
