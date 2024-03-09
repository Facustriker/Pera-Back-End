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
@Table(name = "Rol")
public class Rol extends BaseEntity {

    @Column(name = "fhbRol")
    private Date fhbRol;

    @NotNull
    @Column(name = "nombreRol", nullable = false)
    private String nombreRol;


    @OneToMany(mappedBy = "rol", cascade = {}, fetch = FetchType.EAGER)
    private Collection<ConfiguracionRol> configuraciones;

    public Collection<ConfiguracionRol> getConfiguracionesVigentes() {
        ArrayList<ConfiguracionRol> ret = new ArrayList<>();

        Date ahora = new Date();

        for (ConfiguracionRol configuracion : configuraciones) {
            if(ahora.after(configuracion.getFhaCR()) && (configuracion.getFhbCR() == null || ahora.before(configuracion.getFhbCR()))) {
                ret.add(configuracion);
            }
        }

        return ret;
    }

}
