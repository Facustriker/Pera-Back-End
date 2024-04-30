package Pera.Back.CU.CU17_AdministrarUsuarios;

import Pera.Back.CU.CU16_AdministrarHabilitacionDeCuentasBancarias.DTODetallesAdministrarHabilitacionCuentasBancarias;
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
public class DTOAdministrarUsuariosSeleccionado {

    String nombreUsuario;
    String rolUsuario;
    @Builder.Default
    Collection<DTODetallesUsuarioSeleccionado> detallesUsuario = new ArrayList<>();
}
