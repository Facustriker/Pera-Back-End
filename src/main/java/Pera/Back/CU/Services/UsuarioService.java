package Pera.Back.CU.Services;

import Pera.Back.CU.Entities.Usuario;

public interface UsuarioService extends BaseService<Usuario, Long>{

    Usuario getByMail(String email) throws Exception;
}
