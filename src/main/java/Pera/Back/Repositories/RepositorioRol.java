package Pera.Back.Repositories;

import Pera.Back.Entities.Rol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRol extends BaseRepository<Rol, Long> {

    @Query("SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol")
    public Rol obtenerRolPorNombre(@Param("nombreRol") String nombreRol);
}
