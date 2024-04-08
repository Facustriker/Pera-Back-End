package Pera.Back.Repositories;

import Pera.Back.Entities.PrecioPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPrecioPremium extends BaseRepository<PrecioPremium, Long> {

    @Query("SELECT pp FROM PrecioPremium pp WHERE pp.id = :id")
    PrecioPremium obtenerPorId(@Param("id") Long id);

}
