package Pera.Back.CU.CU27_VerMovimientosDeBanco;

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
public class DTOMovimientosBanco implements Serializable {


    String nombreBanco;

    @Builder.Default
    Collection<DTODetallesMovimientosBanco> detalles = new ArrayList<>();

    public void addDetalle(DTODetallesMovimientosBanco dto) {
        detalles.add(dto);
    }

}
