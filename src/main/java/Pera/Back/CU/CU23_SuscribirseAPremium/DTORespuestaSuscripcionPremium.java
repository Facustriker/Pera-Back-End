package Pera.Back.CU.CU23_SuscribirseAPremium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTORespuestaSuscripcionPremium {

    boolean exito;
    Date fechaFin;
    String mensaje;
}
