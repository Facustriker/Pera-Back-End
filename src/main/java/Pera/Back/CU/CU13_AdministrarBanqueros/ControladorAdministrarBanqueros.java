package Pera.Back.CU.CU13_AdministrarBanqueros;

import Pera.Back.CU.CU11_AdministrarBancoPropio.DTODatosBanco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/AdministrarBanqueros")
public class ControladorAdministrarBanqueros {

    @Autowired
    ExpertoAdministrarBanqueros experto;

    @GetMapping("/{idCB}")
    public ResponseEntity<?> getDatosCB(@PathVariable Long idCB, @RequestParam String  busqueda) {
        try {
            DTOAdministrarBanqueros ret = experto.getDatosCB(idCB, busqueda);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> getDatosCB(@RequestParam boolean confirmacion, @RequestBody DTOAdministrarBanqueros dto) {
        try {
            experto.confirmar(confirmacion, dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
