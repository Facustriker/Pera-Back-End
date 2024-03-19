package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.Entities.AuthUsuario;
import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import Pera.Back.Repositories.BancoRepository;
import Pera.Back.Repositories.UsuarioRepository;
import Pera.Back.Singletons.SingletonObtenerUsuarioActual;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ExpertoCrearBanco {

    private final BancoRepository bancoRepository;


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


        Banco banco = Banco.builder()
                .nombreBanco(request.getNombre())
                .simboloMoneda(request.getSimboloMoneda())
                .habilitacionAutomatica(request.isHabilitacionAutomatica())
                .habilitado(habilitado)
                .password(password)
                .build();

        bancoRepository.save(banco);

        return "";
    }
}
