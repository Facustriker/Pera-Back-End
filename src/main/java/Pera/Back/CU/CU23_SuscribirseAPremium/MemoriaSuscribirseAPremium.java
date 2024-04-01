package Pera.Back.CU.CU23_SuscribirseAPremium;

import Pera.Back.Entities.PrecioPremium;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
@Data
@NoArgsConstructor
public class MemoriaSuscribirseAPremium implements Serializable {
    private PrecioPremium plan;
}
