package Pera.Back.CU.MisCuentasBancarias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOMisCuentasBancarias {

    private Long id;
    private String nombreBanco;
    private Double monto;
    private String moneda;
    private String estado;
}
