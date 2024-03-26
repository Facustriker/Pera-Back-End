package Pera.Back.Repositories;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CantMaxCuentasOtrosBancosRepository extends BaseRepository<CantMaxCuentasOtrosBancos, Long>{

    //Collection<CantMaxCuentasOtrosBancos> getVigentes();
}
