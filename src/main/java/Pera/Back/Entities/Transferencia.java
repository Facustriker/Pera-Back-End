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
@Table(name = "Transferencia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Transferencia extends BaseEntity {

    @NotNull
    @Column(name = "fhTransferencia")
    private Date fhTransferencia;

    @Column(name = "anulada")
    private boolean anulada;

    @NotNull
    @Column(name = "montoTransferencia")
    private Double montoTransferencia;

    @Column(name = "motivo")
    private String motivo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "origen_id")
    private CuentaBancaria origen;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destino_id")
    private CuentaBancaria destino;

}
