package Pera.Back.CU.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTODatosUsuarioRol {

    String nombre;

    Collection<String> permisos;

    Date vencimiento;



}
