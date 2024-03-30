package Pera.Back.Repositories;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends BaseRepository<CuentaBancaria, Long>{

    @Query("SELECT COUNT(*) " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.titular = :Usuario ")
    int cantidadCuentasBancariasPorUsuario(@Param("Usuario") Usuario usuario);

    @Query("SELECT montoDinero " +
            "FROM CuentaBancaria cb " +
            "WHERE cb.banco = :nroBanco ")
    double montoDineroPorNroBanco(@Param("nroBanco") Long nroBanco);

}
