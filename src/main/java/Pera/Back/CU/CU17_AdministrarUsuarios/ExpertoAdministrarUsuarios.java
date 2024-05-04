package Pera.Back.CU.CU17_AdministrarUsuarios;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpertoAdministrarUsuarios {

    @Autowired
    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    @Autowired
    private final RepositorioRol repositorioRol;

    @Autowired
    private RepositorioUsuarioRol repositorioUsuarioRol;

    @Autowired
    private RepositorioConfiguracionRol repositorioConfiguracionRol;

    public Collection<DTOAdministrarUsuarios> getUsuarios(){

        ArrayList<DTOAdministrarUsuarios> dto = new ArrayList<>();

        for(Usuario usuario: repositorioUsuario.findAll()){
            String estado;

            if(usuario.getFhbUsuario()==null){
                estado = "Vigente";
            }else{
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

    public DTOAdministrarUsuariosSeleccionado getUsuarioSeleccionado(Long idUsuario) throws Exception{

        Optional<Usuario> usuario = repositorioUsuario.getUsuarioVigentePorNroUsuario(idUsuario);

        if(usuario.isEmpty()){
            throw new Exception("El usuario no se ha encontrado");
        };

        DTOAdministrarUsuariosSeleccionado dto = DTOAdministrarUsuariosSeleccionado.builder()
                .nombreUsuario(usuario.get().getNombreUsuario())
                .rolUsuario(usuario.get().getRolActual().getNombreRol())
                .build();

        for(CuentaBancaria cuenta: repositorioCuentaBancaria.buscarCuentasVigentesPorUsuario(usuario.get())){

            DTODetallesUsuarioSeleccionado aux = DTODetallesUsuarioSeleccionado.builder()
                    .nroCB(cuenta.getId())
                    .nombreBanco(cuenta.getBanco().getNombreBanco())
                    .monto(cuenta.getMontoDinero())
                    .cuentaHabilitada(cuenta.isHabilitada())
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

    public DTODetallesUsuarioSeleccionado getCuentaFiltrada(String filtro) throws Exception{

        Optional<CuentaBancaria> cuenta = repositorioCuentaBancaria.obtenerCuentaVigentePorNumeroCuenta(Long.parseLong(filtro));

        if(cuenta.isEmpty()){
            throw new Exception("La cuenta ingresada no existe");
        }

        DTODetallesUsuarioSeleccionado dto = DTODetallesUsuarioSeleccionado.builder()
                .nroCB(cuenta.get().getId())
                .nombreBanco(cuenta.get().getBanco().getNombreBanco())
                .monto(cuenta.get().getMontoDinero())
                .cuentaHabilitada(cuenta.get().isHabilitada())
                .build();

        return dto;
    }

    public DTORolUsuarioAdministrarUsuarios getRolUsuario(Long idUsuario) throws Exception{

        Optional<Usuario> usuario = repositorioUsuario.getUsuarioVigentePorNroUsuario(idUsuario);

        if(usuario.isEmpty()){
            throw new Exception("El usuario no se ha encontrado");
        }

        DTORolUsuarioAdministrarUsuarios dto = DTORolUsuarioAdministrarUsuarios.builder()
                .nroUsuario(usuario.get().getId())
                .nombreUsuario(usuario.get().getNombreUsuario())
                .rolActual(usuario.get().getRolActual().getNombreRol())
                .build();

        for(Rol rol: repositorioRol.getRolesVigentes()){
            String rolAux = rol.getNombreRol();

            dto.getRolesDisponibles().add(rolAux);
        }

        return dto;
    }

    public Collection<String> confirmar(String nuevoRol, DTORolUsuarioAdministrarUsuarios dto) throws Exception{

        Optional<Usuario> usuario = repositorioUsuario.getUsuarioVigentePorNroUsuario(dto.getNroUsuario());

        if(usuario.isEmpty()){
            throw new Exception("El usuario no se ha encontrado");
        }

        Optional<Rol> rol = repositorioRol.obtenerRolPorNombre(nuevoRol);

        if(rol.isEmpty()){
            throw new Exception("El rol ingresado no existe");
        }

        if(rol.get().getNombreRol().equals("Premium")){
            long timeNow = System.currentTimeMillis();

            UsuarioRol usuarioRolAntiguo = repositorioUsuarioRol.getActualByUsuario(usuario.get());

            usuarioRolAntiguo.setFhbUsuarioRol(new Date(timeNow));

            Collection<String> permisos = new ArrayList<>();

            for (Permiso permiso : repositorioConfiguracionRol.getPermisos(rol.get())) {
                permisos.add(permiso.getNombrePermiso());
            }

            usuario.get().setRolActual(rol.get());

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .rol(rol.get())
                    .usuario(usuario.get())
                    .fhaUsuarioRol(new Date())
                    .build();

            repositorioUsuarioRol.save(usuarioRol);

            return permisos;

        }else{
            Collection<String> permisos = new ArrayList<>();

            for (Permiso permiso : repositorioConfiguracionRol.getPermisos(usuario.get().getRolActual())) {
                permisos.add(permiso.getNombrePermiso());
            }

            long timeNow = System.currentTimeMillis();

            UsuarioRol usuarioRolAntiguo = repositorioUsuarioRol.getActualByUsuario(usuario.get());

            usuarioRolAntiguo.setFhbUsuarioRol(new Date(timeNow));

            usuario.get().setRolActual(rol.get());

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .fhaUsuarioRol(new Date(timeNow))
                    .rol(rol.get())
                    .usuario(usuario.get())
                    .build();

            repositorioUsuarioRol.save(usuarioRol);

            return permisos;
        }

    }

}
