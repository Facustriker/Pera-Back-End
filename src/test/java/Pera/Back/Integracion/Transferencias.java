package Pera.Back.Integracion;

import Pera.Back.CU.CU24_TransferirDinero.MemoriaTransferirDinero;
import Pera.Back.Entities.*;
import Pera.Back.Repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Pera.Back.BackApplication.class)
@AutoConfigureMockMvc(addFilters = false)
public class Transferencias {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepositorioConfiguracionRol repositorioConfiguracionRol;

    @Autowired
    private RepositorioAuthUsuario repositorioAuthUsuario;

    @Autowired
    private RepositorioUsuarioRol repositorioUsuarioRol;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioBanco repositorioBanco;

    @Autowired
    private RepositorioCuentaBancaria repositorioCuentaBancaria;

    @MockBean
    private MemoriaTransferirDinero memoriaTransferirDinero;

    @Autowired
    private RepositorioTransferencia repositorioTransferencia;

    @Test
    void generarTransferencia() throws Exception {

        Rol rol = Rol.builder()
                .nombreRol("No Premium")
                .build();

        ConfiguracionRol cr = ConfiguracionRol.builder()
                .fhaCR(new Date())
                .rol(rol)
                .build();

        cr.addPermiso(Permiso.builder().nombrePermiso("ADMIN_BANCOS_PROPIOS").build());
        cr.addPermiso(Permiso.builder().nombrePermiso("ADMIN_CUENTAS_BANCARIAS_PROPIAS").build());
        cr.addPermiso(Permiso.builder().nombrePermiso("SUSCRIPCION_PREMIUM").build());

        cr = repositorioConfiguracionRol.save(cr);
        rol = cr.getRol();

        String username = "Test";

        Usuario usuario = Usuario.builder()
                .fhaUsuario(new Date())
                .mail("test@mail.com")
                .nombreUsuario(username)
                .rolActual(rol)
                .build();

        usuario = repositorioUsuario.save(usuario);

        AuthUsuario authUsuario = AuthUsuario.builder()
                .enabled(true)
                .username(username)
                .password("1234")
                .build();

        authUsuario = repositorioAuthUsuario.save(authUsuario);
        authUsuario.setUsuario(usuario);
        repositorioAuthUsuario.save(authUsuario);

        UsuarioRol usuarioRol = UsuarioRol.builder()
                .usuario(usuario)
                .rol(rol)
                .fhaUsuarioRol(new Date())
                .build();

        repositorioUsuarioRol.save(usuarioRol);

        Banco banco = Banco.builder()
                .fhaBanco(new Date())
                .habilitacionAutomatica(true)
                .habilitado(true)
                .nombreBanco("Banco")
                .simboloMoneda("$")
                .build();

        CuentaBancaria cb1 = CuentaBancaria.builder()
                .banco(banco)
                .alias(username)
                .esBanquero(true)
                .fhaCB(new Date())
                .habilitada(true)
                .montoDinero(1000)
                .build();

        cb1 = repositorioCuentaBancaria.save(cb1);
        cb1.setTitular(usuario);
        cb1.getBanco().setDueno(usuario);
        cb1 = repositorioCuentaBancaria.save(cb1);


        CuentaBancaria cb2 = CuentaBancaria.builder()
                .alias(username)
                .esBanquero(true)
                .fhaCB(new Date())
                .habilitada(true)
                .montoDinero(0)
                .build();

        cb2 = repositorioCuentaBancaria.save(cb2);
        cb2.setTitular(usuario);
        cb2.setBanco(banco);
        cb2 = repositorioCuentaBancaria.save(cb2);

        Transferencia transferencia = Transferencia.builder()
                .origen(cb1)
                .destino(cb2)
                .montoTransferencia(200.0)
                .motivo("Transferencia Test")
                .build();

        when(memoriaTransferirDinero.getTransferencia()).thenReturn(transferencia);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/TransferirDinero/confirmar")
                        .with(user(username))
                        .param("confirmacion", "true"))
                .andExpect(status().isOk())
                .andReturn();

        Long transferenciaId = Long.parseLong(result.getResponse().getContentAsString());

        transferencia = repositorioTransferencia.findById(transferenciaId).get();

        assertEquals(transferencia.getOrigen().getMontoDinero(), 800.0);
        assertEquals(transferencia.getDestino().getMontoDinero(), 200.0);

    }
}
