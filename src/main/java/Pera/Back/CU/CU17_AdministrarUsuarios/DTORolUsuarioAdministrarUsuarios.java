package Pera.Back.CU.CU17_AdministrarUsuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTORolUsuarioAdministrarUsuarios {

    Long nroUsuario;
    String nombreUsuario;
    String rolActual;
    @Builder.Default
    Collection<String> rolesDisponibles = new ArrayList<>();
}
