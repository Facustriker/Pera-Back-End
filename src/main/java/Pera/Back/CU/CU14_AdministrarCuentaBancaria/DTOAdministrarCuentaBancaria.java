package Pera.Back.CU.CU14_AdministrarCuentaBancaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAdministrarCuentaBancaria {

    private Long nroCB;
    private String alias;
    private Date fhBaja;
}
