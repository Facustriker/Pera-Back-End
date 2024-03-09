package Pera.Back;

import Pera.Back.Entities.*;
import Pera.Back.Repositories.AuthUsuarioRepository;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import Pera.Back.Repositories.PermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class BackApplication {

    @Autowired
    private ConfiguracionRolRepository configuracionRolRepository;

	@Autowired
	private PermisoRepository permisoRepository;

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

			configuracionRolRepository.save(crAdmin);




			Rol usuario = Rol.builder()
					.nombreRol("No Premium")
					.build();


			ConfiguracionRol crUsuario = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(usuario)
					.build();

			crUsuario.addPermiso(Permiso.builder().nombrePermiso("ADMIN_DATOS_PROPIOS").build());
			crUsuario.addPermiso(Permiso.builder().nombrePermiso("ADMIN_BANCOS_PROPIOS").build());
			crUsuario.addPermiso(Permiso.builder().nombrePermiso("ADMIN_CUENTAS_BANCARIAS_PROPIAS").build());

			crUsuario = configuracionRolRepository.save(crUsuario);




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


			crPremium = configuracionRolRepository.save(crPremium);

			for (Permiso permiso : crUsuario.getPermisos()) {
				crPremium.addPermiso(permiso);
			}

			configuracionRolRepository.save(crPremium);

		};
	}
}

