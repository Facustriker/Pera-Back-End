package Pera.Back.CU.CU28_VerReportes;

import Pera.Back.Entities.Banco;
import Pera.Back.Repositories.RepositorioBanco;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpertoVerReportes {
    private final RepositorioBanco repositorioBanco;
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    public DTOHistograma cuentasPorBanco(Date fecha, Long intervalo) {
        DTOHistograma ret = DTOHistograma.builder().build();
        HashMap<Long, Long> set = new HashMap<>();
        Long maxCBs = 0L;
        for (Banco banco : repositorioBanco.obtenerBancosVigentesAl(fecha)) {
            Long q = repositorioCuentaBancaria.getCantidadCuentasVigentesAl(banco, fecha);
            Long value = set.getOrDefault(q, 0L) + 1;
            set.put(q, value);
            if(q > maxCBs) maxCBs = q;
            System.out.println(q + " - " + value);
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
}
