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
@Table(name = "CantMaxCuentasOtrosBancos")
public class CantMaxCuentasOtrosBancos extends BaseEntity{

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @NotNull
    @Column(name = "fhaCMCOB", nullable = false)
    private Date fhaCMCOB;

    @Column(name = "fhbCMCOB")
    private Date fhbCMCOB;
}
