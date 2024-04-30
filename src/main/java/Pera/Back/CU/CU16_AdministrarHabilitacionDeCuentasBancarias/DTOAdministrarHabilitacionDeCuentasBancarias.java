package Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias;

import Pera.Back.CU.CU13_AdministrarBanqueros.DTOCuentasAdministrarBanqueros;
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
public class DTOAdministrarHabilitacionDeCuentasBancarias {

    String nombreBanco;
    Long idBanco;
    @Builder.Default
    Collection<DTODetallesAdministrarHabilitacionCuentasBancarias> detallesCuentas = new ArrayList<>();
}
