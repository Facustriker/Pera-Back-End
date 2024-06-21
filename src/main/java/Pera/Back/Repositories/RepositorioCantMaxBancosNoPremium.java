package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxBancosNoPremium;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface RepositorioCantMaxBancosNoPremium extends BaseRepository<CantMaxBancosNoPremium, Long>, RepositorioCortable {


    @Modifying
    @Transactional
    @Query("UPDATE CantMaxBancosNoPremium bnp SET bnp.fhbCMBNP = ?1 " +
            "WHERE bnp.fhaCMBNP < ?1 " +
            "AND bnp.fhbCMBNP > ?1 AND bnp.fhbCMBNP < ?2 " +
            "AND bnp.fhbCMBNP IS NOT NULL")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxBancosNoPremium bnp SET bnp.fhbCMBNP = bnp.fhaCMBNP " +
            "WHERE bnp.fhaCMBNP > ?1 " +
            "AND bnp.fhbCMBNP < ?2 " +
            "AND bnp.fhbCMBNP IS NOT NULL")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxBancosNoPremium bnp SET bnp.fhaCMBNP = ?2 " +
            "WHERE bnp.fhaCMBNP > ?1 AND bnp.fhaCMBNP < ?2" +
            "AND (bnp.fhbCMBNP IS NULL OR bnp.fhbCMBNP > ?2)")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO CantMaxBancosNoPremium (fhaCMBNP, fhbCMBNP, cantidad) " +
            "   SELECT ?2, bnp.fhbCMBNP, bnp.cantidad " +
            "   FROM CantMaxBancosNoPremium bnp " +
            "   WHERE bnp.fhaCMBNP < ?1 " +
            "   AND (bnp.fhbCMBNP IS NULL OR bnp.fhbCMBNP > ?2)")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE CantMaxBancosNoPremium bnp SET bnp.fhbCMBNP = ?1 " +
            "WHERE bnp.fhaCMBNP < ?1 " +
            "AND (bnp.fhbCMBNP IS NULL OR bnp.fhbCMBNP > ?2)")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);


    @Query("SELECT cantidad " +
            "FROM CantMaxBancosNoPremium cmbnp " +
            "WHERE cmbnp.fhbCMBNP IS NULL OR CURRENT_TIMESTAMP < cmbnp.fhbCMBNP")
    int obtenerCantidadVigente();
}
