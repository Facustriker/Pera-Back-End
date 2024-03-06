package Pera.Back.CU.Entities;

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
public class Usuario extends BaseEntity implements UserDetails{

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

    /*
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authUsuario_id")
    private AuthUsuario authUsuario;
    */

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    @JoinColumn(name = "usuario_id")
    private List<Rol> roles = new ArrayList<>();

    public void AgregarRol(Rol r){
        roles.add(r);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((roles.get(0).getNombreRol())));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDetails orElseThrow() {
        return null;
    }

}
