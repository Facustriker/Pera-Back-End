package Pera.Back.CU.MisBancos;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
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

    public Collection<DTOMisBancos> obtenerBancos(Usuario usuario) throws Exception{
        ArrayList<DTOMisBancos> dto = new ArrayList<>();

        Collection<Banco> bancos = bancoRepository.obtenerBancos(usuario);

        for (Banco b: bancos) {
            new DTOMisBancos();
            DTOMisBancos aux = DTOMisBancos.builder()
                .id(b.getId())
                .nombre(b.getNombreBanco())
                .ocupacion(b.getDueno().getRolActual().getNombreRol())
                .estado(b.getHabilitado().toString())
                .build();

            dto.add(aux);
        }

        return dto;

    }

}
