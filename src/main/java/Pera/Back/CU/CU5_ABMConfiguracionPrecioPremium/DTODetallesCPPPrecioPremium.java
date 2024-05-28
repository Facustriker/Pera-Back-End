package Pera.Back.CU.CU5_ABMConfiguracionPrecioPremium;

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
public class DTODetallesCPPPrecioPremium {

    String nombre;
    Integer duracion;
    String descripcion;
    Double precio;
}
