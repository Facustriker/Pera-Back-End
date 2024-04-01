package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RepositorioCantMaxCuentasOtrosBancos extends BaseRepository<CantMaxCuentasOtrosBancos, Long> {


    @Query("SELECT p " +
            "FROM CantMaxCuentasOtrosBancos p " +
            "WHERE fhaCMCOB <= CURRENT_TIMESTAMP " +
            "AND (fhbCMCOB IS NULL OR fhbCMCOB > CURRENT_TIMESTAMP)")
    Collection<CantMaxCuentasOtrosBancos> getVigentes();

    @Query("SELECT cantidad " +
            "FROM CantMaxCuentasOtrosBancos cmcob " +
            "WHERE cmcob.fhbCMCOB IS NULL OR CURRENT_TIMESTAMP < cmcob.fhbCMCOB")
    int obtenerCantidadVigente();

}
