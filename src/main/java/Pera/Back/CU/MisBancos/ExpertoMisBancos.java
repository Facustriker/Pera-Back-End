package Pera.Back.CU.MisBancos;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.BancoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExpertoMisBancos {

    @Autowired
    private final BancoRepository bancoRepository;

    public Collection<DTOMisBancos> obtenerBancos() throws Exception{

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        return bancoRepository.obtenerBancos(usuario);

    }

}
