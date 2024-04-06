package Pera.Back.CU.CU25_TransferirDominioDeBanco;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ActualizarRol.SingletonActualizarRol;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.RepositorioBanco;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioUsuario;
import Pera.Back.Repositories.RepositorioUsuarioRol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoTransferirDominioDeBanco {

    private final MemoriaTransferirDominioDeBanco memoria;

    private final RepositorioBanco repositorioBanco;

    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    private final RepositorioUsuarioRol repositorioUsuarioRol;

    private final RepositorioUsuario repositorioUsuario;

    public DTOBancoTransferirDominio obtenerPosiblesDuenos(Long idBanco) throws Exception {
        memoria.setUsuario(null);
        memoria.setBanco(null);

        Banco banco = getBanco(idBanco);

        if (!isDueno(banco)) {
            throw new Exception("Solo el dueño de un banco puede transferir el dominio del mismo");
        }

        DTOBancoTransferirDominio dto = DTOBancoTransferirDominio.builder()
                .nombre(banco.getNombreBanco())
                .build();

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario actual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        SingletonActualizarRol singletonActualizarRol = SingletonActualizarRol.getInstancia();

        for (CuentaBancaria cuentaBancaria : repositorioCuentaBancaria.getCuentasBanqueroVigentesPorBanco(banco)) {
            if (cuentaBancaria.getTitular().getId().longValue() != actual.getId().longValue()) {
                DTOPosiblesDuenos dtoPosiblesDuenos = DTOPosiblesDuenos.builder()
                        .id(cuentaBancaria.getTitular().getId())
                        .nroCB(cuentaBancaria.getId())
                        .nombre(cuentaBancaria.getTitular().getNombreUsuario())
                        .rol(singletonActualizarRol.actualizarRol(repositorioUsuarioRol, cuentaBancaria.getTitular()).getNombreRol())
                        .build();
                dto.getPosiblesDuenos().add(dtoPosiblesDuenos);
            }
        }

        memoria.setBanco(banco);

        return dto;

    }

    public DTOUsuarioPosibleDueno obtenerUsuario(Long idUsuario) throws Exception {
        Banco banco = memoria.getBanco();

        if(banco == null) {
            throw new Exception("Debe seleccionar el banco antes de seleccionar el usuario");
        }

        if (!isDueno(banco)) {
            throw new Exception("Solo el dueño de un banco puede transferir el dominio del mismo");
        }

        Usuario usuario = getUsuario(idUsuario);

        if (!isBanquero(usuario, banco)) {
            throw new Exception("El usuario seleccionado debe ser un banquero");
        }

        DTOUsuarioPosibleDueno dto = DTOUsuarioPosibleDueno.builder()
                .nombre(usuario.getNombreUsuario())
                .mail(usuario.getMail())
                .nombreBanco(banco.getNombreBanco())
                .build();

        memoria.setUsuario(usuario);

        return dto;

    }

    public void confirmar(boolean confirmacion) throws Exception {
        if (!confirmacion) {
            memoria.setBanco(null);
            memoria.setUsuario(null);
            return;
        }

        Banco banco = memoria.getBanco();
        Usuario usuario = memoria.getUsuario();

        if (banco == null || usuario == null) {
            throw new Exception("Debe seleccionar el banco y el usuario");
        }

        if (!isDueno(banco)) {
            throw new Exception("Solo el dueño de un banco puede transferir el dominio del mismo");
        }

        banco = getBanco(banco.getId());

        usuario = getUsuario(usuario.getId());

        if (!isBanquero(usuario, banco)) {
            throw new Exception("El usuario seleccionado debe ser un banquero");
        }

        banco.setDueno(usuario);

        repositorioBanco.save(banco);

        memoria.setBanco(null);
        memoria.setUsuario(null);
    }

    private boolean isDueno(Banco banco) {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario actual = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        return banco.getDueno().getId().longValue() == actual.getId();
    }

    private Banco getBanco(Long id) throws Exception {
        Optional<Banco> optB = repositorioBanco.findById(id);
        if (optB.isEmpty()) {
            throw new Exception("No se encontró el banco");
        }
        return optB.get();
    }

    private Usuario getUsuario(Long id) throws Exception {
        Optional<Usuario> optU = repositorioUsuario.findById(id);
        if(optU.isEmpty()) {
            throw new Exception("No se encontró el usuario");
        }
        return optU.get();
    }

    private boolean isBanquero(Usuario usuario, Banco banco) {
        boolean cbBanqueroEncontrada = false;
        for (CuentaBancaria cuentaBancaria : repositorioCuentaBancaria.obtenerCuentasBancariasVigentesPorUsuarioYBanco(usuario, banco)) {
            if (cuentaBancaria.isEsBanquero()) {
                cbBanqueroEncontrada = true;
                break;
            }
        }
        return cbBanqueroEncontrada;
    }
}
