package Pera.Back.Repositories;

import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioPermiso extends BaseRepository<Permiso, Long> {

    @Query("SELECT p " +
            "FROM Permiso p " +
            "WHERE (fhbPermiso IS NULL OR fhbPermiso > CURRENT_TIMESTAMP) ")
    Collection<Permiso> getPermisosVigentes();

    Optional<Permiso> findByNombrePermiso(String nombrePermiso);

}
