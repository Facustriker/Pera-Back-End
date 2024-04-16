package Pera.Back.CU.CU15_AdministrarDatosDelUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAdminDatosUsuario {
    private Long id;
    private String email;
    private String nombre;
    private String contrasena;
    private boolean cambiarContrasena;
}
