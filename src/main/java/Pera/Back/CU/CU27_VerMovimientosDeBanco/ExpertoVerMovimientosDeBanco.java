package Pera.Back.CU.CU27_VerMovimientosDeBanco;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioBanco;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioTransferencia;
import Pera.Back.Repositories.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoVerMovimientosDeBanco {


    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    private final RepositorioTransferencia repositorioTransferencia;

    private final RepositorioBanco repositorioBanco;


    public DTOMovimientosBanco filtrar(Long idBanco, DTOFiltrosMovimientosBanco filtro) throws Exception {
        Optional<Banco> banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        getCBBanquero(banco.get());

        Collection<CuentaBancaria> cuentaBancarias = repositorioCuentaBancaria.getCuentasVigentesPorBancoYNroCB(banco.get(), filtro.getNroCBFiltro());

        DTOMovimientosBanco dto = DTOMovimientosBanco.builder()
                .nombreBanco(banco.get().getNombreBanco())
                .build();

        for (Transferencia transferencia : repositorioTransferencia.getTransferenciasPorCuentaBancaria(cuentaBancarias, filtro.getFechaDesde(), filtro.getFechaHasta())) {
            CuentaBancaria cbOrigen = transferencia.getOrigen();
            CuentaBancaria cbDestino = transferencia.getDestino();

            if (!filtro.emisiones && cbOrigen == null) continue;
            if (!filtro.recepciones && cbDestino == null) continue;
            if (!filtro.transferencias && cbOrigen != null && cbDestino != null) continue;

            DTODetallesMovimientosBanco detalle = DTODetallesMovimientosBanco.builder()
                    .nroTransferencia(transferencia.getId())
                    .fhTransferencia(transferencia.getFhTransferencia())
                    .nroCBOrigen(cbOrigen != null ? cbOrigen.getId() : null)
                    .nroCBDestino(cbDestino != null ? cbDestino.getId() : null)
                    .nombreTitularDestino(cbDestino != null ? cbDestino.getTitular().getNombreUsuario() : null)
                    .nombreTitularOrigen(cbOrigen != null ? cbOrigen.getTitular().getNombreUsuario() : null)
                    .monto(transferencia.getMontoTransferencia())
                    .anulada(transferencia.isAnulada())
                    .build();

            dto.addDetalle(detalle);
        }

        return dto;
    }

    public DTODetallesMovimientosBancoSeleccionado getDetalle(Long nroTransferencia) throws Exception {

        Transferencia transferencia = getTransferencia(nroTransferencia);

        //Mientras las transferencias sean entre CBs de un mismo banco, esto está bien
        Banco banco = transferencia.getDestino().getBanco();

        getCBBanquero(banco);

        DTODetallesMovimientosBancoSeleccionado dto = DTODetallesMovimientosBancoSeleccionado.builder()
                .nroTransferencia(transferencia.getId())
                .build();

        if(transferencia.getOrigen() == null) {
            dto.setTipo("emisión");
            dto.setResponsable(((Emision)transferencia).getResponsable().getNombreUsuario());
        } else {
            if(transferencia.getDestino() == null) {
                dto.setTipo("recepción");
            } else {
                dto.setTipo("transferencia");
            }
        }

        dto.setFhTransferencia(transferencia.getFhTransferencia());

        CuentaBancaria cbOrigen = transferencia.getOrigen();
        CuentaBancaria cbDestino = transferencia.getDestino();

        dto.setNroCBOrigen(cbOrigen != null ? cbOrigen.getId() : null);
        dto.setNombreTitularOrigen(cbOrigen != null ? cbOrigen.getTitular().getNombreUsuario() : null);
        dto.setNroCBDestino(cbDestino != null ? cbDestino.getId() : null);
        dto.setNombreTitularDestino(cbDestino != null ? cbDestino.getTitular().getNombreUsuario() : null);
        dto.setMonto(transferencia.getMontoTransferencia());
        dto.setAnulada(transferencia.isAnulada());
        dto.setMotivo(transferencia.getMotivo());

        return dto;
    }

    public void anular(Long nroTransferencia) throws Exception {

        Transferencia transferencia = getTransferencia(nroTransferencia);

        //Mientras las transferencias sean entre CBs de un mismo banco, esto está bien
        Banco banco = transferencia.getDestino().getBanco();

        getCBBanquero(banco);

        transferencia.setAnulada(true);

        Double montoTransferencia = transferencia.getMontoTransferencia();

        CuentaBancaria cbOrigen = transferencia.getOrigen();
        CuentaBancaria cbDestino = transferencia.getDestino();

        if (cbOrigen != null)
            cbOrigen.setMontoDinero(cbOrigen.getMontoDinero() + montoTransferencia);

        if (cbDestino != null)
            cbDestino.setMontoDinero(cbDestino.getMontoDinero() - montoTransferencia);

        repositorioTransferencia.save(transferencia);

    }



    private CuentaBancaria getCBBanquero(Banco banco) throws Exception {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        CuentaBancaria cuentaBanquero = null;

        for (CuentaBancaria cuentaBancaria : repositorioCuentaBancaria.obtenerCuentasBancariasVigentesPorUsuarioYBanco(usuario, banco)) {
            if(cuentaBancaria.isEsBanquero()) {
                cuentaBanquero = cuentaBancaria;
                break;
            }
        }

        if (cuentaBanquero == null) {
            throw new Exception("Debe ser un banquero para acceder a esta funcionalidad");
        }

        return cuentaBanquero;
    }

    private Transferencia getTransferencia(Long nroTransferencia) throws Exception {
        Optional<Transferencia> optTrans = repositorioTransferencia.findById(nroTransferencia);

        if(optTrans.isEmpty()) {
            throw new Exception("No se encontró la transferencia");
        }

        return optTrans.get();
    }
}
