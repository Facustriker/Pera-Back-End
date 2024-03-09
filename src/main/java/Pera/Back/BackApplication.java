package Pera.Back;

import Pera.Back.Entities.*;
import Pera.Back.Repositories.AuthUsuarioRepository;
import Pera.Back.Repositories.ConfiguracionRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class BackApplication {

    @Autowired
    private ConfiguracionRolRepository configuracionRolRepository;

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
					.nombreRol("ADMIN")
					.build();

			ArrayList<Permiso> permisosAdmin = new ArrayList<>();

			permisosAdmin.add(Permiso.builder().nombrePermiso("ADMIN_PARAMETROS").build());
			permisosAdmin.add(Permiso.builder().nombrePermiso("ADMIN_USUARIOS").build());
			permisosAdmin.add(Permiso.builder().nombrePermiso("ADMIN_BANCOS").build());
			permisosAdmin.add(Permiso.builder().nombrePermiso("VER_REPORTES").build());

			ConfiguracionRol crAdmin = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(admin)
					.permisos(permisosAdmin)
					.build();

			configuracionRolRepository.save(crAdmin);


			Rol usuario = Rol.builder()
					.nombreRol("USUARIO")
					.build();

			ArrayList<Permiso> permisosUsuario = new ArrayList<>();

			permisosUsuario.add(Permiso.builder().nombrePermiso("ADMIN_DATOS_PROPIOS").build());
			permisosUsuario.add(Permiso.builder().nombrePermiso("ADMIN_BANCOS_PROPIOS").build());
			permisosUsuario.add(Permiso.builder().nombrePermiso("ADMIN_CUENTAS_BANCARIAS_PROPIAS").build());

			ConfiguracionRol crUsuario = ConfiguracionRol.builder()
					.fhaCR(fecha)
					.rol(usuario)
					.permisos(permisosUsuario)
					.build();

			configuracionRolRepository.save(crUsuario);
		};
	}
}

