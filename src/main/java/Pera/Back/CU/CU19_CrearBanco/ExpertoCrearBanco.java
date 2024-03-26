package Pera.Back.CU.CU19_CrearBanco;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.CantMaxBancosNoPremium;
import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Usuario;
import Pera.Back.Functionalities.ObtenerUsuarioActual.SingletonObtenerUsuarioActual;
import Pera.Back.Repositories.BancoRepository;
import Pera.Back.Repositories.CantMaxBancosNoPremiumRepository;
import Pera.Back.Repositories.CuentaBancariaRepository;
import Pera.Back.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpertoCrearBanco {

    private final BancoRepository bancoRepository;

    private final UsuarioRepository usuarioRepository;

    private final CuentaBancariaRepository cuentaBancariaRepository;

    private final CantMaxBancosNoPremiumRepository cantMaxBancosNoPremiumRepository;

    public String crear(DTOCrearBanco request) throws Exception{
        String password = "";
        boolean habilitado = false;

        Optional<Banco> prev = bancoRepository.findBynombreBanco(request.getNombre());
        if (prev.isPresent()){
            throw new Exception("Error, este banco ya esta registrado");
        }

        if (request.isUsarPassword()){
            password = request.getPassword();
        }

        if (request.isHabilitacionAutomatica()){
            habilitado = true;
        }

        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        dueno = usuarioRepository.findById(dueno.getId()).get();

        if(verificarRolPremium()){
            Banco banco = Banco.builder()
                    .nombreBanco(validarNombre(request))
                    .simboloMoneda(validarSimboloMoneda(request))
                    .habilitacionAutomatica(request.isHabilitacionAutomatica())
                    .habilitado(habilitado)
                    .password(password)
                    .dueno(dueno)
                    .build();

            CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                    .alias(generarAlias(request.getNombre()))
                    .esBanquero(true)
                    .fhaCB(new Date())
                    .habilitada(true)
                    .montoDinero(0)
                    .build();

            cuentaBancaria.setTitular(dueno);
            cuentaBancaria.setBanco(banco);

            cuentaBancariaRepository.save(cuentaBancaria);
            return "";
        }else{
            CantMaxBancosNoPremium cant = new CantMaxBancosNoPremium();
            int maximo = cant.getCantidad();

            if(cantBancosActualesCreados()>=2){
                throw new Exception("Error, ya no puede crear mas bancos. Maximo alcanzado.");
            }else{
                Banco banco = Banco.builder()
                        .nombreBanco(validarNombre(request))
                        .simboloMoneda(validarSimboloMoneda(request))
                        .habilitacionAutomatica(request.isHabilitacionAutomatica())
                        .habilitado(habilitado)
                        .password(password)
                        .dueno(dueno)
                        .build();

                CuentaBancaria cuentaBancaria = CuentaBancaria.builder()
                        .alias(generarAlias(request.getNombre()))
                        .esBanquero(true)
                        .fhaCB(new Date())
                        .habilitada(true)
                        .montoDinero(0)
                        .build();

                cuentaBancaria.setTitular(dueno);
                cuentaBancaria.setBanco(banco);

                cuentaBancariaRepository.save(cuentaBancaria);
                return "";
            }

        }




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

    private boolean verificarRolPremium(){
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();

        dueno = usuarioRepository.findById(dueno.getId()).get();

        if(dueno.getRolActual().getNombreRol().equals("Premium")){
            return true;
        }else{
            return false;
        }
    }

    private int cantBancosActualesCreados(){
        SingletonObtenerUsuarioActual singletonObtenerUsuarioActual = SingletonObtenerUsuarioActual.getInstancia();
        Usuario dueno = singletonObtenerUsuarioActual.obtenerUsuarioActual();
        dueno = usuarioRepository.findById(dueno.getId()).get();

        return bancoRepository.cantidadBancosPorIdUsuario(dueno.getId());

    }
}
