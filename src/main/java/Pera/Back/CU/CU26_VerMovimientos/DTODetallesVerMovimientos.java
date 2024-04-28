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
public class DTODetallesVerMovimientos {

    Long nroTransferencia;
    Date fechaTransferencia;
    String tipoTransferencia;
    Long nroCBTransferencia;
    String titularCB;
    Double montoTransferencia;
    String estadoTransferencia;
}
