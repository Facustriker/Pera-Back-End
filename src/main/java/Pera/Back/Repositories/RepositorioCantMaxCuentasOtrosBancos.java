package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Repository
public interface RepositorioCantMaxCuentasOtrosBancos extends BaseRepository<CantMaxCuentasOtrosBancos, Long>, RepositorioCortable {

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasOtrosBancos cob SET cob.fhbCMCOB = ?1 " +
            "WHERE cob.fhaCMCOB < ?1 " +
            "AND cob.fhbCMCOB > ?1 AND cob.fhbCMCOB < ?2 " +
            "AND cob.fhbCMCOB IS NOT NULL")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasOtrosBancos cob SET cob.fhbCMCOB = cob.fhaCMCOB " +
            "WHERE cob.fhaCMCOB > ?1 " +
            "AND cob.fhbCMCOB < ?2 " +
            "AND cob.fhbCMCOB IS NOT NULL")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasOtrosBancos cob SET cob.fhaCMCOB = ?2 " +
            "WHERE cob.fhaCMCOB > ?1 AND cob.fhaCMCOB < ?2" +
            "AND (cob.fhbCMCOB IS NULL OR cob.fhbCMCOB > ?2)")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO CantMaxCuentasOtrosBancos (fhaCMCOB, fhbCMCOB, cantidad) " +
            "   SELECT ?2, cob.fhbCMCOB, cob.cantidad " +
            "   FROM CantMaxCuentasOtrosBancos cob " +
            "   WHERE cob.fhaCMCOB < ?1 " +
            "   AND (cob.fhbCMCOB IS NULL OR cob.fhbCMCOB > ?2)")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasOtrosBancos cob SET cob.fhbCMCOB = ?1 " +
            "WHERE cob.fhaCMCOB < ?1 " +
            "AND (cob.fhbCMCOB IS NULL OR cob.fhbCMCOB > ?2)")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);


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
