package Pera.Back.CU.CU10_AbrirCuentaBancaria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOBancoAbrirCuentaBancaria {
    String nombreBanco;
    Long nroBanco;
}
