package Pera.Back.CU.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Usuario")
public class Usuario extends EntidadBase {

    @NotNull
    @Column(name = "fhaUsuario", nullable = false)
    private Date fhaUsuario;

    @Column(name = "fhbUsuario")
    private Date fhbUsuario;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @NotNull
    @Column(name = "nombreUsuario", nullable = false)
    private String nombreUsuario;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authUsuario_id")
    private AuthUsuario authUsuario;

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    @JoinColumn(name = "usuario_id")
    private List<Rol> roles = new ArrayList<>();

    public void AgregarRol(Rol r){
        roles.add(r);
    }

}
