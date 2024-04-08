package Pera.Back.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Emision extends Transferencia{

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "responsable_id")
    private Usuario responsable;
}
