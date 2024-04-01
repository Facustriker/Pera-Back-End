package Pera.Back.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "UsuarioRol")
public class UsuarioRol extends BaseEntity {

    @Column(name = "fhaUsuarioRol")
    private Date fhaUsuarioRol;

    @Column(name = "fhbUsuarioRol")
    private Date fhbUsuarioRol;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Rol rol;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private Usuario usuario;

    @ManyToOne(cascade = {CascadeType.MERGE})
    private PrecioPremium plan;

}
