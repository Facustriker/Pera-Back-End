package Pera.Back.CU.CU24_TransferirDinero;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Transferencia;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioTransferencia;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpertoTransferirDinero {

    private CuentaBancaria cuentaOrigenGlobal;
    private CuentaBancaria cuentaDestinoGlobal;
    private Transferencia transferenciaGlobal;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioTransferencia repositorioTransferencia;

    public void almacenarCBOrigen(Long nroCB) throws Exception{
        CuentaBancaria cuentaOrigen = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        cuentaOrigenGlobal = cuentaOrigen;
        if (cuentaOrigen==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }
    }

    public void ingresarCB(Long nroCB) throws Exception{
        CuentaBancaria cuentaDestino = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        cuentaDestinoGlobal = cuentaDestino;
        if (cuentaDestino==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }

        crearTransferencia(cuentaOrigenGlobal, cuentaDestinoGlobal);

    }

    public void ingresarAlias(String alias) throws Exception{
        cuentaDestinoGlobal = repositorioCuentaBancaria.obtenerCuentaVigentePorAliasUsuario(alias);
        if (cuentaDestinoGlobal==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria con alias: " + alias);
        }

        crearTransferencia(cuentaOrigenGlobal, cuentaDestinoGlobal);
    }

    public DTODatosIngresarMonto getDatosIngresarMonto() throws Exception{

        String alias = cuentaDestinoGlobal.getAlias();
        Long nroCBDestino = cuentaDestinoGlobal.getId();

        DTODatosIngresarMonto dto = DTODatosIngresarMonto.builder()
                .aliasCuentaDestino(alias)
                .nroCuentaDestino(nroCBDestino)
                .build();

        return dto;
    }

    public void ingresarMontoYMotivo(DTOMontoMotivo montoMotivo) throws Exception{
        Double montoDineroOrigen = cuentaOrigenGlobal.getMontoDinero();
        Double montoIngresado = montoMotivo.getMonto();
        String motivoIngresado = montoMotivo.getMotivo();

        if(montoIngresado<= montoDineroOrigen){
            throw new Exception("Error: No tiene suficiente dinero disponible en la cuenta");
        }

        transferenciaGlobal.setMontoTransferencia(montoIngresado);
        transferenciaGlobal.setMotivo(motivoIngresado);

    }

    public DTOConfirmacionTransferencia getDatosConfirmacionTransferencia(){
        CuentaBancaria cuentaOrigen = transferenciaGlobal.getOrigen();
        Long nroCBOrigen = cuentaOrigen.getId();
        CuentaBancaria cuentaDestino = transferenciaGlobal.getDestino();
        Long nroCBDestino = cuentaDestino.getId();
        String motivo = transferenciaGlobal.getMotivo();
        Double monto = transferenciaGlobal.getMontoTransferencia();

        DTOConfirmacionTransferencia dto = DTOConfirmacionTransferencia.builder()
                .nroCBOrigen(nroCBOrigen)
                .nroCBDestino(nroCBDestino)
                .monto(monto)
                .motivo(motivo)
                .build();

        return dto;

    }

    public Long confirmar(Boolean confirmacion){
        transferenciaGlobal.setAnulada(false);
        transferenciaGlobal.setFhTransferencia(new Date());

        Double montoTransferencia = transferenciaGlobal.getMontoTransferencia();
        Double montoDineroCBOrigen = cuentaOrigenGlobal.getMontoDinero();
        cuentaOrigenGlobal.setMontoDinero((montoDineroCBOrigen)-(montoTransferencia));

        Double montoDineroCBDestino = cuentaDestinoGlobal.getMontoDinero();
        cuentaDestinoGlobal.setMontoDinero((montoDineroCBDestino)+(montoTransferencia));

        repositorioTransferencia.save(transferenciaGlobal);

        return transferenciaGlobal.getId();
    }

    public void crearTransferencia(CuentaBancaria cuentaOrigen, CuentaBancaria cuentaDestino) throws Exception{

        if(cuentaOrigen.getId()==cuentaDestino.getId()){
            throw new Exception("Error: No se pudo realizar la transferencia, el origen coincide con el destino");
        }

        Transferencia transferencia = Transferencia.builder()
                .origen(cuentaOrigen)
                .destino(cuentaDestino)
                .build();

        transferenciaGlobal = transferencia;
    }
}


