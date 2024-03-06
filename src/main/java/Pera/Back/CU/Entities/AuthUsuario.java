package Pera.Back.CU.Entities;

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
public class AuthUsuario extends BaseEntity {

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Usuario usuario;
}
