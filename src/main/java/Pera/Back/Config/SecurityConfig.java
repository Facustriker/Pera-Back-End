package Pera.Back.Config;

import Pera.Back.JWT.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/RegistrarUsuario/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/LoguearUsuario/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/CrearBanco/**")).hasAnyAuthority("ADMIN_BANCOS_PROPIOS")
                        .requestMatchers(new AntPathRequestMatcher("/MisBancos/**")).hasAnyAuthority("ADMIN_BANCOS_PROPIOS")
                        .requestMatchers(new AntPathRequestMatcher("/SuscribirseAPremium/**")).hasAnyAuthority("ADMIN_DATOS_PROPIOS")
                        .requestMatchers(new AntPathRequestMatcher("/Usuario/**")).hasAnyAuthority("ADMIN_DATOS_PROPIOS")
                        .requestMatchers(new AntPathRequestMatcher("/AbrirCuentaBancaria/**")).hasAnyAuthority("ADMIN_CUENTAS_BANCARIAS_PROPIAS")
                        .requestMatchers(new AntPathRequestMatcher("/MisCuentasBancarias/**")).hasAnyAuthority("ADMIN_CUENTAS_BANCARIAS_PROPIAS")
                        .requestMatchers(new AntPathRequestMatcher("/EmitirDinero/**")).hasAnyAuthority("ADMIN_BANCOS_PROPIOS")
                        .requestMatchers(new AntPathRequestMatcher("/AdministrarDatosDelUsuario/**")).hasAnyAuthority("ADMIN_DATOS_PROPIOS")
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                )
                .cors(withDefaults())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
