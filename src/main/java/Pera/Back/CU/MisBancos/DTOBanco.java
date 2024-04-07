package Pera.Back.CU.MisBancos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOBanco {

    private Long nroBanco;
    private String nombre;
    private String estado;
    private String simboloMoneda;
    private String usaPassword;
    private String usaHabilitacionAutomatica;
    private String nombreDueno;
    private String emailDueno;
    private double baseMonetaria;
    private Long nroCB;
    private boolean esDueno;
}
