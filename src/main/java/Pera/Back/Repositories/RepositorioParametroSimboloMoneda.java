package Pera.Back.Repositories;

import Pera.Back.Entities.ParametroSimboloMoneda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioParametroSimboloMoneda extends BaseRepository<ParametroSimboloMoneda, Long>{

    @Query("SELECT simboloMonedaPorDefecto " +
            "FROM ParametroSimboloMoneda psm " +
            "WHERE psm.fhbPSM IS NULL OR CURRENT_TIMESTAMP < psm.fhbPSM")
    String obtenerSimboloVigente();
}
