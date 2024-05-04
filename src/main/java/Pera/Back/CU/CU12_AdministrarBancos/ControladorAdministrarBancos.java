package Pera.Back.CU.CU12_AdministrarBancos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/AdministrarBancos")
public class ControladorAdministrarBancos {

    @Autowired
    protected ExpertoAdministrarBancos experto;

    @PostMapping("/bancos")
    public ResponseEntity<?> getBancos(@RequestBody DTOFiltrosBancos filtros) {
        try {
            Collection<DTOAdministrarBancos> ret = experto.getBancos(filtros);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/banco/{id}")
    public ResponseEntity<?> getBanco(@PathVariable("id") Long id) {
        try {
            DTODetalleBanco ret = experto.getBanco(id);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/banco")
    public ResponseEntity<?> modificarBanco(@RequestBody DTODetalleBanco dto) {
        try {
            experto.modificarBanco(dto);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {"/cuentas", "/cuentas/{nroCB}"})
    public ResponseEntity<?> getCuentas(@PathVariable(value = "nroCB", required = false) Long nroCB, @RequestParam("banco") Long idBanco) {
        try {
            DTOAdministrarBancosCuentas ret = experto.getCuentas(nroCB, idBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cuenta/{nroCB}")
    public ResponseEntity<?> getCuenta(@PathVariable("nroCB") Long nroCB) {
        try {
            DTODetalleCuenta ret = experto.getCuenta(nroCB);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





}
