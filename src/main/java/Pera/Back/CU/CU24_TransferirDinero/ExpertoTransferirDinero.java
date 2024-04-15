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

    private final MemoriaTransferirDinero memoria;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioTransferencia repositorioTransferencia;

    public void recordarCBOrigen(Long nroCB) throws Exception{
        CuentaBancaria cuentaOrigen = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        if (cuentaOrigen==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }
        memoria.setCuentaOrigen(cuentaOrigen);
        memoria.setAliasCBOrigen(cuentaOrigen.getAlias());
        memoria.setMoneda(cuentaOrigen.getBanco().getSimboloMoneda());
    }

    public DTOObtenerCB obtenerCB(Long nroCB) throws Exception{
        CuentaBancaria cuentaDestino = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);
        if (cuentaDestino==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria N.° " + nroCB);
        }
        memoria.setAliasCBDestino(cuentaDestino.getAlias());
        memoria.setCuentaDestino(cuentaDestino);

        CuentaBancaria cuentaOrigen = memoria.getCuentaOrigen();

        if(cuentaOrigen.getId()==cuentaDestino.getId()){
            throw new Exception("Error: No se pudo realizar la transferencia, el origen coincide con el destino");
        }

        DTOObtenerCB dto = DTOObtenerCB.builder()
                .aliasCuentaDestino(cuentaDestino.getAlias())
                .nroCuentaDestino(cuentaDestino.getId())
                .build();

        return dto;
    }

    public DTOObtenerCB obtenerCBAlias(String alias) throws Exception{
        CuentaBancaria cuentaDestino = repositorioCuentaBancaria.obtenerCuentaVigentePorAliasUsuario(alias);
        if (cuentaDestino==null) {
            throw new Exception("Error: No se encontró la cuenta bancaria con alias: " + alias);
        }
        memoria.setAliasCBDestino(cuentaDestino.getAlias());
        memoria.setCuentaDestino(cuentaDestino);

        CuentaBancaria cuentaOrigen = memoria.getCuentaOrigen();

        if(cuentaOrigen.getId()==cuentaDestino.getId()){
            throw new Exception("Error: No se pudo realizar la transferencia, el origen coincide con el destino");
        }

        DTOObtenerCB dto = DTOObtenerCB.builder()
                .aliasCuentaDestino(cuentaDestino.getAlias())
                .nroCuentaDestino(cuentaDestino.getId())
                .build();

        return dto;
    }

    public void establecerDetalles(DTODetallesTransferencia request) throws Exception{
        if (request.getMonto() < 0.0) {
            throw new Exception("Error: debe ingresar un monto valido");
        }
        if (request.getMonto() == 0.0) {
            throw new Exception("Error: debe ingresar dinero para transferir");
        }
        if((request.getMonto())>(memoria.getCuentaOrigen().getMontoDinero())){
            throw new Exception("Error: No tiene suficiente dinero en la cuenta");
        }
        memoria.setDto(request);
    }

    public DTOConfirmacionTransferencia obtenerDetalles() throws Exception{
        DTODetallesTransferencia detalles = memoria.getDto();

        if(detalles==null){
            throw new Exception("Error: no se han encontrado datos");
        }

        DTOConfirmacionTransferencia dto = DTOConfirmacionTransferencia.builder()
                .AliasCBOrigen(memoria.getAliasCBOrigen())
                .AliasCBDestino(detalles.getAliasCBDestino())
                .monto(detalles.getMonto())
                .motivo(detalles.getMotivo())
                .build();

        return dto;
    }

    public DTOConfirmacionTransferencia confirmar(Boolean valor){

        DTODetallesTransferencia detalles = memoria.getDto();
        CuentaBancaria destino = memoria.getCuentaDestino();
        CuentaBancaria origen = memoria.getCuentaOrigen();

        boolean anulada = true;

        if(valor){
            anulada = false;
        }

        Transferencia transferencia = Transferencia.builder()
                .anulada(anulada)
                .montoTransferencia(detalles.getMonto())
                .motivo(detalles.getMotivo())
                .build();

        DTOConfirmacionTransferencia dto = DTOConfirmacionTransferencia.builder()
                .AliasCBOrigen(memoria.getAliasCBOrigen())
                .AliasCBDestino(detalles.getAliasCBDestino())
                .monto(detalles.getMonto())
                .motivo(detalles.getMotivo())
                .simbolo(memoria.getMoneda())
                .build();

        destino.setMontoDinero((memoria.getCuentaDestino().getMontoDinero())+(detalles.getMonto()));
        destino = repositorioCuentaBancaria.save(destino);
        transferencia.setDestino(destino);
        origen.setMontoDinero((memoria.getCuentaOrigen().getMontoDinero())-(detalles.getMonto()));
        origen = repositorioCuentaBancaria.save(origen);
        transferencia.setOrigen(origen);
        transferencia.setFhTransferencia(new Date());
        transferencia = repositorioTransferencia.save(transferencia);

        dto.setNroTransferencia(transferencia.getId());
        return dto;
    }
}


