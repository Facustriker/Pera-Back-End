package Pera.Back.CU.CU10_AbrirCuentaBancaria;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.ActualizarRol.SingletonActualizarRol;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExpertoAbrirCuentaBancaria {

    private final RepositorioBanco repositorioBanco;

    private final RepositorioUsuario repositorioUsuario;

    private final RepositorioConfiguracionRol repositorioConfiguracionRol;

    private final RepositorioUsuarioRol repositorioUsuarioRol;

    private final RepositorioCantMaxCuentasOtrosBancos repositorioCantMaxCuentasOtrosBancos;

    private final RepositorioCuentaBancaria repositorioCuentaBancaria;

    private final RepositorioCantMaxCuentasBancoPropio repositorioCantMaxCuentasBancoPropio;

    private final MemoriaAbrirCuentaBancaria memoria;

    public Collection<DTOBancoAbrirCuentaBancaria> getBancos(String nombreBanco) {
        Collection<Banco> bancos = repositorioBanco.buscarBancosVigentesYHabilitados(nombreBanco);

        ArrayList<DTOBancoAbrirCuentaBancaria> dtos = new ArrayList<>();

        for (Banco banco : bancos) {
            DTOBancoAbrirCuentaBancaria dto = DTOBancoAbrirCuentaBancaria.builder()
                    .nroBanco(banco.getId())
                    .nombreBanco(banco.getNombreBanco())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }

    public boolean seleccionarBanco(Long nroBanco) throws Exception {
        Banco banco = repositorioBanco.getBancoPorNumeroBanco(nroBanco);

        CuentaBancaria cuenta = CuentaBancaria.builder()
                .banco(banco)
                .esBanquero(false)
                .fhbCB(null)
                .montoDinero(0)
                .habilitada(banco.getHabilitacionAutomatica())
                .build();

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario usuario = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        usuario = repositorioUsuario.findById(usuario.getId()).get();

        cuenta.setTitular(usuario);

        Rol rolActualUsuario = usuario.getRolActual();

        ArrayList<String> permisos = new ArrayList<>();
        for ( Permiso permiso : repositorioConfiguracionRol.getPermisos(rolActualUsuario) ) {
            permisos.add(permiso.getNombrePermiso());
        };

        if (!permisos.contains("CANTIDAD_CUENTAS_PROPIAS_ILIMITADA")) {
            Collection<CantMaxCuentasOtrosBancos> cantMaxCuentasOtrosBancos = repositorioCantMaxCuentasOtrosBancos.getVigentes();

            int cantidad = cantMaxCuentasOtrosBancos.iterator().next().getCantidad();

            Collection<CuentaBancaria> cuentasBancarias = repositorioCuentaBancaria.getCuentasVigentesPorUsuario(usuario);

            if (cuentasBancarias.size() >= cantidad) {
                throw new Exception("Ha llegado al límite de cuentas bancarias que puede tener en bancos");
            }
        }

        SingletonActualizarRol singletonActualizarRol = SingletonActualizarRol.getInstancia();
        Rol rolActualDueno = singletonActualizarRol.actualizarRol(repositorioUsuarioRol, banco.getDueno());

        ArrayList<String> permisosDueno = new ArrayList<>();
        for ( Permiso permiso : repositorioConfiguracionRol.getPermisos(rolActualDueno) ) {
            permisosDueno.add(permiso.getNombrePermiso());
        };

        if (!permisos.contains("CANTIDAD_CUENTAS_BANCO_PROPIO_ILIMITADA")) {
            Collection<CantMaxCuentasBancoPropio> cantMaxCuentasBancoPropios = repositorioCantMaxCuentasBancoPropio.getVigentes();

            int cantidad = cantMaxCuentasBancoPropios.iterator().next().getCantidad();

            Collection<CuentaBancaria> cuentasBancarias = repositorioCuentaBancaria.getCuentasVigentesPorBanco(banco);

            if (cuentasBancarias.size() >= cantidad) {
                throw new Exception("Este banco ha llegado al límite de cuentas bancarias soportadas");
            }
        }

        memoria.setCuenta(cuenta);
        return true;
    }

    public boolean ingresarAlias(String alias) throws Exception {
        CuentaBancaria cuenta = memoria.getCuenta();

        if(cuenta == null) {
            throw new Exception("Debe seleccionar el banco antes de ingresar el alias");
        }

        if (alias == null || alias.isEmpty()) {
            throw new Exception("Ingrese el alias");
        }

        Banco banco = cuenta.getBanco();

        boolean aliasDisponible = repositorioCuentaBancaria.checkAliasDisponible(banco, alias);

        if (!aliasDisponible) {
            throw new Exception("El alias ya está en uso por otra cuenta bancaria de este banco");
        }

        cuenta.setAlias(alias);
        memoria.setCuenta(cuenta);

        return true;
    }

    public DTOConfirmacionAbrirCuenta getDatosConfirmacion() throws Exception {
        CuentaBancaria cuenta = memoria.getCuenta();

        if(cuenta == null) {
            throw new Exception("Debe seleccionar el banco antes de poder abrir su cuenta bancaria");
        }
        if(cuenta.getAlias() == null || cuenta.getAlias().isEmpty()) {
            throw new Exception("Debe ingresar el alias antes de poder abrir su cuenta bancaria");
        }

        Banco banco = cuenta.getBanco();

        DTOConfirmacionAbrirCuenta dto = DTOConfirmacionAbrirCuenta.builder()
                .alias(cuenta.getAlias())
                .cuentaHabilitada(cuenta.isHabilitada())
                .nombreBanco(banco.getNombreBanco())
                .nroBanco(banco.getId())
                .tieneContrasena(!banco.getPassword().isEmpty())
                .build();

        return dto;
    }

    public Long confirmar(Boolean confirmacion, String contrasena) throws Exception {
        CuentaBancaria cuenta = memoria.getCuenta();

        if(cuenta == null) {
            throw new Exception("Debe seleccionar el banco antes de poder abrir su cuenta bancaria");
        }
        if(cuenta.getAlias() == null || cuenta.getAlias().isEmpty()) {
            throw new Exception("Debe ingresar el alias antes de poder abrir su cuenta bancaria");
        }

        Banco banco = cuenta.getBanco();

        if (!contrasena.equals(banco.getPassword())) {
            throw new Exception("La contraseña no coincide");
        }

        boolean aliasDisponible = repositorioCuentaBancaria.checkAliasDisponible(banco, cuenta.getAlias());

        if (!aliasDisponible) {
            throw new Exception("El alias ya está en uso por otra cuenta bancaria de este banco");
        }

        cuenta.setFhaCB(new Date());

        cuenta.setBanco(repositorioBanco.getBancoPorNumeroBanco(cuenta.getBanco().getId()));
        cuenta.setTitular(repositorioUsuario.findById(cuenta.getTitular().getId()).get());

        cuenta = repositorioCuentaBancaria.save(cuenta);

        memoria.setCuenta(null);

        return cuenta.getId();

    }
}
