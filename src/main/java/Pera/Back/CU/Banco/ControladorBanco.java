package Pera.Back.CU.Banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/MisBancos")
public class ControladorBanco {

    @Autowired
    protected ExpertoBanco experto;

    @GetMapping(value = "/obtenerBanco/{nroBanco}")
    public ResponseEntity<?> obtenerBancos(@PathVariable Long nroBanco){
        try {
            return ResponseEntity.ok(experto.obtenerDatosBanco(nroBanco));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
