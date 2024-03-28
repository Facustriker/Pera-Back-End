package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface CantMaxCuentasOtrosBancosRepository extends BaseRepository<CantMaxCuentasOtrosBancos, Long> {

    @Query("SELECT p " +
            "FROM CantMaxCuentasOtrosBancos p " +
            "WHERE fhaCMCOB <= CURRENT_TIMESTAMP " +
            "AND (fhbCMCOB IS NULL OR fhbCMCOB > CURRENT_TIMESTAMP)")
    Collection<CantMaxCuentasOtrosBancos> getVigentes();
}
