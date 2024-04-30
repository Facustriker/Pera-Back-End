package Pera.Back.Repositories;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends BaseRepository<Usuario, Long> {

    @Query("SELECT u " +
            "FROM Usuario u " +
            "WHERE fhaUsuario <= CURRENT_TIMESTAMP " +
            "AND (fhbUsuario IS NULL OR fhbUsuario > CURRENT_TIMESTAMP) ")
    Collection<Usuario> getUsuariosVigentes();


    @Query("SELECT u " +
            "FROM Usuario u " +
            "WHERE u.id = :idUsuario " +
            "AND fhaUsuario <= CURRENT_TIMESTAMP " +
            "AND (fhbUsuario IS NULL OR fhbUsuario > CURRENT_TIMESTAMP)")
    Optional<Usuario> getUsuarioVigentePorNroUsuario(@Param("idUsuario") Long idUsuario);
}
