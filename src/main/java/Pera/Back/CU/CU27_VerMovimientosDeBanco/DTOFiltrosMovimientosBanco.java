package Pera.Back.CU.CU27_VerMovimientosDeBanco;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOFiltrosMovimientosBanco implements Serializable {
    boolean emisiones;
    Date fechaDesde;
    Date fechaHasta;
    Long nroCBFiltro;
    boolean recepciones;
    boolean transferencias;
}
