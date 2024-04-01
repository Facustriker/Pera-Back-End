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
@Table(name = "Permiso")
public class Permiso extends BaseEntity {

    @Column(name = "fhbPermiso")
    private Date fhbPermiso;

    @NotNull
    @Column(name = "nombrePermiso", nullable = false)
    private String nombrePermiso;

}
