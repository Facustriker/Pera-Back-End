package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpertoCrearBanco {

    private final BancoRepository bancoRepository;

    private final UsuarioRepository usuarioRepository;

    private final CuentaBancariaRepository cuentaBancariaRepository;

    private final ConfiguracionRolRepository configuracionRolRepository;

    private final CantMaxBancosNoPremiumRepository cantMaxBancosNoPremiumRepository;

    private final CantMaxCuentasOtrosBancosRepository cantMaxCuentasOtrosBancosRepository;

    private final ParametroSimboloMonedaRepository parametroSimboloMonedaRepository;

    public Long crear(DTOCrearBanco request) throws Exception{
        String password = "";
        boolean habilitacionAutomatica = false;

        Optional<Banco> prev = bancoRepository.findBynombreBanco(request.getNombre());
        if (prev.isPresent()){
            throw new Exception("Error, este banco ya esta registrado");
        }

        if (request.isUsarPassword()){
            password = request.getPassword();
        }

        if (request.isHabilitacionAutomatica()){
            habilitacionAutomatica = true;
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        dueno = usuarioRepository.findById(dueno.getId()).get();

        Rol rolDueno = dueno.getRolActual();
        ArrayList<String> permisos = new ArrayList<>();
        for ( Permiso permiso : configuracionRolRepository.getPermisos(rolDueno) ) {
            permisos.add(permiso.getNombrePermiso());
        };

        if (!permisos.contains("CANTIDAD_BANCOS_DUENO_ILIMITADA")) {
            int cantidadBancosUsuario = bancoRepository.cantidadBancosPorUsuario(dueno);
            int cantMaxBancosNoPremium = cantMaxBancosNoPremiumRepository.obtenerCantidadVigente();
            if(cantidadBancosUsuario>=cantMaxBancosNoPremium){
                throw new Exception("Error, se ha alcanzado la cantidad maxima de bancos No Premium");
            }

        }

        if (!permisos.contains("CANTIDAD_CUENTAS_PROPIAS_ILIMITADA")) {
            int cantidadTotalCuentas = cuentaBancariaRepository.cantidadCuentasBancariasPorUsuario(dueno);
            int cantidadMaximaCuentasOtrosBancos = cantMaxCuentasOtrosBancosRepository.obtenerCantidadVigente();
            if(cantidadTotalCuentas>=cantidadMaximaCuentasOtrosBancos){
                throw new Exception("Error, se ha alcanzado la cantidad maxima de cuentas bancarias No Premium");
            }
        }

        String simbolo = "$";
        if (permisos.contains("ELEGIR_SIMBOLO_MONEDA")) {
            simbolo = validarSimboloMoneda(request);
        } else {
            simbolo= parametroSimboloMonedaRepository.obtenerSimboloVigente();
        }

        Banco banco = Banco.builder()
                .nombreBanco(validarNombre(request))
                .simboloMoneda(simbolo)
                .habilitado(true)
                .habilitacionAutomatica(habilitacionAutomatica)
                .password(password)
                .dueno(dueno)
                .build();

        CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                .alias(generarAlias(request.getNombre()))
                .esBanquero(true)
                .fhaCB(new Date())
                .habilitada(true)
                .montoDinero(0)
                .titular(dueno)
                .banco(banco)
                .build();


        cuentaBancariaRepository.save(cuentaBancaria);

        return banco.getId();

    }

    private String generarAlias(String patron){
        String alias = patron+"_"+UUID.randomUUID().toString().substring(0,8);
        return alias;
    }

    private String validarNombre(DTOCrearBanco request) throws Exception{
        String nombrebanco = "";
        if (Objects.equals(request.getNombre(), "")){
            throw new Exception("Error, debe ingresar un nombre");
        }else{
            nombrebanco = request.getNombre();
            return  nombrebanco;
        }
    }

    private String validarSimboloMoneda(DTOCrearBanco request) throws Exception{
        String simboloMoneda = "";
        if (Objects.equals(request.getSimboloMoneda(), "")){
            throw new Exception("Error, debe ingresar un simbolo de moneda");
        }else{
            simboloMoneda = request.getSimboloMoneda();
            return  simboloMoneda;
        }
    }

}
