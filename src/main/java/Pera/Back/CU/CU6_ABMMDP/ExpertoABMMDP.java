package Pera.Back.CU.CU6_ABMMDP;

import Pera.Back.Entities.MedioDePago;
import Pera.Back.Repositories.RepositorioMedioDePago;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoABMMDP {

    @Autowired
    private final RepositorioMedioDePago repositorioMedioDePago;

    public Collection<DTOABMMDP> getMediosPago() throws Exception{
        boolean dadoDeBaja;

        Collection<MedioDePago> mediosPagoSistema = repositorioMedioDePago.findAll();

        if(mediosPagoSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado medios de pago");
        }

        Collection<DTOABMMDP> dto = new ArrayList<>();

        for(MedioDePago mdp: mediosPagoSistema){
            if(mdp.getFhbMDP() == null){
                dadoDeBaja = false;
            }else{
                dadoDeBaja = true;
            }

            DTOABMMDP aux = DTOABMMDP.builder()
                    .nroMDP(mdp.getId())
                    .nombreMDP(mdp.getNombreMDP())
                    .fechaBajaMDP(mdp.getFhbMDP())
                    .deBaja(dadoDeBaja)
                    .build();

            dto.add(aux);
        }

        return dto;

    }

    public void altaMDP(String nombreMDP) throws Exception{

        Collection<MedioDePago> mediosPagoSistema = repositorioMedioDePago.obtenerVigentes();

        if(mediosPagoSistema.isEmpty()){
            throw new Exception("Error, no se han encontrado medios de pago");
        }

        for(MedioDePago mdp: mediosPagoSistema){
            if(mdp.getNombreMDP().equals(nombreMDP)){
                throw new Exception("Error, el nombre ingresado ya existe");
            }
        }

        MedioDePago mdp = MedioDePago.builder()
                .nombreMDP(nombreMDP)
                .descripcionMDP(nombreMDP + ", medio de pago creado el " + new Date())
                .build();

        repositorioMedioDePago.save(mdp);
    }

    public void darBajaMDP(Long nroMDP) throws Exception{

        Optional<MedioDePago> medioPago = repositorioMedioDePago.obtenerVigentePorId(nroMDP);

        if(medioPago.isEmpty()){
            throw new Exception("Error, no se han encontrado medios de pago");
        }

        medioPago.get().setFhbMDP(new Date());

        repositorioMedioDePago.save(medioPago.get());
    }
}
