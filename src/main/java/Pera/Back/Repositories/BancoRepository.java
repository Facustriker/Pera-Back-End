package Pera.Back.Repositories;

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
            "    new ruta.al.dto.main.MisBancos.DTOBanco(" +
            "    b.id AS id, " +
            "    b.nombre AS nombre, " +
            "    CASE WHEN dueno IS NULL THEN 'Banquero' ELSE 'Dueño' END AS ocupacion," +
            "    CASE WHEN b.fhbBanco < CURRENT_TIMESTAMP THEN 'Baja' WHEN b.habilitado THEN 'Habilitado' ELSE 'Deshabilitado' END AS estado" +
            ")" +
            "FROM CuentaBancaria cb " +
            "    INNER JOIN cb.banco b " +
            "    INNER JOIN cb.usuario u " +
            "    LEFT JOIN b.usuario dueno " +
            "WHERE u = :usuario " +
            "    AND (dueno IS NULL OR dueno = :usuario)")
    public Collection<Banco> obtenerBancos(@Param("usuario")Usuario usuario);
}
