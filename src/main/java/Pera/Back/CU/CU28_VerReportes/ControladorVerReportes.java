package Pera.Back.CU.CU28_VerReportes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/VerReportes")
public class ControladorVerReportes {
    @Autowired
    protected ExpertoVerReportes experto;

    @GetMapping("cuentasPorBanco")
    public ResponseEntity<?> cuentasPorBanco(@RequestParam("fecha") Long fecha, @RequestParam("intervalo") Long intervalo) {
        try {
            DTOHistograma ret = experto.cuentasPorBanco(new Date(fecha), intervalo);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
