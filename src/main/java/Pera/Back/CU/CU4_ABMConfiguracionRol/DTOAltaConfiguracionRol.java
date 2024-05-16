package Pera.Back.CU.CU4_ABMConfiguracionRol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAltaConfiguracionRol {

    Long nroConfig;
    String nombreRol;
    Date fechaInicio;
    Date fechaFin;
    @Builder.Default
    Collection<String> permisos = new ArrayList<>();

    public void addPermiso(String p){
        permisos.add(p);
    }
}
