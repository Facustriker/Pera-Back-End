package Pera.Back;

import Pera.Back.Entities.*;
import Pera.Back.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class BackApplication {

    @Autowired
    private RepositorioConfiguracionRol repositorioConfiguracionRol;

	@Autowired
	private RepositorioPermiso repositorioPermiso;

	@Autowired
	private RepositorioMedioDePago repositorioMedioDePago;

	@Autowired
	private RepositorioConfiguracionPrecioPremium repositorioConfiguracionPrecioPremium;

    @Autowired
    private RepositorioCantMaxBancosNoPremium repositorioCantMaxBancosNoPremium;

    @Autowired
    private RepositorioCantMaxCuentasBancoPropio repositorioCantMaxCuentasBancoPropio;

    @Autowired
    private RepositorioCantMaxCuentasOtrosBancos repositorioCantMaxCuentasOtrosBancos;

	@Autowired
	private RepositorioParametroSimboloMoneda repositorioParametroSimboloMoneda;

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
        System.out.println("La aplicacion se ha iniciado correctamente");
	}

	@Bean
	public CommandLineRunner init() {
		return args -> {

			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm:ss");
			String fechaString = "2023-03-03";
			String horaString = "21:03:05";

			Date fecha = formatoFecha.parse(fechaString); //2023-03-03
			Date hora = formatoHora.parse(horaString); //21:03:05

			Rol admin = Rol.builder()
					.nombreRol("Administrador del Sistema")
					.build();

			ConfiguracionRol crAdmin = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(admin)
					.build();

			crAdmin.addPermiso(Permiso.builder().nombrePermiso("ADMIN_PARAMETROS").build());
			crAdmin.addPermiso(Permiso.builder().nombrePermiso("ADMIN_USUARIOS").build());
			crAdmin.addPermiso(Permiso.builder().nombrePermiso("ADMIN_BANCOS").build());
			crAdmin.addPermiso(Permiso.builder().nombrePermiso("VER_REPORTES").build());
			crAdmin.addPermiso(Permiso.builder().nombrePermiso("ADMIN_DATOS_PROPIOS").build());

			crAdmin = repositorioConfiguracionRol.save(crAdmin);




			Rol usuario = Rol.builder()
					.nombreRol("No Premium")
					.build();


			ConfiguracionRol crUsuario = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(usuario)
					.build();

			crUsuario.addPermiso(Permiso.builder().nombrePermiso("ADMIN_BANCOS_PROPIOS").build());
			crUsuario.addPermiso(Permiso.builder().nombrePermiso("ADMIN_CUENTAS_BANCARIAS_PROPIAS").build());
			crUsuario.addPermiso(Permiso.builder().nombrePermiso("SUSCRIPCION_PREMIUM").build());

			crUsuario = repositorioConfiguracionRol.save(crUsuario);

			for (Permiso permiso : crAdmin.getPermisos()) {
				if(permiso.getNombrePermiso() == "ADMIN_DATOS_PROPIOS") {
					crUsuario.addPermiso(permiso);
				}
			}

			crUsuario = repositorioConfiguracionRol.save(crUsuario);


			Rol premium = Rol.builder()
					.nombreRol("Premium")
					.build();

			ConfiguracionRol crPremium = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(premium)
					.build();

			crPremium.addPermiso(Permiso.builder().nombrePermiso("CANTIDAD_BANCOS_DUENO_ILIMITADA").build());
			crPremium.addPermiso(Permiso.builder().nombrePermiso("CANTIDAD_CUENTAS_BANCO_PROPIO_ILIMITADA").build());
			crPremium.addPermiso(Permiso.builder().nombrePermiso("CANTIDAD_CUENTAS_PROPIAS_ILIMITADA").build());
			crPremium.addPermiso(Permiso.builder().nombrePermiso("ELEGIR_SIMBOLO_MONEDA").build());


			crPremium = repositorioConfiguracionRol.save(crPremium);

			for (Permiso permiso : crUsuario.getPermisos()) {
				if (permiso.getNombrePermiso() != "SUSCRIPCION_PREMIUM")
					crPremium.addPermiso(permiso);
			}

			repositorioConfiguracionRol.save(crPremium);



			MedioDePago mdpMercadoPago = MedioDePago.builder()
					.nombreMDP("Mercado Pago")
					.build();
			repositorioMedioDePago.save(mdpMercadoPago);

			PrecioPremium ppMensual = PrecioPremium.builder()
					.nombrePP("Mensual")
					.descripcion("Disfrute de las ventajas de Premium por 30 días")
					.diasDuracion(30)
					.precio(2999.99)
					.build();

			PrecioPremium ppTrimestral = PrecioPremium.builder()
					.nombrePP("Trimestral")
					.descripcion("Aproveche los beneficios por un precio inferior durante tres meses")
					.diasDuracion(90)
					.precio(7999.50)
					.build();

			PrecioPremium ppAnual = PrecioPremium.builder()
					.nombrePP("Anual")
					.descripcion("Olvídese de renovar la suscripción por un año")
					.diasDuracion(365)
					.precio(29999.00)
					.build();

			ConfiguracionPrecioPremium cpp = ConfiguracionPrecioPremium.builder()
					.fhaCPP(fecha)
					.build();

			cpp.addPrecio(ppMensual);
			cpp.addPrecio(ppTrimestral);
			cpp.addPrecio(ppAnual);

			repositorioConfiguracionPrecioPremium.save(cpp);

            CantMaxBancosNoPremium cantMaxBancosNoPremium = CantMaxBancosNoPremium.builder()
                    .cantidad(2)
                    .fhaCMBNP(new Date())
                    .build();

            CantMaxCuentasBancoPropio cantMaxCuentasBancoPropio = CantMaxCuentasBancoPropio.builder()
                    .cantidad(3)
                    .fhaCMCBP(new Date())
                    .build();

            CantMaxCuentasOtrosBancos cantMaxCuentasOtrosBancos = CantMaxCuentasOtrosBancos.builder()
                    .cantidad(3)
                    .fhaCMCOB(new Date())
                    .build();

            repositorioCantMaxBancosNoPremium.save(cantMaxBancosNoPremium);
            repositorioCantMaxCuentasBancoPropio.save(cantMaxCuentasBancoPropio);
            repositorioCantMaxCuentasOtrosBancos.save(cantMaxCuentasOtrosBancos);

			ParametroSimboloMoneda parametroSimboloMoneda = ParametroSimboloMoneda.builder()
					.fhaPSM(new Date())
					.simboloMonedaPorDefecto("$")
					.build();

			repositorioParametroSimboloMoneda.save(parametroSimboloMoneda);

		};
	}
}

