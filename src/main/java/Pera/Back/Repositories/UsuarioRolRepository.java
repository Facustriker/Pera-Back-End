package Pera.Back.Repositories;

import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Usuario;
import Pera.Back.Entities.UsuarioRol;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UsuarioRolRepository extends BaseRepository<UsuarioRol, Long> {

    @Query("SELECT ur " +
            "FROM UsuarioRol ur " +
            "WHERE ur.usuario = :usuario " +
            "AND CURRENT_TIMESTAMP >= ur.fhaUsuarioRol " +
            "AND (ur.fhbUsuarioRol IS NULL OR CURRENT_TIMESTAMP < ur.fhbUsuarioRol)")
    public UsuarioRol getActualByUsuario(@Param("usuario") Usuario usuario);
}
