package Pera.Back.CU.MisCuentasBancarias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOCuentaBancariaPropia {

    private Long id;
    private String nombreUsuario;
    private Double monto;
    private String moneda;
    private Date fha;
    private Date fhb;
    private String estado;
    private String alias;
    private Long nroBanco;
    private String nombreBanco;
    private String estadoBanco;
}
