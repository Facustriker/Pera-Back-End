package Pera.Back.CU.Banco;

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
    private String nombreUsuario;
    private double baseMonetaria;
}
