package Pera.Back.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ConfiguracionPrecioPremium")
public class ConfiguracionPrecioPremium extends BaseEntity {


    @NotNull
    @Column(name = "fhaCPP", nullable = false)
    private Date fhaCPP;

    @Column(name = "fhbCPP")
    private Date fhbCPP;

    @JoinColumn(name = "configuracion")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private Collection<PrecioPremium> precios = new ArrayList<>();

    public void addPrecio(PrecioPremium pp) {
        precios.add(pp);
    }



}
