package Pera.Back.CU.CU4_ABMConfiguracionRol;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.RepositorioConfiguracionRol;
import Pera.Back.Repositories.RepositorioPermiso;
import Pera.Back.Repositories.RepositorioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

        Collection<ConfiguracionRol> configuracionesSistema = repositorioConfiguracionRol.findAll(Sort.by("rol.nombreRol", "fhaCR"));

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

        Optional<Rol> optR = repositorioRol.obtenerRolPorNombre((dto.getNombreRol()));

        if(optR.isEmpty()) {
            throw new Exception("No se encontró el rol");
        }

        Rol rol = optR.get();


        configuracionNueva.setRol(rol);

        for(String p: dto.getPermisos()){
            Optional<Permiso> optP = repositorioPermiso.findByNombrePermiso(p);

            if(optP.isEmpty()) {
                throw new Exception("No se encontró el permiso \"" + p + "\"");
            }

            Permiso permiso = optP.get();

            configuracionNueva.addPermiso(permiso);
        }

        Optional<ConfiguracionRol> optCrAnterior = repositorioConfiguracionRol.obtenerVigentePorRol(rol);

        if(optCrAnterior.isEmpty()) throw new Exception("No se pudo registrar el nuevo vínculo ya que no se encontró el anterior");

        ConfiguracionRol crAnterior = optCrAnterior.get();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(repositorioConfiguracionRol, dto.getFechaInicio(), dto.getFechaFin(), rol.getId());

        if(crAnterior.getFhaCR().before(configuracionNueva.getFhaCR()) && (crAnterior.getFhbCR() == null || crAnterior.getFhbCR().after(configuracionNueva.getFhbCR()))) {
            //Es envolvente
            ConfiguracionRol crDividida = repositorioConfiguracionRol.obtenerCRDividida(configuracionNueva.getFhbCR(), crAnterior.getFhbCR());
            for (Permiso permiso : crAnterior.getPermisos()) {
                crDividida.addPermiso(permiso);
            }
            crDividida.setRol(crAnterior.getRol());
            repositorioConfiguracionRol.save(crDividida);
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
