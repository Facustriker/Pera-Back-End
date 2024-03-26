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
@Table(name = "CantMaxCuentasBancoPropio")
public class CantMaxCuentasBancoPropio extends BaseEntity{

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @NotNull
    @Column(name = "fhaCMCBP", nullable = false)
    private Date fhaCMCBP;

    @Column(name = "fhbCMCBP")
    private Date fhbCMCBP;
}
