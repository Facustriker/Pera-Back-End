package Pera.Back.CU.CU23_SuscribirseAPremium;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Functionalities.RealizarPagos.AdaptadorRealizarPago;
import Pera.Back.Functionalities.RealizarPagos.DTODatosPago;
import Pera.Back.Functionalities.RealizarPagos.FactoriaARP;
import Pera.Back.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
public class ExpertoSuscribirseAPremium {

    @Autowired
    private ConfiguracionPrecioPremiumRepository configuracionPrecioPremiumRepository;

    @Autowired
    private PrecioPremiumRepository precioPremiumRepository;

    @Autowired
    private MedioDePagoRepository medioDePagoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private ConfiguracionRolRepository configuracionRolRepository;


    private PrecioPremium plan;

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
        plan = precioPremiumRepository.obtenerPorId(idPlan);

        DTOOpcionesPago dtoOpcionesPago = DTOOpcionesPago.builder()
                .idPP(plan.getId())
                .nombrePP(plan.getNombrePP())
                .descripPP(plan.getDescripcion())
                .precioPP(plan.getPrecio())
                .mediosDePago(new ArrayList<>())
                .build();

        Collection<MedioDePago> mediosDePago = medioDePagoRepository.obtenerVigentes();

        for (MedioDePago medioDePago : mediosDePago) {
            DTOMedioDePago sub = DTOMedioDePago.builder()
                    .nombreMDP(medioDePago.getNombreMDP())
                    .idMDP(medioDePago.getId())
                    .build();

            dtoOpcionesPago.addMedioDePago(sub);
        }

        return dtoOpcionesPago;
    }

    public DTORespuestaSuscripcionPremium realizarPago(DTODatosPagoSuscripcionPremium dto) throws Exception {
        if(plan == null) {
            throw new Exception("Seleccione el plan");
        }

        Long idMDP = dto.getIdMedioDePago();
        MedioDePago medioDePago = medioDePagoRepository.obtenerPorId(idMDP);

        FactoriaARP factoriaARP = FactoriaARP.getInstancia();
        AdaptadorRealizarPago adaptadorRealizarPago = factoriaARP.obtenerARP(medioDePago);

        DTODatosPago dtoDatosPago = dto.getDatosPago();
        int retAdaptador = adaptadorRealizarPago.realizarPago(dtoDatosPago);

        String mensaje = retAdaptador == 0 ? "Pago realizado exitosamente" : "No se pudo completar el pago. Inténtelo nuevamente";

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, plan.getDiasDuracion());

        Rol rolPremium = rolRepository.obtenerRolPorNombre("Premium");

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        Collection<String> permisos = new ArrayList<>();

        for (Permiso permiso : configuracionRolRepository.getPermisos(rolPremium)) {
            permisos.add(permiso.getNombrePermiso());
        }

        DTORespuestaSuscripcionPremium dtoRespuestaSuscripcionPremium = DTORespuestaSuscripcionPremium.builder()
                .exito(retAdaptador == 0)
                .mensaje(mensaje)
                .fechaFin(c.getTime())
                .permisos(permisos)
                .build();

        usuario.setRolActual(rolPremium);

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .rol(rolPremium)
                .usuario(usuario)
                .plan(plan)
                .fhaUsuarioRol(new Date())
                .fhbUsuarioRol(c.getTime())
                .build();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(usuarioRolRepository, new Date(), c.getTime(), usuario.getId());

        usuarioRol = usuarioRolRepository.save(usuarioRol);

        usuarioRol.getUsuario().setRolActual(rolPremium);

        usuarioRolRepository.save(usuarioRol);

        return dtoRespuestaSuscripcionPremium;
    }
}
