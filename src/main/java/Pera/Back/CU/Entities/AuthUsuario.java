package Pera.Back.CU.Entities;

import Pera.Back.CU.Entities.EntidadBase;
import Pera.Back.CU.Entities.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "AuthUsuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthUsuario extends EntidadBase {

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
}
