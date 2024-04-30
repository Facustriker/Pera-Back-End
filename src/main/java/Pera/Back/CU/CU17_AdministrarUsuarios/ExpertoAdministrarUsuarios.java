package Pera.Back.CU.CU17_AdministrarUsuarios;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Repositories.RepositorioCuentaBancaria;
import Pera.Back.Repositories.RepositorioUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarUsuarios {

    @Autowired
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    public Collection<DTOAdministrarUsuarios> getUsuarios(){

        ArrayList<DTOAdministrarUsuarios> dto = new ArrayList<>();

        for(Usuario usuario: repositorioUsuario.findAll()){
            Date ahora = new Date();
            String estado = "Vigente";

            if(usuario.getFhbUsuario().before(ahora)){
                estado = "De baja";
            }

            DTOAdministrarUsuarios aux = DTOAdministrarUsuarios.builder()
                    .idUsuario(usuario.getId())
                    .nombre(usuario.getNombreUsuario())
                    .correo(usuario.getMail())
                    .rol(usuario.getRolActual().getNombreRol())
                    .estado(estado)
                    .build();

            dto.add(aux);
        }

        return dto;
    }

    public DTOAdministrarUsuariosSeleccionado getUsuarioSeleccionado(Long idUsuario, String filtro) throws Exception{

        Optional<Usuario> usuario = repositorioUsuario.getUsuarioVigentePorNroUsuario(idUsuario);

        if(usuario.isEmpty()){
            throw new Exception("El usuario no se ha encontrado");
        };

        DTOAdministrarUsuariosSeleccionado dto = DTOAdministrarUsuariosSeleccionado.builder()
                .nombreUsuario(usuario.get().getNombreUsuario())
                .rolUsuario(usuario.get().getRolActual().getNombreRol())
                .build();

        for(CuentaBancaria cuenta: repositorioCuentaBancaria.buscarCuentasVigentesPorUsuario(usuario.get(), filtro)){
            String habilitada = "Habilitada";

            if(!(cuenta.isHabilitada())){
                habilitada = "Deshabilitada";
            }

            DTODetallesUsuarioSeleccionado aux = DTODetallesUsuarioSeleccionado.builder()
                    .nroCB(cuenta.getId())
                    .nombreBanco(cuenta.getBanco().getNombreBanco())
                    .monto(cuenta.getMontoDinero())
                    .cuentaHabilitada(habilitada)
                    .build();

            dto.getDetallesUsuario().add(aux);
        }

        return dto;
    }

    public void darBajaCuentaSeleccionada(Long nroCB) throws Exception{
        Optional<CuentaBancaria> cuenta = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(nroCB);

        if(cuenta.isEmpty()){
            throw new Exception("La cuenta no se ha encontrado");
        }

        cuenta.get().setHabilitada(false);

        repositorioCuentaBancaria.save(cuenta.get());
    }

}
