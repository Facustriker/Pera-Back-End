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
@Table(name = "PrecioPremium")
public class PrecioPremium extends BaseEntity {


    @NotNull
    @Column(name = "nombrePP", nullable = false)
    private String nombrePP;

    @Column(name = "descripcion")
    private String descripcion;

    @NotNull
    @Column(name = "diasDuracion", nullable = false)
    private Integer diasDuracion;

    @NotNull
    @Column(name = "precio", nullable = false)
    private Double precio;



}
