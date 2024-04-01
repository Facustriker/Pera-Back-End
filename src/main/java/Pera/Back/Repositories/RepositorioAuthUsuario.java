package Pera.Back.Repositories;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioAuthUsuario extends BaseRepository<AuthUsuario, Long> {

    Optional<AuthUsuario> findByUsername(String username);


    Optional<AuthUsuario> findByUsuario(Usuario usuario);
}
