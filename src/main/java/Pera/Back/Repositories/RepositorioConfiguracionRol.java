package Pera.Back.Repositories;

import Pera.Back.Entities.ConfiguracionRol;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioConfiguracionRol extends BaseRepository<ConfiguracionRol, Long> {

    @Query("SELECT p " +
            "FROM ConfiguracionRol cr " +
            "INNER JOIN cr.permisos p " +
            "WHERE cr.rol = :rol " +
            "AND CURRENT_TIMESTAMP >= cr.fhaCR " +
            "AND (CURRENT_TIMESTAMP < cr.fhbCR OR cr.fhbCR IS NULL)")
    Collection<Permiso> getPermisos(@Param("rol") Rol rol);

    @Query("SELECT c " +
            "FROM ConfiguracionRol c " +
            "WHERE id = :nroConfig " +
            "AND (fhbCR IS NULL OR fhbCR > CURRENT_TIMESTAMP)")
    Optional<ConfiguracionRol> obtenerConfiguracionVigentePorNumeroConfiguracion(@Param("nroConfig") Long nroConfig);

}
