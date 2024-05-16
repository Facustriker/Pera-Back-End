package Pera.Back.CU.CU4_ABMConfiguracionRol;

import Pera.Back.Entities.ConfiguracionRol;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import Pera.Back.Repositories.RepositorioConfiguracionRol;
import Pera.Back.Repositories.RepositorioPermiso;
import Pera.Back.Repositories.RepositorioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoABMConfiguracionRol {

    @Autowired
    private final RepositorioConfiguracionRol repositorioConfiguracionRol;

    @Autowired
    private final RepositorioRol repositorioRol;

    @Autowired
    private final RepositorioPermiso repositorioPermiso;

    public Collection<DTOABMConfiguracionRol> getConfiguraciones() throws Exception{

        Collection<ConfiguracionRol> configuracionesSistema = repositorioConfiguracionRol.findAll();

        if(configuracionesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado configuraciones");
        }

        Collection<DTOABMConfiguracionRol> dto = new ArrayList<>();

        for(ConfiguracionRol configuracion: configuracionesSistema){
            DTOABMConfiguracionRol aux = DTOABMConfiguracionRol.builder()
                    .nroConfig(configuracion.getNroCR())
                    .nombreRol(configuracion.getRol().getNombreRol())
                    .fechaInicio(configuracion.getFhaCR())
                    .fechaFin(configuracion.getFhbCR())
                    .build();

            dto.add(aux);
        }

        return dto;

    }

    public DTODetallesAltaConfiguracionRol altaConfiguracion() throws Exception{
        Collection<Rol> rolesSistema = repositorioRol.getRolesVigentes();

        if(rolesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado roles vigentes");
        }

        Collection<Permiso> permisosSistema = repositorioPermiso.getPermisosVigentes();

        if(permisosSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado permisos vigentes");
        }

        Collection<String> roles = new ArrayList<>();
        Collection<String> permisos = new ArrayList<>();

        for(Rol rol: rolesSistema){
            String nombreRol = rol.getNombreRol();
            roles.add(nombreRol);
        }

        for(Permiso permiso: permisosSistema){
            String nombrePermiso = permiso.getNombrePermiso();
            permisos.add(nombrePermiso);
        }


        DTODetallesAltaConfiguracionRol dto = DTODetallesAltaConfiguracionRol.builder()
                .roles(roles)
                .permisos(permisos)
                .build();

        return dto;

    }

    public void confirmar(DTOAltaConfiguracionRol dto) throws Exception{

        ConfiguracionRol configuracionNueva = ConfiguracionRol.builder()
                .fhaCR(dto.getFechaInicio())
                .fhbCR(dto.getFechaFin())
                .build();

        Rol rol = Rol.builder()
                .nombreRol(dto.getNombreRol())
                .build();

        configuracionNueva.setRol(rol);

        for(String permiso: dto.getPermisos()){
            Permiso permisoNuevo = Permiso.builder()
                    .nombrePermiso(permiso)
                    .build();

            configuracionNueva.addPermiso(permisoNuevo);
        }

        repositorioConfiguracionRol.save(configuracionNueva);
    }

    public DTOAltaConfiguracionRol getDetalleConfiguracion(Long nroConfig) throws Exception{

        Optional<ConfiguracionRol> configuracion = repositorioConfiguracionRol.obtenerConfiguracionVigentePorNumeroConfiguracion(nroConfig);

        if(configuracion.isEmpty()){
            throw new Exception("Error, no se ha encontrado la configuracion");
        }

        Collection<String> permisos = new ArrayList<>();

        for(Permiso permiso: configuracion.get().getPermisos()){
            String nombrePermiso = permiso.getNombrePermiso();
            permisos.add(nombrePermiso);
        }

        DTOAltaConfiguracionRol dto = DTOAltaConfiguracionRol.builder()
                .nroConfig(configuracion.get().getNroCR())
                .nombreRol(configuracion.get().getRol().getNombreRol())
                .fechaInicio(configuracion.get().getFhaCR())
                .fechaFin(configuracion.get().getFhbCR())
                .permisos(permisos)
                .build();

        return dto;
    }

}
