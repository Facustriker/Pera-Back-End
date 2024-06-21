package Pera.Back.CU.CU26_VerMovimientos;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Transferencia;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioTransferencia;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoVerMovimientos {

    @Autowired
    private final RepositorioTransferencia repositorioTransferencia;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    private Long nroCBCuentaO;

    public DTOVerMovimientos getMovimientosCuentas(Long nroCB, DTOFiltrosVerMovimientos dtoFiltros) throws Exception{
        Optional<CuentaBancaria> cuentaOrigen = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(nroCB);
        nroCBCuentaO = nroCB;

        if(cuentaOrigen.isEmpty()){
            throw new Exception("Error, no se ha encontrado la cuenta bancaria");
        }

        DTOVerMovimientos dto = DTOVerMovimientos.builder()
                .nroCuenta(nroCB)
                .build();

        Collection<Transferencia> transferencias = repositorioTransferencia.getTransferenciasDeCuentaBancaria(cuentaOrigen, dtoFiltros.getFechaDesde(), dtoFiltros.getFechaHasta());
        for (Transferencia transferencia : transferencias) {
            String tipoT;
            Long nroCBT;
            String titularCBT;
            String estadoT;

            CuentaBancaria cbOrigen = transferencia.getOrigen();
            CuentaBancaria cbDestino = transferencia.getDestino();

            if(cuentaOrigen.get().equals(cbOrigen)){ //Envio
                tipoT = "Envio";
                if(cbDestino != null) {
                    nroCBT = cbDestino.getId();
                    titularCBT = cbDestino.getTitular().getNombreUsuario();

                    if (dtoFiltros.filtroNombreUsuario.isEmpty() && dtoFiltros.filtroNroCB.isEmpty()) {

                    } else if (dtoFiltros.filtroNombreUsuario.isEmpty() && !dtoFiltros.filtroNroCB.isEmpty()) {
                        if (!(nroCBT == Long.parseLong(dtoFiltros.filtroNroCB))) {
                            continue;
                        }
                    } else if (!dtoFiltros.filtroNombreUsuario.isEmpty() && dtoFiltros.filtroNroCB.isEmpty()) {
                        if (!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase()))) {
                            continue;
                        }
                    } else {
                        if (!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase())) && (nroCBT == Long.parseLong(dtoFiltros.filtroNroCB))) {
                            continue;
                        }
                    }
                } else {
                    nroCBT = null;
                    titularCBT = "Banco";
                    if(!dtoFiltros.filtroNombreUsuario.equals("Banco") && !dtoFiltros.filtroNombreUsuario.isEmpty() || !dtoFiltros.filtroNroCB.isEmpty()) {
                        continue;
                    }
                }
            }else{
                if(cuentaOrigen.get().equals(cbDestino)){ //Recepcion
                    tipoT = "Recepcion";
                    if(cbOrigen==null){
                        nroCBT = null;
                        titularCBT = "Banco";
                        if(!dtoFiltros.filtroNombreUsuario.equals("Banco") && !dtoFiltros.filtroNombreUsuario.isEmpty() || !dtoFiltros.filtroNroCB.isEmpty()) {
                            continue;
                        }
                    }else{
                        nroCBT = transferencia.getOrigen().getId();
                        titularCBT = transferencia.getOrigen().getTitular().getNombreUsuario();
                        if(dtoFiltros.filtroNombreUsuario.isEmpty() && dtoFiltros.filtroNroCB.isEmpty()) {

                        } else if(dtoFiltros.filtroNombreUsuario.isEmpty() && !dtoFiltros.filtroNroCB.isEmpty()) {
                            if(!(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                continue;
                            }
                        } else if(!dtoFiltros.filtroNombreUsuario.isEmpty() && dtoFiltros.filtroNroCB.isEmpty()) {
                            if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase()))){
                                continue;
                            }
                        } else  {
                            if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase())) && (nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                continue;
                            }
                        }
                    }
                }else{
                    throw new Exception("No hay transferencias");
                }
            }

            if(transferencia.isAnulada()){
                estadoT = "Anulada";
            }else{
                estadoT = "Vigente";
            }

            DTODetallesVerMovimientos aux = DTODetallesVerMovimientos.builder()
                    .nroTransferencia(transferencia.getId())
                    .fechaTransferencia(transferencia.getFhTransferencia())
                    .tipoTransferencia(tipoT)
                    .nroCBTransferencia(nroCBT)
                    .titularCB(titularCBT)
                    .montoTransferencia(transferencia.getMontoTransferencia())
                    .estadoTransferencia(estadoT)
                    .build();

            dto.addDetalle(aux);
        }



        /*if(dtoFiltros.filtroNombreUsuario.equals("")){
            if(dtoFiltros.filtroNroCB.equals("")){
                //NO HAY FILTROS - HECHO -
                for (Transferencia transferencia : repositorioTransferencia.getTransferenciasDeCuentaBancaria(cuentaOrigen, dtoFiltros.getFechaDesde(), dtoFiltros.getFechaHasta())) {
                    String tipoT;
                    Long nroCBT;
                    String titularCBT;
                    String estadoT;

                    CuentaBancaria cbOrigen = transferencia.getOrigen();
                    CuentaBancaria cbDestino = transferencia.getDestino();

                    if(cuentaOrigen.get().equals(cbOrigen)){ //Envio
                        tipoT = "Envio";
                        nroCBT = transferencia.getDestino().getId();
                        titularCBT = transferencia.getDestino().getTitular().getNombreUsuario();
                    }else{
                        if(cuentaOrigen.get().equals(cbDestino)){ //Recepcion
                            tipoT = "Recepcion";
                            if(cbOrigen==null){
                                nroCBT = 0L;
                                titularCBT = "Banco";
                            }else{
                                nroCBT = transferencia.getOrigen().getId();
                                titularCBT = transferencia.getOrigen().getTitular().getNombreUsuario();
                            }
                        }else{
                            throw new Exception("No hay transferencias");
                        }
                    }

                    if(transferencia.isAnulada()){
                        estadoT = "Anulada";
                    }else{
                        estadoT = "Vigente";
                    }

                    DTODetallesVerMovimientos aux = DTODetallesVerMovimientos.builder()
                            .nroTransferencia(transferencia.getId())
                            .fechaTransferencia(transferencia.getFhTransferencia())
                            .tipoTransferencia(tipoT)
                            .nroCBTransferencia(nroCBT)
                            .titularCB(titularCBT)
                            .montoTransferencia(transferencia.getMontoTransferencia())
                            .estadoTransferencia(estadoT)
                            .build();

                    dto.addDetalle(aux);
                }
            }else{ //EL FILTRO ES NUMERO - HECHO -
                for (Transferencia transferencia : repositorioTransferencia.getTransferenciasDeCuentaBancaria(cuentaOrigen, dtoFiltros.getFechaDesde(), dtoFiltros.getFechaHasta())) {
                    String tipoT;
                    Long nroCBT;
                    String titularCBT;
                    String estadoT;

                    CuentaBancaria cbOrigen = transferencia.getOrigen();
                    CuentaBancaria cbDestino = transferencia.getDestino();

                    if(cuentaOrigen.get().equals(cbOrigen)){ //Envio
                        tipoT = "Envio";
                        nroCBT = transferencia.getDestino().getId();
                        titularCBT = transferencia.getDestino().getTitular().getNombreUsuario();
                        if(!(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                            continue;
                        }
                    }else{
                        if(cuentaOrigen.get().equals(cbDestino)){ //Recepcion
                            tipoT = "Recepcion";
                            if(cbOrigen==null){
                                nroCBT = 0L;
                                titularCBT = "Banco";
                                if(!(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                    continue;
                                }
                            }else{
                                nroCBT = transferencia.getOrigen().getId();
                                titularCBT = transferencia.getOrigen().getTitular().getNombreUsuario();
                                if(!(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                    continue;
                                }
                            }
                        }else{
                            throw new Exception("No hay transferencias");
                        }
                    }

                    if(transferencia.isAnulada()){
                        estadoT = "Anulada";
                    }else{
                        estadoT = "Vigente";
                    }

                    DTODetallesVerMovimientos aux = DTODetallesVerMovimientos.builder()
                            .nroTransferencia(transferencia.getId())
                            .fechaTransferencia(transferencia.getFhTransferencia())
                            .tipoTransferencia(tipoT)
                            .nroCBTransferencia(nroCBT)
                            .titularCB(titularCBT)
                            .montoTransferencia(transferencia.getMontoTransferencia())
                            .estadoTransferencia(estadoT)
                            .build();

                    dto.addDetalle(aux);
                }
            }
        }else{ //FILTRE SOLO USANDO EL NOMBRE - HECHO -
            if(dtoFiltros.filtroNroCB.equals("")){
                for (Transferencia transferencia : repositorioTransferencia.getTransferenciasDeCuentaBancaria(cuentaOrigen, dtoFiltros.getFechaDesde(), dtoFiltros.getFechaHasta())) {
                    String tipoT;
                    Long nroCBT;
                    String titularCBT;
                    String estadoT;

                    CuentaBancaria cbOrigen = transferencia.getOrigen();
                    CuentaBancaria cbDestino = transferencia.getDestino();

                    if(cuentaOrigen.get().equals(cbOrigen)){ //Envio
                        tipoT = "Envio";
                        nroCBT = transferencia.getDestino().getId();
                        titularCBT = transferencia.getDestino().getTitular().getNombreUsuario();
                        if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase()))){
                            continue;
                        }
                    }else{
                        if(cuentaOrigen.get().equals(cbDestino)){ //Recepcion
                            tipoT = "Recepcion";
                            if(cbOrigen==null){
                                nroCBT = 0L;
                                titularCBT = "Banco";
                                if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase()))){
                                    continue;
                                }
                            }else{
                                nroCBT = transferencia.getOrigen().getId();
                                titularCBT = transferencia.getOrigen().getTitular().getNombreUsuario();
                                if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase()))){
                                    continue;
                                }
                            }
                        }else{
                            throw new Exception("No hay transferencias");
                        }
                    }

                    if(transferencia.isAnulada()){
                        estadoT = "Anulada";
                    }else{
                        estadoT = "Vigente";
                    }

                    DTODetallesVerMovimientos aux = DTODetallesVerMovimientos.builder()
                            .nroTransferencia(transferencia.getId())
                            .fechaTransferencia(transferencia.getFhTransferencia())
                            .tipoTransferencia(tipoT)
                            .nroCBTransferencia(nroCBT)
                            .titularCB(titularCBT)
                            .montoTransferencia(transferencia.getMontoTransferencia())
                            .estadoTransferencia(estadoT)
                            .build();

                    dto.addDetalle(aux);
                }
            }else{ //HAY AMBOS FILTROS
                for (Transferencia transferencia : repositorioTransferencia.getTransferenciasDeCuentaBancaria(cuentaOrigen, dtoFiltros.getFechaDesde(), dtoFiltros.getFechaHasta())) {
                    String tipoT;
                    Long nroCBT;
                    String titularCBT;
                    String estadoT;

                    CuentaBancaria cbOrigen = transferencia.getOrigen();
                    CuentaBancaria cbDestino = transferencia.getDestino();

                    if(cuentaOrigen.get().equals(cbOrigen)){ //Envio
                        tipoT = "Envio";
                        nroCBT = transferencia.getDestino().getId();
                        titularCBT = transferencia.getDestino().getTitular().getNombreUsuario();
                        if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase())) && (nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                            continue;
                        }
                    }else{
                        if(cuentaOrigen.get().equals(cbDestino)){ //Recepcion
                            tipoT = "Recepcion";
                            if(cbOrigen==null){
                                nroCBT = 0L;
                                titularCBT = "Banco";
                                if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase())) && !(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                    continue;
                                }
                            }else{
                                nroCBT = transferencia.getOrigen().getId();
                                titularCBT = transferencia.getOrigen().getTitular().getNombreUsuario();
                                if(!(titularCBT.toUpperCase().contains(dtoFiltros.filtroNombreUsuario.toUpperCase())) && !(nroCBT==Long.parseLong(dtoFiltros.filtroNroCB))){
                                    continue;
                                }
                            }
                        }else{
                            throw new Exception("No hay transferencias");
                        }
                    }

                    if(transferencia.isAnulada()){
                        estadoT = "Anulada";
                    }else{
                        estadoT = "Vigente";
                    }

                    DTODetallesVerMovimientos aux = DTODetallesVerMovimientos.builder()
                            .nroTransferencia(transferencia.getId())
                            .fechaTransferencia(transferencia.getFhTransferencia())
                            .tipoTransferencia(tipoT)
                            .nroCBTransferencia(nroCBT)
                            .titularCB(titularCBT)
                            .montoTransferencia(transferencia.getMontoTransferencia())
                            .estadoTransferencia(estadoT)
                            .build();

                    dto.addDetalle(aux);
                }
            }
        }*/

        return dto;
    }

    public DTODetallesVerMovimientosSeleccionado getDetalle(Long nroTransferencia) throws Exception {
        String tipoT;
        String estadoT;
        Long nroCBTO = null;
        String titularCBTO = "";
        Long nroCBTD = null;
        String titularCBTD = "";

        Optional<CuentaBancaria> cuentaOrigen = repositorioCuentaBancaria.getCuentaVigentePorNumeroCuenta(nroCBCuentaO);

        if (cuentaOrigen.isEmpty()) {
            throw new Exception("Error, no se ha encontrado la cuenta bancaria");
        }

        Transferencia transferencia = getTransferencia(nroTransferencia);

        CuentaBancaria cbOrigen = transferencia.getOrigen();
        CuentaBancaria cbDestino = transferencia.getDestino();

        if (cuentaOrigen.get().equals(cbOrigen)) { //Envio
            tipoT = "Envio";
            nroCBTO = cbOrigen.getId();
            titularCBTO = cbOrigen.getTitular().getNombreUsuario();
            if(cbDestino==null){
                nroCBTD = null;
                titularCBTD = "Banco";
            }else{
                nroCBTD = transferencia.getDestino().getId();
                titularCBTD = transferencia.getDestino().getTitular().getNombreUsuario();
            }
        } else {
            if (cuentaOrigen.get().equals(cbDestino)) { //Recepcion
                tipoT = "Recepcion";
                nroCBTD = cbDestino.getId();
                titularCBTD = cbDestino.getTitular().getNombreUsuario();
                if(cbOrigen==null){
                    nroCBTO = null;
                    titularCBTO = "Banco";
                }else{
                    nroCBTO = transferencia.getOrigen().getId();
                    titularCBTO = transferencia.getOrigen().getTitular().getNombreUsuario();
                }
            } else {
                throw new Exception("No hay transferencias");
            }
        }

        if (transferencia.isAnulada()) {
            estadoT = "Anulada";
        } else {
            estadoT = "Vigente";
        }

        DTODetallesVerMovimientosSeleccionado dto = DTODetallesVerMovimientosSeleccionado.builder()
                .nroTransferencia(transferencia.getId())
                .tipoTransferencia(tipoT)
                .fechaTransferencia(transferencia.getFhTransferencia())
                .nroCBOrigen(nroCBTO)
                .titularCBOrigen(titularCBTO)
                .nroCBDestino(nroCBTD)
                .titularCBDestino(titularCBTD)
                .montoTransferencia(transferencia.getMontoTransferencia())
                .estadoTransferencia(estadoT)
                .motivoTransferencia(transferencia.getMotivo())
                .build();

        return dto;

    }
    private Transferencia getTransferencia(Long nroTransferencia) throws Exception {
        Optional<Transferencia> optTrans = repositorioTransferencia.findById(nroTransferencia);

        if(optTrans.isEmpty()) {
            throw new Exception("No se encontró la transferencia");
        }

        return optTrans.get();
    }
}
