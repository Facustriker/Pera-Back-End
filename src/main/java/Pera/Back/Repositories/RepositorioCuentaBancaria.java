package Pera.Back.Repositories;

import Pera.Back.CU.CU14_AdministrarCuentaBancaria.DTOAdministrarCuentaBancaria;
import Pera.Back.CU.MisCuentasBancarias.DTOMisCuentasBancarias;
import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface RepositorioCuentaBancaria extends BaseRepository<CuentaBancaria, Long>{

    @Query("SELECT COUNT(*) " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.titular = :Usuario ")
    int cantidadCuentasBancariasPorUsuario(@Param("Usuario") Usuario usuario);

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

    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND (:nroCB IS NULL OR id = :nroCB) " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Collection<CuentaBancaria> getCuentasVigentesPorBancoYNroCB(@Param("banco") Banco banco, @Param("nroCB") Long nroCB);

    @Query("SELECT CASE WHEN (COUNT(cb) > 0) THEN false ELSE true END " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND alias LIKE :alias " +
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


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE id = :nroCB " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Optional<CuentaBancaria> getCuentaVigentePorNumeroCuenta(@Param("nroCB") Long nroCB);


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.titular = :usuario " +
            "AND cb.banco = :banco " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP) " +
            "AND habilitada = true")
    Collection<CuentaBancaria> obtenerCuentasBancariasVigentesPorUsuarioYBanco(@Param("usuario") Usuario usuario, @Param("banco") Banco banco);

    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.titular = :usuario " +
            "AND cb.banco = :banco " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP) ")
    Collection<CuentaBancaria> obtenerCuentasBancariasPorUsuarioYBanco(@Param("usuario") Usuario usuario, @Param("banco") Banco banco);


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE id = :nroCB " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Optional<CuentaBancaria> obtenerCuentaVigentePorNumeroCuenta(@Param("nroCB") Long nroCB);


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.alias = :alias " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Optional<CuentaBancaria> obtenerCuentaVigentePorAliasUsuario(@Param("alias") String alias);
    
    @Query("SELECT alias " +
            "FROM CuentaBancaria cb " +
            "WHERE fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)")
    Collection<String> obtenerAliasCuentasBancariasVigentes();


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP) " +
            "AND esBanquero = true")
    Collection<CuentaBancaria> getCuentasBanqueroVigentesPorBanco(@Param("banco") Banco banco);


    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)" +
            "AND titular.nombreUsuario LIKE %:busqueda%")
    Collection<CuentaBancaria> buscarCuentasVigentesPorBanco(@Param("banco") Banco banco, @Param("busqueda") String busqueda);

    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.titular = :usuario " +
            "AND fhaCB <= CURRENT_TIMESTAMP " +
            "AND (fhbCB IS NULL OR fhbCB > CURRENT_TIMESTAMP)" )
    Collection<CuentaBancaria> buscarCuentasVigentesPorUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT cb " +
            "FROM CuentaBancaria cb " +
            "WHERE banco = :banco " +
            "AND (:nroCB IS NULL OR cb.id = :nroCB)")
    Collection<CuentaBancaria> getCuentasPorBancoYNroCB(@Param("banco") Banco banco, @Param("nroCB") Long nroCB);

    @Query("SELECT COUNT(*) " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.banco = :banco " +
            "AND CAST(fhaCB as date) <= :fecha " +
            "AND (fhbCB IS NULL OR CAST(fhbCB as date) > :fecha)")
    Long getCantidadCuentasVigentesAl(@Param("banco") Banco banco, @Param("fecha")Date fecha);
}
