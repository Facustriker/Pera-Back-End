package Pera.Back.CU.CU3_ABMCMCOB;

import Pera.Back.Entities.CantMaxCuentasOtrosBancos;
import Pera.Back.Repositories.RepositorioCantMaxCuentasOtrosBancos;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertoABMCMCOB {

    @Autowired
    private final RepositorioCantMaxCuentasOtrosBancos repositorioCantMaxCuentasOtrosBancos;

    public Collection<DTOABMCMCOB> getCantidadesCOB() throws Exception{

        Collection<CantMaxCuentasOtrosBancos> cantidadesSistema = repositorioCantMaxCuentasOtrosBancos.findAll();

        if(cantidadesSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado parametros");
        }

        Collection<DTOABMCMCOB> dto = new ArrayList<>();

        for(CantMaxCuentasOtrosBancos cantMax: cantidadesSistema){
            DTOABMCMCOB aux = DTOABMCMCOB.builder()
                    .idCMCOB(cantMax.getId())
                    .fechaInicio(cantMax.getFhaCMCOB())
                    .fechaFin(cantMax.getFhbCMCOB())
                    .cantidad(String.valueOf(cantMax.getCantidad()))
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public void confirmarCOB(DTOABMCMCOB dto) throws Exception{

        if(!(esNumero(dto.getCantidad()))){
            throw new Exception("Error, ingrese una cantidad valida");
        }

        CantMaxCuentasOtrosBancos cantidadNueva = CantMaxCuentasOtrosBancos.builder()
                .fhaCMCOB(dto.getFechaInicio())
                .fhbCMCOB(dto.getFechaFin())
                .cantidad(Integer.parseInt(dto.getCantidad()))
                .build();

        repositorioCantMaxCuentasOtrosBancos.save(cantidadNueva);
    }

    public static boolean esNumero(String dato){
        String patron = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patron);

        return pattern.matcher(dato).matches();
    }
}
