package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxBancosNoPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CantMaxBancosNoPremiumRepository extends BaseRepository<CantMaxBancosNoPremium, Long>{

    @Query("SELECT cantidad " +
            "FROM CantMaxBancosNoPremium cmbnp " +
            "WHERE cmbnp.fhbCMBNP IS NULL OR CURRENT_TIMESTAMP < cmbnp.fhbCMBNP")
    int obtenerCantidadVigente();
}
