package Pera.Back.CU.CU7_ABMPermiso;

import Pera.Back.CU.CU8_ABMRol.DTOABMRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/ABMPermiso")
public class ControladorABMPermiso {

    @Autowired
    protected ExpertoABMPermiso experto;

    @GetMapping("/getPermisos")
    public ResponseEntity<?> getPermisos() {
        try {
            Collection<DTOABMPermiso> ret = experto.getPermisos();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
