package Pera.Back.CU.CU12_AdministrarBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODetalleCuenta {

    Long nroCB;
    String nombreBanco;
    boolean habilitada;
    Date alta;
    Date baja;
    String nombreTitular;
}
