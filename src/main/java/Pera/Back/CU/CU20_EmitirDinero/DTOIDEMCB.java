package Pera.Back.CU.CU20_EmitirDinero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOIDEMCB implements Serializable {
    boolean checked;
    String mail;
    String nombreUsuario;
    Long nroCB;
    Long nroTransferencia;
}
