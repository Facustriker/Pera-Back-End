package Pera.Back.CU.CU23_SuscribirseAPremium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOOpcionesPago {

    Long idPP;
    String nombrePP;
    String descripPP;
    Double precioPP;

    @Builder.Default
    Collection<DTOMedioDePago> mediosDePago = new ArrayList<>();

    public void addMedioDePago(DTOMedioDePago dtoMedioDePago) {
        mediosDePago.add(dtoMedioDePago);
    }
}
