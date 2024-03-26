package Pera.Back.CU.MisBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOMisBancos {

    private Long id;
    private String nombre;
    private String ocupacion;
    private String estado;
}
