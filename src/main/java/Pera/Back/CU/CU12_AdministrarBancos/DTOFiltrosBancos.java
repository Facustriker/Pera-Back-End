package Pera.Back.CU.CU12_AdministrarBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOFiltrosBancos {
    String nombre;
    boolean deshabilitados;
    boolean bajas;
}
