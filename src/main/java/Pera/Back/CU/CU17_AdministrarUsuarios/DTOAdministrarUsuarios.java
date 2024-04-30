package Pera.Back.CU.CU17_AdministrarUsuarios;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAdministrarUsuarios {

    Long idUsuario;
    String nombre;
    String correo;
    String rol;
    String estado;
}
