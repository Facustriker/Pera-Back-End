package Pera.Back.CU.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODatosUsuario {

    Long id;

    String email;

    String nombre;

    DTODatosUsuarioRol rol;
}
