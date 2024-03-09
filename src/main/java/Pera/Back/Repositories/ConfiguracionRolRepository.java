package Pera.Back.Repositories;

import Pera.Back.Entities.ConfiguracionRol;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ConfiguracionRolRepository extends BaseRepository<ConfiguracionRol, Long> {

    @Query("SELECT p " +
            "FROM ConfiguracionRol cr " +
            "INNER JOIN cr.permisos p " +
            "WHERE cr.rol = :rol " +
            "AND CURRENT_DATE BETWEEN cr.fhaCR AND cr.fhbCR")
    Collection<Permiso> getPermisos(@Param("rol") Rol rol);

}
