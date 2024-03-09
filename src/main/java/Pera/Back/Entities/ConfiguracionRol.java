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
@Table(name = "ConfiguracionRol")
public class ConfiguracionRol extends BaseEntity {

    @Column(name = "fhaCR")
    private Date fhaCR;

    @Column(name = "fhbCR")
    private Date fhbCR;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Rol rol;

    @JoinTable(name = "ConfiguracionRolPermiso",
    joinColumns = @JoinColumn(name = "configuracionRol"),
    inverseJoinColumns = @JoinColumn(name = "permiso"))
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Builder.Default
    private Collection<Permiso> permisos = new ArrayList<>();

    public void addPermiso(Permiso permiso) {
        permisos.add(permiso);
    }

    public Long getNroCR() {
        return getId();
    }

    public void setNroCR(Long nroCR) {
        setId(nroCR);
    }

}
