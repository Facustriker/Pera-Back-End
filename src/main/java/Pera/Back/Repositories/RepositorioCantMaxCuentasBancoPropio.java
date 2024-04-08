package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasBancoPropio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository

public interface RepositorioCantMaxCuentasBancoPropio extends BaseRepository<CantMaxCuentasBancoPropio, Long> {

    @Query("SELECT p " +
            "FROM CantMaxCuentasBancoPropio p " +
            "WHERE fhaCMCBP <= CURRENT_TIMESTAMP " +
            "AND (fhbCMCBP IS NULL OR fhbCMCBP > CURRENT_TIMESTAMP)")
    Collection<CantMaxCuentasBancoPropio> getVigentes();

    @Query("SELECT cantidad " +
            "FROM CantMaxCuentasBancoPropio cmcbp " +
            "WHERE cmcbp.fhbCMCBP IS NULL OR CURRENT_TIMESTAMP < cmcbp.fhbCMCBP")
    int obtenerCantidadVigente();

}
