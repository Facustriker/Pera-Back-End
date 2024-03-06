package Pera.Back.CU.Services;

import Pera.Back.CU.Entities.Usuario;

public interface UsuarioService extends BaseService<Usuario, Long>{

    Usuario getByEmail(String email) throws Exception;
}
