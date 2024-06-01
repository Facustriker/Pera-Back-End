package Pera.Back.Repositories;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Transferencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Repository
public interface RepositorioTransferencia extends BaseRepository<Transferencia, Long> {

    @Query("SELECT t " +
            "FROM Transferencia t " +
            "WHERE (origen IN :cuentasBancarias OR destino IN :cuentasBancarias) " +
            "AND fhTransferencia >= :fechaDesde AND fhTransferencia <= :fechaHasta")
    Collection<Transferencia> getTransferenciasPorCuentaBancaria(@Param("cuentasBancarias")Collection<CuentaBancaria> cuentasBancarias, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);

    @Query("SELECT t " +
            "FROM Transferencia t " +
            "WHERE (origen = :cuentaBancaria OR destino = :cuentaBancaria) " +
            "AND fhTransferencia >= :fechaDesde AND fhTransferencia <= :fechaHasta")
    Collection<Transferencia> getTransferenciasDeCuentaBancaria(@Param("cuentaBancaria")Optional<CuentaBancaria> cuentaBancaria, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);
}
