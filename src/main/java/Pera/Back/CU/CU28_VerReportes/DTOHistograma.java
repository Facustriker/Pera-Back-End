package Pera.Back.CU.CU28_VerReportes;

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
public class DTOHistograma {

    @Builder.Default
    Collection<DTOItemHistograma> items = new ArrayList<>();

    String titulo;
    public void addItem(DTOItemHistograma item) {
        items.add(item);
    }
}
