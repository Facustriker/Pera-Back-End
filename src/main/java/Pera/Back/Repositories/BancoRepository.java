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
public interface BancoRepository extends BaseRepository<Banco, Long>{
    Optional<Banco> findBynombreBanco(String nombre);

    @Query("SELECT COUNT(*) " +
            "FROM Banco b " +
            "WHERE b.id = :idUsuario ")
    int cantidadBancosPorIdUsuario(@Param("idUsuario") long id);

    @Query("SELECT " +
            "    new Pera.Back.CU.MisBancos.DTOMisBancos(" +
            "    b.id AS id, " +
            "    b.nombreBanco AS nombre, " +
            "    CASE WHEN d IS NULL THEN 'Banquero' ELSE 'Dueño' END AS ocupacion," +
            "    CASE WHEN b.fhbBanco < CURRENT_TIMESTAMP THEN 'Baja' WHEN b.habilitado THEN 'Habilitado' ELSE 'Deshabilitado' END AS estado" +
            ")" +
            "FROM CuentaBancaria cb " +
            "    INNER JOIN cb.banco b " +
            "    INNER JOIN cb.titular u " +
            "    LEFT JOIN b.dueno d " +
            "WHERE u = :usuario " +
            "    AND (d IS NULL OR d = :usuario)")
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
    Banco getBancoPorNumeroBanco(@Param("nroBanco") Long nroBanco);
}
