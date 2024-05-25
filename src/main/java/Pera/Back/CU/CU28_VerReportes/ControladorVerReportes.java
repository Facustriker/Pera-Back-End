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
    @GetMapping("bancosAbiertosCerrados")
    public ResponseEntity<?> bancosAbiertosCerrados(@RequestParam("fechaInicio") Long fechaInicio, @RequestParam("fechaFin") Long fechaFin, @RequestParam("intervalo") Long intervalo) {
        try {
            DTOHistograma ret = experto.bancosAbiertosCerrados(new Date(fechaInicio), new Date(fechaFin), intervalo);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("montosTransferidos")
    public ResponseEntity<?> montosTransferidos(@RequestParam("fechaInicio") Long fechaInicio, @RequestParam("fechaFin") Long fechaFin, @RequestParam("intervalo") Long intervalo, @RequestParam("nroBanco") Long nroBanco) {
        try {
            DTOHistograma ret = experto.montosTransferidos(new Date(fechaInicio), new Date(fechaFin), intervalo, nroBanco);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("cantidadRegistraciones")
    public ResponseEntity<?> cantidadRegistraciones(@RequestParam("fechaInicio") Long fechaInicio, @RequestParam("fechaFin") Long fechaFin, @RequestParam("intervalo") Long intervalo) {
        try {
            DTOHistograma ret = experto.cantidadRegistraciones(new Date(fechaInicio), new Date(fechaFin), intervalo);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("cantidadSuscripciones")
    public ResponseEntity<?> cantidadSuscripciones(@RequestParam("fechaInicio") Long fechaInicio, @RequestParam("fechaFin") Long fechaFin) {
        try {
            DTOHistograma ret = experto.cantidadSuscripciones(new Date(fechaInicio), new Date(fechaFin));
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
