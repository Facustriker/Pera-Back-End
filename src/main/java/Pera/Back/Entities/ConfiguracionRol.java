package Pera.Back.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ConfiguracionRol")
public class ConfiguracionRol extends BaseEntity {

    @Column(name = "fhaCR")
    private Date fhaCR;

    @Column(name = "fhbCR")
    private Date fhbCR;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nroCR", nullable = false)
    private Integer nroCR;

}
