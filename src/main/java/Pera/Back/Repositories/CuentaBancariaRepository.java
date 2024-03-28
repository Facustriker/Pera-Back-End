package Pera.Back.Repositories;

import Pera.Back.CU.MisCuentasBancarias.DTOMisCuentasBancarias;
import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CuentaBancariaRepository extends BaseRepository<CuentaBancaria, Long>{


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE titular = :usuario " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Collection<CuentaBancaria> getCuentasVigentesPorUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Collection<CuentaBancaria> getCuentasVigentesPorBanco(@Param("banco") Banco banco);

    @Query("SELECT CASE WHEN (COUNT(cb) > 0) THEN false ELSE true END " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND alias LIKE ':alias' " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    boolean checkAliasDisponible(@Param("banco") Banco banco, @Param("alias") String alias);

    @Query("SELECT " +
            "new Pera.Back.CU.MisCuentasBancarias.DTOMisCuentasBancarias(" +
            "    cb.id AS id, " +
            "    b.nombreBanco AS nombreBanco, " +
            "    cb.montoDinero AS monto, " +
            "    b.simboloMoneda AS moneda, " +
            "    CASE WHEN cb.fhbCB < CURRENT_TIMESTAMP THEN 'Baja' WHEN cb.habilitada THEN 'Habilitado' ELSE 'Deshabilitado' END AS estado" +
            ") " +
            "FROM CuentaBancaria cb " +
            "    INNER JOIN cb.banco b " +
            "    INNER JOIN cb.titular u " +
            "WHERE u = :usuario")
    Collection<DTOMisCuentasBancarias> obtenerCuentasBancariasUsuario(@Param("usuario") Usuario usuario);

}
