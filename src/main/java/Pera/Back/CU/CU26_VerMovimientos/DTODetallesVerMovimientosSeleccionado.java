package Pera.Back.CU.CU26_VerMovimientos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODetallesVerMovimientosSeleccionado {

    Long nroTransferencia;
    String tipoTransferencia;
    Date fechaTransferencia;
    Long nroCBOrigen;
    String titularCBOrigen;
    Long nroCBDestino;
    String titularCBDestino;
    Double montoTransferencia;
    String estadoTransferencia;
    String motivoTransferencia;
}
