package Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODetallesAdministrarHabilitacionCuentasBancarias {

    Long nroCB;
    String nombreTitular;
    Double monto;
    Date fechaBaja;
    boolean habilitada;
}
