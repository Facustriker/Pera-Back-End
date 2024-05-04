package Pera.Back.Repositories;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Rol;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioRol extends BaseRepository<Rol, Long> {

    @Query("SELECT r FROM Rol r WHERE r.nombreRol = :nombreRol")
    Optional<Rol> obtenerRolPorNombre(@Param("nombreRol") String nombreRol);

    @Query("SELECT r " +
            "FROM Rol r " +
            "WHERE (fhbRol IS NULL OR fhbRol > CURRENT_TIMESTAMP) ")
    Collection<Rol> getRolesVigentes();
}
