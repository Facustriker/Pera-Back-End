package Pera.Back.CU.CU9_ABMPSM;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOABMPSM {

    Long nroSimbolo;
    String simbolo;
    boolean soloPremium;
    Date fechaInicio;
    Date fechaFin;
}
