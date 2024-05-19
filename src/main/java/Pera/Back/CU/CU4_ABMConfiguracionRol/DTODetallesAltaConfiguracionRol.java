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
public class DTODetallesAltaConfiguracionRol {

    @Builder.Default
    Collection<String> roles = new ArrayList<>();
    @Builder.Default
    Collection<String> permisos = new ArrayList<>();

    public void addRol(String r){
        roles.add(r);
    }

    public void addPermiso(String p){
        permisos.add(p);
    }

}
