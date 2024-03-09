package Pera.Back.Repositories;

import Pera.Back.Entities.Rol;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends BaseRepository<Rol, Long> {

    public Rol findByNombreRol(String nombreRol);
}
