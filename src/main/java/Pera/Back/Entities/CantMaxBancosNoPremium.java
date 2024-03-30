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
@Table(name = "CantMaxBancosNoPremium")
public class CantMaxBancosNoPremium extends BaseEntity{

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @NotNull
    @Column(name = "fhaCMBNP", nullable = false)
    private Date fhaCMBNP;

    @Column(name = "fhbCMBNP")
    private Date fhbCMBNP;
}
