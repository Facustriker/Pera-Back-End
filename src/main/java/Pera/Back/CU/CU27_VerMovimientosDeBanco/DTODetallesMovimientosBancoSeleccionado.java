package Pera.Back.CU.CU27_VerMovimientosDeBanco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODetallesMovimientosBancoSeleccionado implements Serializable {
    boolean anulada;
    Date fhTransferencia;
    Double monto;
    String motivo;
    String nombreTitularDestino;
    String nombreTitularOrigen;
    Long nroCBDestino;
    Long nroCBOrigen;
    Long nroTransferencia;
    String responsable;
    String tipo;

}
