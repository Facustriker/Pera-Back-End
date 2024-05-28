package Pera.Back.Repositories;

import Pera.Back.Entities.ConfiguracionPrecioPremium;
import Pera.Back.Entities.ConfiguracionRol;
import Pera.Back.Entities.Permiso;
import Pera.Back.Entities.Rol;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface RepositorioConfiguracionRol extends BaseRepository<ConfiguracionRol, Long>, RepositorioCortable {


    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionRol cr SET cr.fhbCR = ?1 " +
            "WHERE cr.fhaCR < ?1 " +
            "AND cr.fhbCR > ?1 AND cr.fhbCR < ?2 " +
            "AND cr.fhbCR IS NOT NULL " +
            "AND cr.rol.id = ?3")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionRol cr SET cr.fhbCR = cr.fhaCR " +
            "WHERE cr.fhaCR > ?1 " +
            "AND cr.fhbCR < ?2 " +
            "AND cr.fhbCR IS NOT NULL " +
            "AND cr.rol.id = ?3")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionRol cr SET cr.fhaCR = ?2 " +
            "WHERE cr.fhaCR > ?1 AND cr.fhaCR < ?2" +
            "AND (cr.fhbCR IS NULL OR cr.fhbCR > ?2) " +
            "AND cr.rol.id = ?3")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO ConfiguracionRol (fhaCR, fhbCR) " +
            "   SELECT ?2, cr.fhbCR" +
            "   FROM ConfiguracionRol cr " +
            "   WHERE cr.fhaCR < ?1 " +
            "   AND (cr.fhbCR IS NULL OR cr.fhbCR > ?2) " +
            "   AND cr.rol.id = ?3")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE ConfiguracionRol cr SET cr.fhbCR = ?1 " +
            "WHERE cr.fhaCR < ?1 " +
            "AND (cr.fhbCR IS NULL OR cr.fhbCR > ?2) " +
            "AND cr.rol.id = ?3")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);


    @Query("SELECT p " +
            "FROM ConfiguracionRol cr " +
            "INNER JOIN cr.permisos p " +
            "WHERE cr.rol = :rol " +
            "AND CURRENT_TIMESTAMP >= cr.fhaCR " +
            "AND (CURRENT_TIMESTAMP < cr.fhbCR OR cr.fhbCR IS NULL)")
    Collection<Permiso> getPermisos(@Param("rol") Rol rol);

    @Query("SELECT c " +
            "FROM ConfiguracionRol c " +
            "WHERE id = :nroConfig " +
            "AND fhaCR > CURRENT_TIMESTAMP AND (fhbCR IS NULL OR fhbCR > CURRENT_TIMESTAMP)")
    Optional<ConfiguracionRol> obtenerConfiguracionVigentePorNumeroConfiguracion(@Param("nroConfig") Long nroConfig);


    @Query("SELECT c " +
            "FROM ConfiguracionRol c " +
            "WHERE rol = ?1 " +
            "AND fhaCR <= CURRENT_TIMESTAMP AND (fhbCR IS NULL OR fhbCR > CURRENT_TIMESTAMP)")
    Optional<ConfiguracionRol> obtenerVigentePorRol(Rol rol);

    @Query("SELECT cr " +
            "FROM ConfiguracionRol cr " +
            "WHERE cr.fhaCR = :fhbInsertada AND (:fhbAnterior IS NULL AND cr.fhbCR IS NULL OR cr.fhbCR = :fhbAnterior)")
    ConfiguracionRol obtenerCRDividida(Date fhbInsertada, Date fhbAnterior);
}
