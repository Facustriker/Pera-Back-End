package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasBancoPropio;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@Repository

public interface RepositorioCantMaxCuentasBancoPropio extends BaseRepository<CantMaxCuentasBancoPropio, Long>, RepositorioCortable {


    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasBancoPropio cbp SET cbp.fhbCMCBP = ?1 " +
            "WHERE cbp.fhaCMCBP < ?1 " +
            "AND cbp.fhbCMCBP > ?1 AND cbp.fhbCMCBP < ?2 " +
            "AND cbp.fhbCMCBP IS NOT NULL")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasBancoPropio cbp SET cbp.fhbCMCBP = cbp.fhaCMCBP " +
            "WHERE cbp.fhaCMCBP > ?1 " +
            "AND cbp.fhbCMCBP < ?2 " +
            "AND cbp.fhbCMCBP IS NOT NULL")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasBancoPropio cbp SET cbp.fhaCMCBP = ?2 " +
            "WHERE cbp.fhaCMCBP > ?1 AND cbp.fhaCMCBP < ?2" +
            "AND (cbp.fhbCMCBP IS NULL OR cbp.fhbCMCBP > ?2)")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO CantMaxCuentasBancoPropio (fhaCMCBP, fhbCMCBP, cantidad) " +
            "   SELECT ?2, cbp.fhbCMCBP, cbp.cantidad " +
            "   FROM CantMaxCuentasBancoPropio cbp " +
            "   WHERE cbp.fhaCMCBP < ?1 " +
            "   AND (cbp.fhbCMCBP IS NULL OR cbp.fhbCMCBP > ?2)")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxCuentasBancoPropio cbp SET cbp.fhbCMCBP = ?1 " +
            "WHERE cbp.fhaCMCBP < ?1 " +
            "AND (cbp.fhbCMCBP IS NULL OR cbp.fhbCMCBP > ?2)")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);

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
