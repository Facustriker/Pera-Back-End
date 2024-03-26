package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasBancoPropio;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CantMaxCuentasBancoPropioRepository extends BaseRepository<CantMaxCuentasBancoPropio, Long>{

    //Collection<CantMaxCuentasBancoPropio> getVigentes();
}
