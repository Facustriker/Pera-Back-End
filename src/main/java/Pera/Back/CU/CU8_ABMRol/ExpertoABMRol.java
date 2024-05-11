package Pera.Back.CU.CU8_ABMRol;

import Pera.Back.Entities.Rol;
import Pera.Back.Repositories.RepositorioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoABMRol {

    @Autowired
    private final RepositorioRol repositorioRol;

    public Collection<DTOABMRol> getRoles() throws Exception{
        boolean dadoDeBaja;

        Collection<Rol> rolesSistema = repositorioRol.findAll();

        if(rolesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado roles vigentes");
        }

        Collection<DTOABMRol> dto = new ArrayList<>();

        for(Rol rol: rolesSistema){
            if(rol.getFhbRol() == null){
                dadoDeBaja = false;
            }else{
                dadoDeBaja = true;
            }

            DTOABMRol aux = DTOABMRol.builder()
                    .nroRol(rol.getId())
                    .nombreRol(rol.getNombreRol())
                    .fechaBajaRol(rol.getFhbRol())
                    .deBaja(dadoDeBaja)
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public void altaRol(String nombreRol) throws Exception{

        Collection<Rol> rolesSistema = repositorioRol.getRolesVigentes();

        if(rolesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado roles vigentes");
        }

        for(Rol rol: rolesSistema){
            if(rol.getNombreRol().equals(nombreRol)){
                throw new Exception("Error, ya existe un rol con el nombre ingresado");
            }
        }

        Rol rolNuevo = Rol.builder()
                .nombreRol(nombreRol)
                .build();

        repositorioRol.save(rolNuevo);
    }

    public DTOModificarRol getRol(Long nroRol) throws Exception{

        Optional<Rol> rol = repositorioRol.obtenerRolVigentePorNumeroRol(nroRol);

        if(rol.isEmpty()){
            throw new Exception("Error, no se ha encontrado el rol");
        }

        if(!(rol.get().getFhbRol() == null)){
            throw new Exception("Error, el rol ha sido dado de baja");
        }

        DTOModificarRol dto = DTOModificarRol.builder()
                .nroRol(rol.get().getId())
                .nombreRol(rol.get().getNombreRol())
                .build();

        return dto;
    }

    public void confirmar(DTOModificarRol dto) throws Exception{

        Optional<Rol> rol = repositorioRol.obtenerRolVigentePorNumeroRol(dto.getNroRol());

        if(rol.isEmpty()){
            throw new Exception("Error, no se ha encontrado el rol");
        }

        rol.get().setNombreRol(dto.getNombreRol());

        repositorioRol.save(rol.get());

    }

    public void darBaja(Long nroRol) throws Exception{

        Optional<Rol> rol = repositorioRol.obtenerRolVigentePorNumeroRol(nroRol);

        if(rol.isEmpty()){
            throw new Exception("Error, no se ha encontrado el rol");
        }

        rol.get().setFhbRol(new Date());

        repositorioRol.save(rol.get());
    }
}
