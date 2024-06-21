package Pera.Back.CU.CU11_AdministrarBancoPropio;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarBancoPropio {

    private final RepositorioBanco repositorioBanco;

    public DTODatosBanco obtenerDatos(Long idBanco) throws Exception {
        Optional<Banco> banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco.get())) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        DTODatosBanco dto = DTODatosBanco.builder()
                .id(banco.get().getId())
                .nombre(banco.get().getNombreBanco())
                .simboloMoneda(banco.get().getSimboloMoneda())
                .habilitacionAutomatica(banco.get().getHabilitacionAutomatica())
                .dueno(banco.get().getDueno().getNombreUsuario())
                .contrasena("")
                .cambiarContrasena(!banco.get().getPassword().isEmpty())
                .habilitado(banco.get().getHabilitado())
                .build();

        return dto;
    }

    public void modificar(DTODatosBanco dto) throws Exception {
        Optional<Banco> banco = repositorioBanco.getBancoPorNumeroBanco(dto.getId());

        if (!esTitular(banco.get())) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        if(dto.getNombre().trim().isEmpty())
            throw new Exception("El nombre no puede quedar en blanco");

        if(dto.getSimboloMoneda().trim().isEmpty())
            throw new Exception("El símbolo de la moneda no puede quedar en blanco");

        banco.get().setNombreBanco(dto.getNombre());
        banco.get().setSimboloMoneda(dto.getSimboloMoneda());
        banco.get().setHabilitacionAutomatica(dto.isHabilitacionAutomatica());

        if(dto.isCambiarContrasena()) {
            if (dto.getContrasena().isEmpty()) {
                throw new Exception("Debe ingresar la contraseña");
            }
            banco.get().setPassword(dto.getContrasena());
        } else {
            banco.get().setPassword("");
        }

        repositorioBanco.save(banco.get());
    }

    public void cambiarHabilitacion(Long idBanco) throws Exception {
        Optional<Banco> banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco.get())) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        banco.get().setHabilitado(!banco.get().getHabilitado());
        repositorioBanco.save(banco.get());
    }

    public void baja(Long idBanco) throws Exception {
        Optional<Banco> banco = repositorioBanco.getBancoPorNumeroBanco(idBanco);

        if (!esTitular(banco.get())) {
            throw new Exception("Solo el dueño del banco puede modificar esta información");
        }

        banco.get().setFhbBanco(new Date());

        repositorioBanco.save(banco.get());
    }




    private boolean esTitular(Banco banco) {
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        return banco.getDueno().getId().longValue() == usuario.getId().longValue();

    }
}
