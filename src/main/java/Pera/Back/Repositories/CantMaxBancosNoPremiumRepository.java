package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxBancosNoPremium;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CantMaxBancosNoPremiumRepository extends BaseRepository<CantMaxBancosNoPremium, Long>{
}
