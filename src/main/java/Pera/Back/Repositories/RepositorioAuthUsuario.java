package Pera.Back.Repositories;

import Pera.Back.Entities.AuthUsuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioAuthUsuario extends BaseRepository<AuthUsuario, Long> {

    Optional<AuthUsuario> findByUsername(String username);
}
