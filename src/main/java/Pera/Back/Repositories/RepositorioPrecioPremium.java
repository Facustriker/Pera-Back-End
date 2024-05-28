package Pera.Back.Repositories;

import Pera.Back.Entities.PrecioPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;

@Repository
public interface RepositorioPrecioPremium extends BaseRepository<PrecioPremium, Long> {

    @Query("SELECT pp FROM PrecioPremium pp WHERE pp.id = :id")
    PrecioPremium obtenerPorId(@Param("id") Long id);

    @Query("SELECT pp " +
            "FROM ConfiguracionPrecioPremium cpp INNER JOIN cpp.precios pp " +
            "WHERE CAST(fhaCPP as date) <= :hasta AND (fhbCPP IS NULL OR CAST(fhbCPP as date) > :desde)")
    Collection<PrecioPremium> obtenerVigentesEntre(Date desde, Date hasta);

}
