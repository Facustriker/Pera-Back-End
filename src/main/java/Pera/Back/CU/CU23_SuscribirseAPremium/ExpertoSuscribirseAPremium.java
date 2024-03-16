package Pera.Back.CU.CU23_SuscribirseAPremium;

import Pera.Back.Entities.PrecioPremium;
import Pera.Back.Repositories.ConfiguracionPrecioPremiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ExpertoSuscribirseAPremium {

    @Autowired
    private ConfiguracionPrecioPremiumRepository configuracionPrecioPremiumRepository;
    public Collection<DTOPlanPremium> obtenerPlanes() {
        ArrayList<DTOPlanPremium> ret = new ArrayList<>();

        Collection<PrecioPremium> pps = configuracionPrecioPremiumRepository.obtenerPPVigentes();

        for (PrecioPremium pp : pps) {
            DTOPlanPremium aux = DTOPlanPremium.builder()
                    .id(pp.getId())
                    .nombre(pp.getNombrePP())
                    .descrip(pp.getDescripcion())
                    .precio(pp.getPrecio())
                    .build();

            ret.add(aux);
        }
        return ret;
    }

    public DTOOpcionesPago obtenerMediosDePago(Long idPlan) {
        return DTOOpcionesPago.builder().build();
    }

    public DTORespuestaSuscripcionPremium realizarPago(DTODatosPagoSuscripcionPremium dto) {
        return DTORespuestaSuscripcionPremium.builder().build();
    }
}
