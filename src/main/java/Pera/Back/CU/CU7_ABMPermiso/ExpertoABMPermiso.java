package Pera.Back.CU.CU7_ABMPermiso;

import Pera.Back.Entities.Permiso;
import Pera.Back.Repositories.RepositorioPermiso;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoABMPermiso {

    @Autowired
    private final RepositorioPermiso repositorioPermiso;

    public Collection<DTOABMPermiso> getPermisos() throws Exception{

        Collection<Permiso> permisosSistema = repositorioPermiso.findAll();

        Collection<DTOABMPermiso> dto = new ArrayList<>();

        if(permisosSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado permisos");
        }

        for(Permiso permiso: permisosSistema){
            DTOABMPermiso aux = DTOABMPermiso.builder()
                    .nroPermiso(permiso.getId())
                    .nombrePermiso(permiso.getNombrePermiso())
                    .fechaBajaPermiso(permiso.getFhbPermiso())
                    .build();

            dto.add(aux);
        }

        return dto;

    }
}
