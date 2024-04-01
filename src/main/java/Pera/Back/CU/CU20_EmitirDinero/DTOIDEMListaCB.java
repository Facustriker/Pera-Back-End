package Pera.Back.CU.CU20_EmitirDinero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOIDEMListaCB implements Serializable {
    Double monto;
    String moneda;
    String motivo;
    String nombreBanco;
    @Builder.Default
    Collection<DTOIDEMCB> detalles = new ArrayList<>();

    public void addDetalle(DTOIDEMCB dto) {
        detalles.add(dto);
    }

}
