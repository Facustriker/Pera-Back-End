package Pera.Back.CU.CU28_VerReportes;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.PrecioPremium;
import Pera.Back.Entities.Transferencia;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpertoVerReportes {
    private final RepositorioBanco repositorioBanco;
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioUsuarioRol repositorioUsuarioRol;
    private final RepositorioPrecioPremium repositorioPrecioPremium;

    public DTOHistograma cuentasPorBanco(Date fecha, Long intervalo) {
        DTOHistograma ret = DTOHistograma.builder().build();
        HashMap<Long, Long> set = new HashMap<>();
        Long maxCBs = 0L;
        for (Banco banco : repositorioBanco.obtenerBancosVigentesAl(fecha)) {
            Long q = repositorioCuentaBancaria.getCantidadCuentasVigentesAl(banco, fecha);
            Long value = set.getOrDefault(q, 0L) + 1;
            set.put(q, value);
            if(q > maxCBs) maxCBs = q;
        }

        Long nroIntervalos = (long) Math.ceil((double) maxCBs / intervalo);
        for (int i = 0; i < nroIntervalos; i++) {
            Long rangoDesde = i * intervalo;
            Long rangoHasta = rangoDesde + intervalo;
            Long valor = 0L;

            for (Map.Entry<Long, Long> entry : set.entrySet()) {
                Long k = entry.getKey();
                Long v = entry.getValue();
                if ((k > rangoDesde || k == 0L) && k <= rangoHasta) {
                    valor += v;
                }
            }

            DTOItemHistograma item = DTOItemHistograma.builder()
                    .label(rangoDesde + "-" + rangoHasta)
                    .build();
            item.values.add(valor);
            ret.addItem(item);
        }
        return ret;
    }

    public DTOHistograma bancosAbiertosCerrados(Date fechaInicio, Date fechaFin, Long intervalo) {
        DTOHistograma ret = DTOHistograma.builder().build();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        while (calendar.getTime().before(fechaFin)) {
            Date desde = calendar.getTime();
            calendar.add(Calendar.DATE, intervalo.intValue());
            Date hasta = calendar.getTime();

            DTOItemHistograma item = DTOItemHistograma.builder()
                    .label(format.format(desde) + "-" + format.format(hasta))
                    .build();
            item.values.add(repositorioBanco.getCantidadBancosAltaEntre(desde, hasta));
            item.values.add(repositorioBanco.getCantidadBancosBajaEntre(desde, hasta));
            ret.addItem(item);
        }
        return ret;
    }

    public DTOHistograma montosTransferidos(Date fechaInicio, Date fechaFin, Long intervalo, Long nroBanco) throws Exception {
        DTOHistograma ret = DTOHistograma.builder()
                .build();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);
        Optional<Banco> optB = repositorioBanco.findById(nroBanco);
        if(optB.isEmpty()) {
            throw new Exception("No se encontró el banco");
        }
        Banco banco = optB.get();
        ret.setTitulo("Banco: " + banco.getNombreBanco());

        while (calendar.getTime().before(fechaFin)) {
            Date desde = calendar.getTime();
            calendar.add(Calendar.DATE, intervalo.intValue());
            Date hasta = calendar.getTime();

            DTOItemHistograma item = DTOItemHistograma.builder()
                    .label(format.format(desde) + "-" + format.format(hasta))
                    .build();
            Double monto = 0.0;
            for (Transferencia transferencia : repositorioBanco.getMontosTransferidos(desde, hasta)) {
                CuentaBancaria CO = transferencia.getOrigen();
                CuentaBancaria CD = transferencia.getDestino();
                if (CO != null && Objects.equals(CO.getBanco().getId(), banco.getId())
                || CD != null && Objects.equals(CD.getBanco().getId(), banco.getId())) {
                    monto += transferencia.getMontoTransferencia();
                }
            }
            item.values.add(monto.longValue());
            ret.addItem(item);
        }
        return ret;
    }

    public DTOHistograma cantidadRegistraciones(Date fechaInicio, Date fechaFin, Long intervalo) {
        DTOHistograma ret = DTOHistograma.builder().build();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaInicio);

        while (calendar.getTime().before(fechaFin)) {
            Date desde = calendar.getTime();
            calendar.add(Calendar.DATE, intervalo.intValue());
            Date hasta = calendar.getTime();

            DTOItemHistograma item = DTOItemHistograma.builder()
                    .label(format.format(desde) + "-" + format.format(hasta))
                    .build();
            item.values.add(repositorioUsuario.getCantidadUsuariosAltaEntre(desde, hasta));
            ret.addItem(item);
        }
        return ret;
    }

    public DTOHistograma cantidadSuscripciones(Date fechaInicio, Date fechaFin) {
        DTOHistograma ret = DTOHistograma.builder().build();

        for (PrecioPremium precioPremium : repositorioPrecioPremium.obtenerVigentesEntre(fechaInicio, fechaFin)) {
            DTOItemHistograma item = DTOItemHistograma.builder()
                    .label(precioPremium.getNombrePP() + " (" + precioPremium.getDiasDuracion() + " días) $" + precioPremium.getPrecio())
                    .build();
            item.values.add(repositorioUsuarioRol.getCantidadSuscripcionesEntre(fechaInicio, fechaFin, precioPremium));
            ret.addItem(item);
        }
        return ret;
    }
}
