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
@Table(name = "Usuario")
public class Usuario extends BaseEntity {

    @NotNull
    @Column(name = "fhaUsuario", nullable = false)
    private Date fhaUsuario;

    @Column(name = "fhbUsuario")
    private Date fhbUsuario;

    @NotNull
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @NotNull
    @Column(name = "nombreUsuario", nullable = false)
    private String nombreUsuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_actual_id")
    private Rol rolActual;



}
