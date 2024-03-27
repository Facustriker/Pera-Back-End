package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CantMaxCuentasOtrosBancosRepository extends BaseRepository<CantMaxCuentasOtrosBancos, Long>{

    @Query("SELECT cantidad " +
            "FROM CantMaxCuentasOtrosBancos cmcob " +
            "WHERE cmcob.fhbCMCOB IS NULL OR CURRENT_TIMESTAMP < cmcob.fhbCMCOB")
    int obtenerCantidadVigente();
}
