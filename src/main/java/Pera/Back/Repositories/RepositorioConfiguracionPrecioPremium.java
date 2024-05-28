package Pera.Back.Repositories;

import Pera.Back.Entities.ConfiguracionPrecioPremium;
import Pera.Back.Entities.PrecioPremium;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface RepositorioConfiguracionPrecioPremium extends BaseRepository<ConfiguracionPrecioPremium, Long>, RepositorioCortable {

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionPrecioPremium cpp SET cpp.fhbCPP = ?1 " +
            "WHERE cpp.fhaCPP < ?1 " +
            "AND cpp.fhbCPP > ?1 AND cpp.fhbCPP < ?2 " +
            "AND cpp.fhbCPP IS NOT NULL")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionPrecioPremium cpp SET cpp.fhbCPP = cpp.fhaCPP " +
            "WHERE cpp.fhaCPP > ?1 " +
            "AND cpp.fhbCPP < ?2 " +
            "AND cpp.fhbCPP IS NOT NULL")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionPrecioPremium cpp SET cpp.fhaCPP = ?2 " +
            "WHERE cpp.fhaCPP > ?1 AND cpp.fhaCPP < ?2" +
            "AND (cpp.fhbCPP IS NULL OR cpp.fhbCPP > ?2)")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO ConfiguracionPrecioPremium (fhaCPP, fhbCPP) " +
            "   SELECT ?2, cpp.fhbCPP" +
            "   FROM ConfiguracionPrecioPremium cpp " +
            "   WHERE cpp.fhaCPP < ?1 " +
            "   AND (cpp.fhbCPP IS NULL OR cpp.fhbCPP > ?2)")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionPrecioPremium cpp SET cpp.fhbCPP = ?1 " +
            "WHERE cpp.fhaCPP < ?1 " +
            "AND (cpp.fhbCPP IS NULL OR cpp.fhbCPP > ?2)")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);

    @Query("SELECT pp " +
            "FROM ConfiguracionPrecioPremium cpp " +
            "INNER JOIN cpp.precios pp " +
            "WHERE CURRENT_TIMESTAMP >= cpp.fhaCPP AND (cpp.fhbCPP IS NULL OR CURRENT_TIMESTAMP < cpp.fhbCPP)")
    Collection<PrecioPremium> obtenerPPVigentes();

    @Query("SELECT cpp " +
            "FROM ConfiguracionPrecioPremium cpp " +
            "WHERE CURRENT_TIMESTAMP >= cpp.fhaCPP AND (cpp.fhbCPP IS NULL OR CURRENT_TIMESTAMP < cpp.fhbCPP)")
    ConfiguracionPrecioPremium obtenerCPPVigente();

    @Query("SELECT cpp " +
            "FROM ConfiguracionPrecioPremium cpp " +
            "WHERE cpp.fhaCPP = :fhbInsertada AND (:fhbAnterior IS NULL AND cpp.fhbCPP IS NULL OR cpp.fhbCPP = :fhbAnterior)")
    ConfiguracionPrecioPremium obtenerCPPDividida(Date fhbInsertada, Date fhbAnterior);

    @Query("SELECT cpp " +
            "FROM ConfiguracionPrecioPremium cpp " +
            "ORDER BY cpp.fhaCPP")
    Collection<ConfiguracionPrecioPremium> findAllOrdenadas();

}
