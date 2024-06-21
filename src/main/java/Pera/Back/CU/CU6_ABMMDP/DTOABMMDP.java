package Pera.Back.CU.CU6_ABMMDP;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMMDP {

    Long nroMDP;
    String nombreMDP;
    Date fechaBajaMDP;
    boolean deBaja;
}
