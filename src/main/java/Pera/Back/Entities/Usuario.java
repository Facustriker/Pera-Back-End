package Pera.Back.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authUsuario_id")
    private AuthUsuario authUsuario;
    */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rol_id")
    private Rol rol;



}
