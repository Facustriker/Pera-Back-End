package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxBancosNoPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCantMaxBancosNoPremium extends BaseRepository<CantMaxBancosNoPremium, Long>{

    @Query("SELECT cantidad " +
            "FROM CantMaxBancosNoPremium cmbnp " +
            "WHERE fhaCMBNP < CURRENT_TIMESTAMP AND " +
            "(cmbnp.fhbCMBNP IS NULL OR CURRENT_TIMESTAMP < cmbnp.fhbCMBNP)")
    int obtenerCantidadVigente();
}
