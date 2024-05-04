package Pera.Back.CU.CU12_AdministrarBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAdministrarBancosCuentas {

    String nombreBanco;
    @Builder.Default
    Collection<DTOAdministrarBancosCuentasDetalle> detalles = new ArrayList<>();
}
