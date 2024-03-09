package Pera.Back.Entities;

import Pera.Back.Repositories.ConfiguracionRolRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "AuthUsuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthUsuario extends BaseEntity implements UserDetails {

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @NotNull
    @Column(name = "verificationCode", nullable = false)
    private int verificationCode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> ret = new ArrayList<>();

        Rol rol = usuario.getRol();

        Collection<ConfiguracionRol> configuraciones = rol.getConfiguracionesVigentes();

        Collection<Permiso> permisos = new ArrayList<>();

        for (ConfiguracionRol configuracion : configuraciones) {
            Collection<Permiso> permisosConf = configuracion.getPermisos();
            for (Permiso permiso : permisosConf) {
                permisos.add(permiso);
            }
        }

        for (Permiso permiso : permisos) {
            ret.add(new SimpleGrantedAuthority(permiso.getNombrePermiso()));
        }

        return ret;
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
}
