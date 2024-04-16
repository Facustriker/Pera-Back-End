package Pera.Back.CU.CU13_AdministrarBanqueros;

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
public class DTOAdministrarBanqueros {
    String nombreBanco;
    Long idBanco;
    Long idCBDueno;
    @Builder.Default
    Collection<DTOCuentasAdministrarBanqueros> detallesCuentas = new ArrayList<>();
}
