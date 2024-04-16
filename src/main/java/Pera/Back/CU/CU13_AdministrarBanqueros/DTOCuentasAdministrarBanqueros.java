package Pera.Back.CU.CU13_AdministrarBanqueros;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOCuentasAdministrarBanqueros {
    boolean checked;
    String nombreUsuario;
    Long nroCB;
}
