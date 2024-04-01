package Pera.Back.CU.MisCuentasBancarias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/MisCuentasBancarias")
public class ControladorMisCuentasBancarias {

    @Autowired
    protected ExpertoMisCuentasBancarias experto;

    @GetMapping(value = "/cuentasBancarias")
    public ResponseEntity<?> obtenerCuentasBancarias(){
        try {
            return ResponseEntity.ok(experto.obtenerCuentasBancarias());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/cuentaBancariaPropia")
    public ResponseEntity<?> obtenerCuentaBancariaPropia(@RequestParam("id") Long id){
        try {
            return ResponseEntity.ok(experto.obtenerCuentaBancariaPropia(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
