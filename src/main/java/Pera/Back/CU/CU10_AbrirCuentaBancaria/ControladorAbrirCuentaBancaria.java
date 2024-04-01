package Pera.Back.CU.CU10_AbrirCuentaBancaria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/AbrirCuentaBancaria")
public class ControladorAbrirCuentaBancaria {

    @Autowired
    protected ExpertoAbrirCuentaBancaria experto;

    @GetMapping("/getBancos")
    public ResponseEntity<?> getBancos(@RequestParam("nombreBanco") String nombreBanco) {
        try {
            Collection<DTOBancoAbrirCuentaBancaria> ret = experto.getBancos(nombreBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/seleccionarBanco")
    public ResponseEntity<?> seleccionarBanco(@RequestParam("nroBanco") Long nroBanco) {
        try {
            boolean ret = experto.seleccionarBanco(nroBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/ingresarAlias")
    public ResponseEntity<?> ingresarAlias(@RequestParam("alias") String alias) {
        try {
            boolean ret = experto.ingresarAlias(alias);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getDatosConfirmacion")
    public ResponseEntity<?> getDatosConfirmacion() {
        try {
            DTOConfirmacionAbrirCuenta ret = experto.getDatosConfirmacion();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("confirmacion") Boolean confirmacion, @RequestParam("contrasena") String contrasena) {
        try {
            Long ret = experto.confirmar(confirmacion, contrasena);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
