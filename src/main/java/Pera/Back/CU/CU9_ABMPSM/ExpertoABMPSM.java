package Pera.Back.CU.CU9_ABMPSM;

import Pera.Back.Entities.ParametroSimboloMoneda;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.RepositorioParametroSimboloMoneda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpertoABMPSM {

    @Autowired
    private final RepositorioParametroSimboloMoneda repositorioParametroSimboloMoneda;

    public Collection<DTOABMPSM> getSimbolosMoneda() throws Exception{

        List<ParametroSimboloMoneda> parametrosSistema = repositorioParametroSimboloMoneda.findAll();

        if(parametrosSistema.isEmpty()){
            throw new Exception("No se han encontrado simbolos de monedas");
        }

        List<DTOABMPSM> dto = new ArrayList<>();

        for(ParametroSimboloMoneda parametro: parametrosSistema){
            DTOABMPSM aux = DTOABMPSM.builder()
                    .nroSimbolo(parametro.getId())
                    .simbolo(parametro.getSimboloMonedaPorDefecto())
                    .fechaInicio(parametro.getFhaPSM())
                    .fechaFin(parametro.getFhbPSM())
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public void altaPSM(String simbolo) throws Exception{

        List<ParametroSimboloMoneda> parametrosSistema = repositorioParametroSimboloMoneda.findAll(Sort.by("fhaPSM"));

        if(parametrosSistema.isEmpty()){
            throw new Exception("No se han encontrado simbolos de monedas");
        }

        for(ParametroSimboloMoneda parametro: parametrosSistema){
            if(parametro.getSimboloMonedaPorDefecto().equals(simbolo)){
                throw new Exception("Error, el simbolo ingresado ya existe");
            }
        }

        ParametroSimboloMoneda psm = ParametroSimboloMoneda.builder()
                .simboloMonedaPorDefecto(simbolo)
                .fhaPSM(new Date())
                .build();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(repositorioParametroSimboloMoneda, new Date(), null, 0L);


        repositorioParametroSimboloMoneda.save(psm);

    }
}
