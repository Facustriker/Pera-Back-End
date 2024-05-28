package Pera.Back.CU.CU1_ABMCMBNP;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMCMBNP {

    Long idCMBNP;
    Date fechaInicio;
    Date fechaFin;
    String cantidad;
}
