package Pera.Back.CU.CU2_ABMCMCBP;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMCMCBP {

    Long idCMCBP;
    Date fechaInicio;
    Date fechaFin;
    String cantidad;
}
