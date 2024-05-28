package Pera.Back.CU.CU2_ABMCMCBP;

import Pera.Back.Entities.CantMaxCuentasBancoPropio;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.RepositorioCantMaxCuentasBancoPropio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertoABMCMCBP {

    @Autowired
    private final RepositorioCantMaxCuentasBancoPropio repositorioCantMaxCuentasBancoPropio;

    public Collection<DTOABMCMCBP> getCantidadesCBP() throws Exception{

        Collection<CantMaxCuentasBancoPropio> cantidadesSistema = repositorioCantMaxCuentasBancoPropio.findAll(Sort.by("fhaCMCBP"));

        if(cantidadesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado parametros");
        }

        Collection<DTOABMCMCBP> dto = new ArrayList<>();

        for(CantMaxCuentasBancoPropio cantMax: cantidadesSistema){
            DTOABMCMCBP aux = DTOABMCMCBP.builder()
                    .idCMCBP(cantMax.getId())
                    .fechaInicio(cantMax.getFhaCMCBP())
                    .fechaFin(cantMax.getFhbCMCBP())
                    .cantidad(String.valueOf(cantMax.getCantidad()))
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public void confirmarCBP(DTOABMCMCBP dto) throws Exception{

        if(!(esNumero(dto.getCantidad()))){
            throw new Exception("Error, ingrese una cantidad valida");
        }

        CantMaxCuentasBancoPropio cantidadNueva = CantMaxCuentasBancoPropio.builder()
                .fhaCMCBP(dto.getFechaInicio())
                .fhbCMCBP(dto.getFechaFin())
                .cantidad(Integer.parseInt(dto.getCantidad()))
                .build();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(repositorioCantMaxCuentasBancoPropio, dto.getFechaInicio(), dto.getFechaFin(), 0L);


        repositorioCantMaxCuentasBancoPropio.save(cantidadNueva);
    }

    public static boolean esNumero(String dato){
        String patron = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patron);

        return pattern.matcher(dato).matches();
    }
}
