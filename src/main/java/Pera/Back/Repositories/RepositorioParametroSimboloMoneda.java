package Pera.Back.Repositories;

import Pera.Back.Entities.ParametroSimboloMoneda;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface RepositorioParametroSimboloMoneda extends BaseRepository<ParametroSimboloMoneda, Long>, RepositorioCortable {

    @Modifying
    @Transactional
    @Query("UPDATE ParametroSimboloMoneda psm SET psm.fhbPSM = ?1 " +
            "WHERE psm.fhaPSM < ?1 " +
            "AND psm.fhbPSM > ?1 AND psm.fhbPSM < ?2 " +
            "AND psm.fhbPSM IS NOT NULL")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ParametroSimboloMoneda psm SET psm.fhbPSM = psm.fhaPSM " +
            "WHERE psm.fhaPSM > ?1 " +
            "AND psm.fhbPSM < ?2 " +
            "AND psm.fhbPSM IS NOT NULL")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ParametroSimboloMoneda psm SET psm.fhaPSM = ?2 " +
            "WHERE psm.fhaPSM > ?1 AND psm.fhaPSM < ?2" +
            "AND (psm.fhbPSM IS NULL OR psm.fhbPSM > ?2)")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO ParametroSimboloMoneda (fhaPSM, fhbPSM, simboloMonedaPorDefecto) " +
            "   SELECT ?2, psm.fhbPSM, psm.simboloMonedaPorDefecto " +
            "   FROM ParametroSimboloMoneda psm " +
            "   WHERE psm.fhaPSM < ?1 " +
            "   AND (psm.fhbPSM IS NULL OR psm.fhbPSM > ?2)")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ParametroSimboloMoneda psm SET psm.fhbPSM = ?1 " +
            "WHERE psm.fhaPSM < ?1 " +
            "AND (psm.fhbPSM IS NULL OR psm.fhbPSM > ?2)")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);
    
    @Query("SELECT simboloMonedaPorDefecto " +
            "FROM ParametroSimboloMoneda psm " +
            "WHERE psm.fhaPSM <= CURRENT_TIMESTAMP " +
            "AND (psm.fhbPSM IS NULL OR CURRENT_TIMESTAMP < psm.fhbPSM)")
    String obtenerSimboloVigente();
}
