package Pera.Back.CU.CU9_ABMPSM;

import Pera.Back.Repositories.RepositorioParametroSimboloMoneda;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertoABMPSM {

    @Autowired
    private final RepositorioParametroSimboloMoneda repositorioParametroSimboloMoneda;

    public DTOABMPSM getSimbolosMoneda(){

        DTOABMPSM dto = DTOABMPSM.builder()
                .build();

        return dto;
    }
}
