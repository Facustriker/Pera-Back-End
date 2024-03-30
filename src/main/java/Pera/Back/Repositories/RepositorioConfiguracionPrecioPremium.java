package Pera.Back.Repositories;

import Pera.Back.Entities.ConfiguracionPrecioPremium;
import Pera.Back.Entities.PrecioPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RepositorioConfiguracionPrecioPremium extends BaseRepository<ConfiguracionPrecioPremium, Long> {

    @Query("SELECT pp " +
            "FROM ConfiguracionPrecioPremium cpp " +
            "INNER JOIN cpp.precios pp " +
            "WHERE CURRENT_TIMESTAMP >= cpp.fhaCPP AND (cpp.fhbCPP IS NULL OR CURRENT_TIMESTAMP < cpp.fhbCPP)")
    Collection<PrecioPremium> obtenerPPVigentes();

}
