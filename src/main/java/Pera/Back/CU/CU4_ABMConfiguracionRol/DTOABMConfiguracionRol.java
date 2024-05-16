package Pera.Back.CU.CU4_ABMConfiguracionRol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMConfiguracionRol {

    Long nroConfig;
    String nombreRol;
    Date fechaInicio;
    Date fechaFin;
}
