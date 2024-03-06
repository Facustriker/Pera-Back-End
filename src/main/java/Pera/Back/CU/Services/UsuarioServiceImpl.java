package Pera.Back.CU.Services;

import Pera.Back.CU.Entities.Usuario;
import Pera.Back.CU.Repositories.BaseRepository;
import Pera.Back.CU.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
        super(baseRepository);
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    public Usuario getByEmail(String email) throws Exception {
        try {
            Usuario u = usuarioRepository.findByEmail(email).get();
            return u;
        }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
