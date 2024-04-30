package Pera.Back.CU.CU17_AdministrarUsuarios;

import Pera.Back.CU.CU26_VerMovimientos.DTODetallesVerMovimientosSeleccionado;
import Pera.Back.CU.CU26_VerMovimientos.DTOFiltrosVerMovimientos;
import Pera.Back.CU.CU26_VerMovimientos.DTOVerMovimientos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/AdministrarUsuarios")
public class ControladorAdministrarUsuarios {

    @Autowired
    protected ExpertoAdministrarUsuarios experto;

    @GetMapping("/getUsuarios")
    public ResponseEntity<?> getUsuarios() {
        try {
            Collection<DTOAdministrarUsuarios> ret = experto.getUsuarios();
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<?> getUsuarioSeleccionado(@PathVariable("idUsuario") Long idUsuario, @RequestParam String filtro) {
        try {
            DTOAdministrarUsuariosSeleccionado ret = experto.getUsuarioSeleccionado(idUsuario, filtro);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{nroCB}")
    public ResponseEntity<?> darBajaCuentaSeleccionada(@PathVariable("nroCB") Long nroCB) {
        try {
            experto.darBajaCuentaSeleccionada(nroCB);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
