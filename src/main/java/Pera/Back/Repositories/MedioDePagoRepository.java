package Pera.Back.Repositories;

import Pera.Back.Entities.MedioDePago;
import Pera.Back.Entities.PrecioPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MedioDePagoRepository extends BaseRepository<MedioDePago, Long> {

    @Query("SELECT mdp FROM MedioDePago mdp WHERE mdp.id = :id")
    MedioDePago obtenerPorId(@Param("id") Long id);

    @Query("SELECT mdp " +
            "FROM MedioDePago mdp " +
            "WHERE mdp.fhbMDP IS NULL OR CURRENT_TIMESTAMP < mdp.fhbMDP")
    Collection<MedioDePago> obtenerVigentes();

}
