package Pera.Back.CU.CU14_AdministrarCuentaBancaria;

import Pera.Back.CU.MisBancos.ExpertoMisBancos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/AdministrarCuentaBancaria")
public class ControladorAdministrarCuentaBancaria {

    @Autowired
    protected ExpertoAdministrarCuentaBancaria experto;

    @GetMapping(value = "/{nroCB}")
    public ResponseEntity<?> obtenerCuentaBancaria(@PathVariable Long nroCB){
        try {
            return ResponseEntity.ok(experto.getCuentaBancaria(nroCB));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/modificar")
    public ResponseEntity<?> modificarCuenta(@RequestBody DTOAdministrarCuentaBancaria request){
        try {
            return ResponseEntity.ok(experto.modificarCuenta(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/darBaja")
    public ResponseEntity<?> darBajaCuenta(@RequestBody DTOAdministrarCuentaBancaria request){
        try {
            return ResponseEntity.ok(experto.darBajaCuenta(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
