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
@Table(name = "CuentaBancaria")
public class CuentaBancaria extends BaseEntity{

    @NotNull
    @Column(name = "alias", nullable = false)
    private String alias;

    @NotNull
    @Column(name = "esBanquero", nullable = false)
    private boolean esBanquero;

    @NotNull
    @Column(name = "fhaCB", nullable = false)
    private Date fhaCB;

    @Column(name = "fhbCB")
    private Date fhbCB;

    @NotNull
    @Column(name = "habilitada", nullable = false)
    private boolean habilitada;

    @Column(name = "montoDinero")
    private double montoDinero;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "titular")
    private Usuario titular;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banco")
    private Banco banco;
}
