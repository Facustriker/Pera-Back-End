package Pera.Back.CU.CU12_AdministrarBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODetalleBanco {

    Long id;
    String nombre;
    boolean habilitado;
    Date baja;
    String nombreDueno;
    String mailDueno;
}
