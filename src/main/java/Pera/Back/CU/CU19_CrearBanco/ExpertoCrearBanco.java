package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.BancoRepository;
import Pera.Back.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoCrearBanco {

    private final BancoRepository bancoRepository;

    private final UsuarioRepository usuarioRepository;


    public String crear(DTOCrearBanco request) throws Exception{
        String password = "";
        boolean habilitado = false;

        Optional<Banco> prev = bancoRepository.findBynombreBanco(request.getNombre());
        if (prev.isPresent()){
            throw new Exception("Error, este banco ya esta registrado");
        }

        if (request.isUsarPassword()){
            password = request.getPassword();
        }

        if (request.isHabilitacionAutomatica()){
            habilitado = true;
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        dueno = usuarioRepository.findById(dueno.getId()).get();

        Banco banco = Banco.builder()
                .nombreBanco(request.getNombre())
                .simboloMoneda(request.getSimboloMoneda())
                .habilitacionAutomatica(request.isHabilitacionAutomatica())
                .habilitado(habilitado)
                .password(password)
                .dueno(dueno)
                .build();

        bancoRepository.save(banco);

        return "";
    }
}
