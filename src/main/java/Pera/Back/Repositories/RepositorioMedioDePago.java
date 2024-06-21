package Pera.Back.Repositories;

import Pera.Back.Entities.MedioDePago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioMedioDePago extends BaseRepository<MedioDePago, Long> {

    @Query("SELECT mdp FROM MedioDePago mdp WHERE mdp.id = :id")
    MedioDePago obtenerPorId(@Param("id") Long id);

    @Query("SELECT mdp " +
            "FROM MedioDePago mdp " +
            "WHERE mdp.fhbMDP IS NULL OR CURRENT_TIMESTAMP < mdp.fhbMDP")
    Collection<MedioDePago> obtenerVigentes();

    @Query("SELECT mdp " +
            "FROM MedioDePago mdp " +
            "WHERE mdp.id = :nroMDP " +
            "AND mdp.fhbMDP IS NULL OR CURRENT_TIMESTAMP < mdp.fhbMDP")
    Optional<MedioDePago> obtenerVigentePorId(@Param("nroMDP") Long nroMDP);

}
