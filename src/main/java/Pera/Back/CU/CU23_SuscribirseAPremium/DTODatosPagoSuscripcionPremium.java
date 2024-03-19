package Pera.Back.CU.CU23_SuscribirseAPremium;


import Pera.Back.Functionalities.RealizarPagos.DTODatosPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODatosPagoSuscripcionPremium {

    Long idMedioDePago;
    DTODatosPago datosPago;
}
