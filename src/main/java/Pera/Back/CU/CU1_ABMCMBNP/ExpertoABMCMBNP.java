package Pera.Back.CU.CU1_ABMCMBNP;

import Pera.Back.Entities.CantMaxBancosNoPremium;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.RepositorioCantMaxBancosNoPremium;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertoABMCMBNP {

    @Autowired
    private final RepositorioCantMaxBancosNoPremium repositorioCantMaxBancosNoPremium;

    public Collection<DTOABMCMBNP> getCantidadesBNP() throws Exception{

        Collection<CantMaxBancosNoPremium> cantidadesSistema = repositorioCantMaxBancosNoPremium.findAll(Sort.by("fhaCMBNP"));

        if(cantidadesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado parametros");
        }

        Collection<DTOABMCMBNP> dto = new ArrayList<>();

        for(CantMaxBancosNoPremium cantMax: cantidadesSistema){
            DTOABMCMBNP aux = DTOABMCMBNP.builder()
                    .idCMBNP(cantMax.getId())
                    .fechaInicio(cantMax.getFhaCMBNP())
                    .fechaFin(cantMax.getFhbCMBNP())
                    .cantidad(String.valueOf(cantMax.getCantidad()))
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public void confirmarBNP(DTOABMCMBNP dto) throws Exception{

        if(!(esNumero(dto.getCantidad()))){
           throw new Exception("Error, ingrese una cantidad valida");
        }

        CantMaxBancosNoPremium cantidadNueva = CantMaxBancosNoPremium.builder()
                .fhaCMBNP(dto.getFechaInicio())
                .fhbCMBNP(dto.getFechaFin())
                .cantidad(Integer.parseInt(dto.getCantidad()))
                .build();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(repositorioCantMaxBancosNoPremium, dto.getFechaInicio(), dto.getFechaFin(), 0L);


        repositorioCantMaxBancosNoPremium.save(cantidadNueva);
    }

    public static boolean esNumero(String dato){
        String patron = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patron);

        return pattern.matcher(dato).matches();
    }
}
