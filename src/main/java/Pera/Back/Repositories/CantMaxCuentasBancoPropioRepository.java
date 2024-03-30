package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasBancoPropio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CantMaxCuentasBancoPropioRepository extends BaseRepository<CantMaxCuentasBancoPropio, Long>{
    @Query("SELECT cantidad " +
            "FROM CantMaxCuentasBancoPropio cmcbp " +
            "WHERE cmcbp.fhbCMCBP IS NULL OR CURRENT_TIMESTAMP < cmcbp.fhbCMCBP")
    int obtenerCantidadVigente();
}
