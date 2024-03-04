package Pera.Back.CU.Services;

import Pera.Back.CU.Entities.AuthUsuario;
import Pera.Back.CU.Repositories.AuthUsuarioRepository;
import Pera.Back.CU.Repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthUsuarioServiceImpl extends BaseServiceImpl<AuthUsuario, Long> implements AuthUsuarioService {

    @Autowired
    private AuthUsuarioRepository authUsuarioRepository;

    public AuthUsuarioServiceImpl(BaseRepository<AuthUsuario, Long> baseRepository){
        super(baseRepository);
        this.authUsuarioRepository = authUsuarioRepository;
    }


}
