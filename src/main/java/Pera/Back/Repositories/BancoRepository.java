package Pera.Back.Repositories;

import Pera.Back.Entities.Banco;

import java.util.Optional;

public interface BancoRepository extends BaseRepository<Banco, Long>{
    Optional<Banco> findBynombreBanco(String nombre);
}
