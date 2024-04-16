package Pera.Back.CU.CU24_TransferirDinero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOObtenerCB {

    String aliasCuentaDestino;
    Long nroCuentaDestino;

}
