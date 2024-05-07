package Pera.Back.CU.CU28_VerReportes;

import Pera.Back.Entities.Banco;
import Pera.Back.Repositories.RepositorioBanco;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoVerReportes {

    @Autowired
    private final RepositorioBanco repositorioBanco;

    public DTOCuentasBancos getCuentasPorBanco(String filtro){

        DTOCuentasBancos dto = DTOCuentasBancos.builder()
                .build();

        return dto;
    }

    public DTOCantBancosAbiertosCerrados getBancosAbiertosCerrados() throws Exception{
        Collection<Banco> bancos = repositorioBanco.findAll();
        int cantBancosAbiertos = 0;
        int cantBancosCerrados = 0;

        if(bancos.isEmpty()){
            throw new Exception("Error, no se han encontrado bancos");
        }

        for(Banco banco: bancos){
            if(banco.getHabilitado()){
                cantBancosAbiertos = cantBancosAbiertos + 1;
                continue;
            }
            cantBancosCerrados = cantBancosCerrados + 1;
        }

        DTOCantBancosAbiertosCerrados dto = DTOCantBancosAbiertosCerrados.builder()
                .cantBancosAbiertos(cantBancosAbiertos)
                .cantBancosCerrados(cantBancosCerrados)
                .build();

        return dto;
    }
}
