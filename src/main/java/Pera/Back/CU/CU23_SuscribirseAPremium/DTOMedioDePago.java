package Pera.Back.CU.CU23_SuscribirseAPremium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOMedioDePago {

    String nombreMDP;

    Long idMDP;
}
