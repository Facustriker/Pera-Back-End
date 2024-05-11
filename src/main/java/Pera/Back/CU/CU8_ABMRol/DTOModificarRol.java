package Pera.Back.CU.CU8_ABMRol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOModificarRol {

    Long nroRol;
    String nombreRol;
}
