package Pera.Back.CU.CU11_AdministrarBancoPropio;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Rol;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ActualizarRol.SingletonActualizarRol;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarBancoPropio {

    private final RepositorioBanco repositorioBanco;

    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    private final RepositorioUsuarioRol repositorioUsuarioRol;

    private final RepositorioUsuario repositorioUsuario;

    public DTODatosBanco obtenerDatos(Long idBanco) throws Exception {
        Banco banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco)) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        DTODatosBanco dto = DTODatosBanco.builder()
                .id(banco.getId())
                .nombre(banco.getNombreBanco())
                .simboloMoneda(banco.getSimboloMoneda())
                .habilitacionAutomatica(banco.getHabilitacionAutomatica())
                .dueno(banco.getDueno().getNombreUsuario())
                .contrasena("")
                .cambiarContrasena(!banco.getPassword().isEmpty())
                .habilitado(banco.getHabilitado())
                .build();

        return dto;
    }

    public void modificar(DTODatosBanco dto) throws Exception {
        Banco banco = repositorioBanco.getBancoPorNumeroBanco(dto.getId());

        if (!esTitular(banco)) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        banco.setNombreBanco(dto.getNombre());
        banco.setSimboloMoneda(dto.getSimboloMoneda());
        banco.setHabilitacionAutomatica(dto.isHabilitacionAutomatica());

        if(dto.isCambiarContrasena()) {
            if (dto.getContrasena().isEmpty()) {
                throw new Exception("Debe ingresar la contraseña");
            }
            banco.setPassword(dto.getContrasena());
        } else {
            banco.setPassword("");
        }

        repositorioBanco.save(banco);
    }

    public void cambiarHabilitacion(Long idBanco) throws Exception {
        Banco banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco)) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        banco.setHabilitado(!banco.getHabilitado());
        repositorioBanco.save(banco);
    }

    public void baja(Long idBanco) throws Exception {
        Banco banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco)) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        banco.setFhbBanco(new Date());

        repositorioBanco.save(banco);
    }




    private boolean esTitular(Banco banco) {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        return banco.getDueno().getId().longValue() == usuario.getId().longValue();

    }
}
