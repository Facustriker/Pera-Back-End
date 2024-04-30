package Pera.Back.Repositories;

import Pera.Back.CU.MisBancos.DTOMisBancos;
import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface RepositorioBanco extends BaseRepository<Banco, Long>{
    Optional<Banco> findBynombreBanco(String nombre);

    @Query("SELECT b " +
            "FROM Banco b " +
            "WHERE nombreBanco LIKE :nombre " +
            "AND (fhbBanco IS NULL OR fhbBanco > CURRENT_TIMESTAMP)")
    Optional<Banco> obtenerVigentePorNombre(@Param("nombre") String nombre);

    @Query("SELECT COUNT(*) " +
            "FROM Banco b " +
            "WHERE b.dueno = :Usuario ")
    int cantidadBancosPorUsuario(@Param("Usuario") Usuario dueno);

    @Query("SELECT " +
            "    new Pera.Back.CU.MisBancos.DTOMisBancos(" +
            "    b.id AS id, " +
            "    b.nombreBanco AS nombre, " +
            "    CASE WHEN d IS NULL THEN 'Banquero' ELSE 'Dueño' END AS ocupacion," +
            "    CASE WHEN b.fhbBanco < CURRENT_TIMESTAMP THEN 'Baja' WHEN b.habilitado THEN 'Habilitado' ELSE 'Deshabilitado' END AS estado " +
            ")" +
            "FROM CuentaBancaria cb " +
            "    INNER JOIN cb.banco b " +
            "    INNER JOIN cb.titular u " +
            "    LEFT JOIN b.dueno d ON d = :usuario " +
            "WHERE u = :usuario " +
            "    AND cb.esBanquero = true " +
            "    AND (b.fhbBanco IS NULL OR b.fhbBanco > CURRENT_TIMESTAMP) " +
            "    AND b.habilitado = true " +
            "    AND cb.fhaCB <= CURRENT_TIMESTAMP " +
            "    AND (cb.fhbCB IS NULL OR cb.fhbCB > CURRENT_TIMESTAMP) " +
            "    AND cb.habilitada = true")
    public Collection<DTOMisBancos> obtenerBancos(@Param("usuario") Usuario usuario);


    @Query("SELECT b " +
            "FROM Banco b " +
            "WHERE (fhbBanco IS NULL OR fhbBanco > CURRENT_TIMESTAMP) " +
            "AND habilitado = true " +
            "AND nombreBanco LIKE %:nombre%")
    Collection<Banco> buscarBancosVigentesYHabilitados(@Param("nombre") String nombreBanco);

    @Query("SELECT b " +
            "FROM Banco b " +
            "WHERE id = :nroBanco")
    Optional<Banco> getBancoPorNumeroBanco(@Param("nroBanco") Long nroBanco);

    @Query("SELECT habilitacionAutomatica " +
            "FROM Banco b " +
            "WHERE b.id = :nroBanco ")
    public boolean obtenerIsHabilitacionAutomaticaPorNroBanco(@Param("nroBanco") Long nroBanco);

    @Query("SELECT habilitado " +
            "FROM Banco b " +
            "WHERE b.id = :nroBanco ")
    public boolean obtenerIsHabilitadoPorNroBanco(@Param("nroBanco") Long nroBanco);

    @Query("SELECT nombreBanco " +
            "FROM Banco b " +
            "WHERE b.id = :nroBanco ")
    public String obtenerNombreBancoPorNroBanco(@Param("nroBanco") Long nroBanco);

    @Query("SELECT simboloMoneda " +
            "FROM Banco b " +
            "WHERE b.id = :nroBanco ")
    public String obtenerSimboloMonedaPorNroBanco(@Param("nroBanco") Long nroBanco);

    @Query("SELECT password " +
            "FROM Banco b " +
            "WHERE b.id = :nroBanco ")
    public String obtenerPasswordPorNroBanco(@Param("nroBanco") Long nroBanco);


}
