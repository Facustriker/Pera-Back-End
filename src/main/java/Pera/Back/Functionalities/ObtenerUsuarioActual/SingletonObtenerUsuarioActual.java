package Pera.Back.Functionalities.ObtenerUsuarioActual;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class SingletonObtenerUsuarioActual {
    private static SingletonObtenerUsuarioActual instancia;

    public static SingletonObtenerUsuarioActual getInstancia() {
        if (instancia == null) instancia = new SingletonObtenerUsuarioActual();
        return instancia;
    }

    public Usuario obtenerUsuarioActual() {
        return ((AuthUsuario)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsuario();
    }
}
