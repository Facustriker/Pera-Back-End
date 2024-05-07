package Pera.Back.CU.CU28_VerReportes;

import Pera.Back.Entities.Banco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOCantBancosAbiertosCerrados {

    int cantBancosAbiertos;

    int cantBancosCerrados;
}
