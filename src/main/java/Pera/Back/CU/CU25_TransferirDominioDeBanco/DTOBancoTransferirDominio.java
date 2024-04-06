package Pera.Back.CU.CU25_TransferirDominioDeBanco;

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
public class DTOBancoTransferirDominio {
    String nombre;
    @Builder.Default
    Collection<DTOPosiblesDuenos> posiblesDuenos = new ArrayList<>();
}
