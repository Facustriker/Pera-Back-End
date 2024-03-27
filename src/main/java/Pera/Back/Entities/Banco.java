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
@Table(name = "Banco")
public class Banco extends BaseEntity{

    @Column(name = "fhbBanco")
    private Date fhbBanco;

    @NotNull
    @Column(name = "habilitacionAutomatica", nullable = false)
    private Boolean habilitacionAutomatica;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "habilitado", nullable = false)
    private Boolean habilitado;

    @NotNull
    @Column(name = "nombreBanco", nullable = false)
    private String nombreBanco;

    @NotNull
    @Column(name = "simboloMoneda", nullable = false)
    private String simboloMoneda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dueno")
    private Usuario dueno;

}
