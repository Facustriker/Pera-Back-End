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
@Table(name = "Rol")
public class Rol extends BaseEntity {

    @Column(name = "fhbRol")
    private Date fhbRol;

    @NotNull
    @Column(name = "nombreRol", nullable = false)
    private String nombreRol;

}
