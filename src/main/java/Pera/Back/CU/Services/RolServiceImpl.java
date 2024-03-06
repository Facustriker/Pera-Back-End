package Pera.Back.CU.Services;

import Pera.Back.CU.Entities.Rol;
import Pera.Back.CU.Repositories.BaseRepository;
import Pera.Back.CU.Repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl extends BaseServiceImpl<Rol, Long> implements RolService {

    @Autowired
    private RolRepository rolRepository;

    public RolServiceImpl(BaseRepository<Rol, Long> baseRepository) {
        super(baseRepository);
        this.rolRepository = rolRepository;
    }

}
