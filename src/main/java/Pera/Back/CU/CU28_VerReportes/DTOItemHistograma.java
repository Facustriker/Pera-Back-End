package Pera.Back.CU.CU28_VerReportes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOItemHistograma {
    String label;
    @Builder.Default
    ArrayList<Long> values = new ArrayList<>();
}
