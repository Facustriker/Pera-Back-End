package Pera.Back.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ParametroSimboloMoneda")
public class ParametroSimboloMoneda extends BaseEntity{

    @NotNull
    @Column(name = "fhaPSM", nullable = false)
    private Date fhaPSM;

    @Column(name = "fhbPSM")
    private Date fhbPSM;

    @NotNull
    @Column(name = "simboloMonedaPorDefecto", nullable = false)
    private String simboloMonedaPorDefecto = "$";

    @NotNull
    @Column(name = "usuarioNoPremiumElige", nullable = false)
    private boolean usuarioNoPremiumElige = false;
}
