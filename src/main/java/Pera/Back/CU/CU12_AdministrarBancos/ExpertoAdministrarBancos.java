package Pera.Back.CU.CU12_AdministrarBancos;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarBancos {

    @Autowired
    private final RepositorioBanco repositorioBanco;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;


    public Collection<DTOAdministrarBancos> getBancos(DTOFiltrosBancos filtros){
        ArrayList<DTOAdministrarBancos> ret = new ArrayList<>();

        for (Banco banco : repositorioBanco.filtrarBancos(filtros.getNombre(), filtros.deshabilitados, filtros.bajas)) {
            DTOAdministrarBancos dto = DTOAdministrarBancos.builder()
                    .nroBanco(banco.getId())
                    .nombre(banco.getNombreBanco())
                    .habilitado(banco.getHabilitado())
                    .baja(banco.getFhbBanco())
                    .build();
            ret.add(dto);
        }

        return ret;
    }

    public DTODetalleBanco getBanco(Long id) throws Exception{
        Optional<Banco> optBanco = repositorioBanco.findById(id);

        if (optBanco.isEmpty()) throw new Exception("Banco no encontrado");

        Banco banco = optBanco.get();

        DTODetalleBanco ret = DTODetalleBanco.builder()
                .id(banco.getId())
                .nombre(banco.getNombreBanco())
                .nombreDueno(banco.getDueno().getNombreUsuario())
                .mailDueno(banco.getDueno().getMail())
                .habilitado(banco.getHabilitado())
                .alta(banco.getFhaBanco())
                .baja(banco.getFhbBanco())
                .build();

        return ret;
    }

    public void modificarBanco(DTODetalleBanco dto) throws Exception{
        Optional<Banco> optBanco = repositorioBanco.findById(dto.getId());

        if (optBanco.isEmpty()) throw new Exception("Banco no encontrado");

        Banco banco = optBanco.get();

        banco.setHabilitado(dto.isHabilitado());
        banco.setFhbBanco(dto.getBaja());

        repositorioBanco.save(banco);
    }

    public DTOAdministrarBancosCuentas getCuentas(Long nroCB, Long idBanco) throws Exception{
        Optional<Banco> optBanco = repositorioBanco.findById(idBanco);

        if (optBanco.isEmpty()) throw new Exception("Banco no encontrado");

        Banco banco = optBanco.get();

        DTOAdministrarBancosCuentas ret = DTOAdministrarBancosCuentas.builder()
                .nombreBanco(banco.getNombreBanco())
                .build();

        for (CuentaBancaria cuentaBancaria : repositorioCuentaBancaria.getCuentasPorBancoYNroCB(banco, nroCB)) {

            boolean vigente = cuentaBancaria.getFhaCB().before(new Date()) && (cuentaBancaria.getFhbCB() == null || cuentaBancaria.getFhbCB().after(new Date()));

            DTOAdministrarBancosCuentasDetalle dto = DTOAdministrarBancosCuentasDetalle.builder()
                    .nroCB(cuentaBancaria.getId())
                    .nombreTitular(cuentaBancaria.getTitular().getNombreUsuario())
                    .monto(cuentaBancaria.getMontoDinero())
                    .vigente(vigente)
                    .baja(cuentaBancaria.getFhbCB())
                    .build();
            ret.detalles.add(dto);
        }

        return ret;
    }

    public DTODetalleCuenta getCuenta(Long nroCB) throws Exception{
        Optional<CuentaBancaria> optionalCuentaBancaria = repositorioCuentaBancaria.findById(nroCB);

        if(optionalCuentaBancaria.isEmpty()) throw new Exception("Cuenta bancaria no encontrada");

        CuentaBancaria cuentaBancaria = optionalCuentaBancaria.get();

        DTODetalleCuenta ret = DTODetalleCuenta.builder()
                .nombreBanco(cuentaBancaria.getBanco().getNombreBanco())
                .nroCB(cuentaBancaria.getId())
                .alta(cuentaBancaria.getFhaCB())
                .baja(cuentaBancaria.getFhbCB())
                .habilitada(cuentaBancaria.isHabilitada())
                .nombreTitular(cuentaBancaria.getTitular().getNombreUsuario())
                .build();
        return ret;
    }



}
