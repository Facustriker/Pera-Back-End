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
@Table(name = "MedioDePago")
public class MedioDePago extends BaseEntity {


    @NotNull
    @Column(name = "nombreMDP", nullable = false)
    private String nombreMDP;

    @Column(name = "descripcionMDP")
    private String descripcionMDP;

    @Column(name = "fhbMDP")
    private Date fhbMDP;



}
