package Pera.Back.CU.CU26_VerMovimientos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOFiltrosVerMovimientos {

    String filtroNombreUsuario;
    String filtroNroCB;
    Date fechaDesde;
    Date fechaHasta;
}
