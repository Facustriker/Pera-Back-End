package Pera.Back;

import Pera.Back.CU.Entities.AuthUsuario;
import Pera.Back.CU.Entities.Rol;
import Pera.Back.CU.Entities.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
        System.out.println("La aplicación se ha iniciado correctamente");
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


            Rol rol = Rol.builder()
                    .nombreRol("ADMIN")
                    .build();

            Usuario usuario = Usuario.builder()
                    .fhaUsuario(fecha)
                    .mail("pera@gmail.com")
                    .nombreUsuario("Facustriker")
                    .build();

            usuario.AgregarRol(rol);

            AuthUsuario authUsuario = AuthUsuario.builder()
                    .password("123")
                    .username("Facustriker")
                    .usuario(usuario)
                    .build();


		};
	}
}

