package Pera.Back.Functionalities.ActualizarRol;

import Pera.Back.Entities.Rol;
import Pera.Back.Entities.Usuario;
import Pera.Back.Entities.UsuarioRol;
import Pera.Back.Repositories.UsuarioRepository;
import Pera.Back.Repositories.UsuarioRolRepository;

public class SingletonActualizarRol {
    private static SingletonActualizarRol instancia;

    public static SingletonActualizarRol getInstancia() {
        if (instancia == null) instancia = new SingletonActualizarRol();
        return instancia;
    }

    public Rol actualizarRol(UsuarioRolRepository repo, Usuario usuario) {
        UsuarioRol usuarioRol = repo.getActualByUsuario(usuario);
        Rol rol = usuarioRol.getRol();
        usuarioRol.getUsuario().setRolActual(rol);
        repo.save(usuarioRol);
        return rol;
    }

}
