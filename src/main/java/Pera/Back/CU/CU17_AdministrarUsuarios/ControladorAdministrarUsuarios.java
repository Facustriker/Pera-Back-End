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

    @PostMapping("/getDetalle/{idUsuario}")
    public ResponseEntity<?> getUsuarioSeleccionado(@PathVariable("idUsuario") Long idUsuario) {
        try {
            DTOAdministrarUsuariosSeleccionado ret = experto.getUsuarioSeleccionado(idUsuario);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/filtro")
    public ResponseEntity<?> getCuentaFiltrada(@RequestParam("filtro") String filtro) {
        try {
            DTODetallesUsuarioSeleccionado ret = experto.getCuentaFiltrada(filtro);
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

    @PostMapping("/getRol/{idUsuario}")
    public ResponseEntity<?> getRolUsuario(@PathVariable("idUsuario") Long idUsuario) {
        try {
            DTORolUsuarioAdministrarUsuarios ret = experto.getRolUsuario(idUsuario);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam("nuevoRol") String nuevoRol, @RequestBody DTORolUsuarioAdministrarUsuarios dto) {
        try {
            Collection<String> ret = experto.confirmar(nuevoRol, dto);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
