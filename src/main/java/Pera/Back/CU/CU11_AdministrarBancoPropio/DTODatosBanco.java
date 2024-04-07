package Pera.Back.CU.CU11_AdministrarBancoPropio;

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
public class DTODatosBanco {
    Long id;
    String nombre;
    String simboloMoneda;
    boolean habilitacionAutomatica;
    String dueno;
    boolean cambiarContrasena;
    String contrasena;
    boolean habilitado;
}
