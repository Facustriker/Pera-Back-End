package Pera.Back.CU.CU5_ABMConfiguracionPrecioPremium;

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
public class DTODetallesCPP {

    Long nroConfig;
    Date fechaInicio;
    Date fechaFin;
    @Builder.Default
    Collection<DTODetallesCPPPrecioPremium> planes = new ArrayList<>();

    public void addPlan(DTODetallesCPPPrecioPremium p){
        planes.add(p);
    }
}
