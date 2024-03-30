package Pera.Back.Repositories;

import Pera.Back.Entities.Usuario;
import Pera.Back.Entities.UsuarioRol;
import Pera.Back.Functionalities.CortarSuperpuestas.RepositorioCortable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface RepositorioUsuarioRol extends BaseRepository<UsuarioRol, Long>, RepositorioCortable {

    @Query("SELECT ur " +
            "FROM UsuarioRol ur " +
            "WHERE ur.usuario = :usuario " +
            "AND CURRENT_TIMESTAMP >= ur.fhaUsuarioRol " +
            "AND (ur.fhbUsuarioRol IS NULL OR CURRENT_TIMESTAMP < ur.fhbUsuarioRol)")
    public UsuarioRol getActualByUsuario(@Param("usuario") Usuario usuario);


    @Modifying
    @Transactional
    @Query("UPDATE UsuarioRol ur SET ur.fhbUsuarioRol = ?1 " +
            "WHERE ur.fhaUsuarioRol < ?1 " +
            "AND ur.fhbUsuarioRol > ?1 AND ur.fhbUsuarioRol < ?2 " +
            "AND ur.fhbUsuarioRol IS NOT NULL " +
            "AND ur.usuario.id = ?3")
    public void cortarPrevia(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UsuarioRol ur SET ur.fhbUsuarioRol = ur.fhaUsuarioRol " +
            "WHERE ur.fhaUsuarioRol > ?1 " +
            "AND ur.fhbUsuarioRol < ?2 " +
            "AND ur.fhbUsuarioRol IS NOT NULL " +
            "AND ur.usuario.id = ?3")
    public void cortarIntermedias(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UsuarioRol ur SET ur.fhaUsuarioRol = ?2 " +
            "WHERE ur.fhaUsuarioRol > ?1 AND ur.fhaUsuarioRol < ?2" +
            "AND (ur.fhbUsuarioRol IS NULL OR ur.fhbUsuarioRol > ?2) " +
            "AND ur.usuario.id = ?3")
    public void cortarPosterior(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("INSERT INTO UsuarioRol (fhaUsuarioRol, fhbUsuarioRol, rol, usuario, plan) " +
            "   SELECT ?2, ur.fhbUsuarioRol, ur.rol, ur.usuario, ur.plan " +
            "   FROM UsuarioRol ur " +
            "   WHERE ur.fhaUsuarioRol < ?1 " +
            "   AND (ur.fhbUsuarioRol IS NULL OR ur.fhbUsuarioRol > ?2) " +
            "   AND ur.usuario.id = ?3")
    public void dividirEnvolvente(Date fha, Date fhb, Long id);

    @Modifying
    @Transactional
    @Query("UPDATE UsuarioRol ur SET ur.fhbUsuarioRol = ?1 " +
            "WHERE ur.fhaUsuarioRol < ?1 " +
            "AND (ur.fhbUsuarioRol IS NULL OR ur.fhbUsuarioRol > ?2) " +
            "AND ur.usuario.id = ?3")
    public void cortarEnvolvente(Date fha, Date fhb, Long id);
}
