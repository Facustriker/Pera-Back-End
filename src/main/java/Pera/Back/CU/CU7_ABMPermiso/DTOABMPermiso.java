package Pera.Back.CU.CU7_ABMPermiso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMPermiso {

    Long nroPermiso;
    String nombrePermiso;
    Date fechaBajaPermiso;
}
