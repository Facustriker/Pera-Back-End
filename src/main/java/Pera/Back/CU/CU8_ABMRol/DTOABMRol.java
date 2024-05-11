package Pera.Back.CU.CU8_ABMRol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMRol {

    Long nroRol;
    String nombreRol;
    Date fechaBajaRol;
    boolean deBaja;

}
