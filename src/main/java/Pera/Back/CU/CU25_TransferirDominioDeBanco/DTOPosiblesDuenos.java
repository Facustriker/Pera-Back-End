package Pera.Back.CU.CU25_TransferirDominioDeBanco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOPosiblesDuenos {
    Long id;
    Long nroCB;
    String nombre;
    String rol;
}
