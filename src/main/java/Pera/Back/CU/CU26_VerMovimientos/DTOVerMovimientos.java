package Pera.Back.CU.CU26_VerMovimientos;

import Pera.Back.CU.CU27_VerMovimientosDeBanco.DTODetallesMovimientosBanco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOVerMovimientos {

    Long nroCuenta;
    @Builder.Default
    Collection<DTODetallesVerMovimientos> detalles = new ArrayList<>();

    public void addDetalle(DTODetallesVerMovimientos dto) {
        detalles.add(dto);
    }

}
