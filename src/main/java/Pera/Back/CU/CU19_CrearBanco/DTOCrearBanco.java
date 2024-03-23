package Pera.Back.CU.CU19_CrearBanco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOCrearBanco {

    String nombre;
    String simboloMoneda;
    boolean habilitacionAutomatica;
    boolean usarPassword;
    String password;

}
