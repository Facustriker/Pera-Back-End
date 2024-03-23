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

    @Column(name = "habilitacionAutomatica")
    private Boolean habilitacionAutomatica;

    @Column(name = "password")
    private String password;

    @Column(name = "habilitado")
    private Boolean habilitado;

    @NotNull
    @Column(name = "nombreBanco", nullable = false)
    private String nombreBanco;

    @NotNull
    @Column(name = "simboloMoneda", nullable = false)
    private String simboloMoneda;

    /*

    @NotNull
    @Column(name = "nroBanco", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int nroBanco;

     */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dueno_id")
    private Usuario dueno;

}
