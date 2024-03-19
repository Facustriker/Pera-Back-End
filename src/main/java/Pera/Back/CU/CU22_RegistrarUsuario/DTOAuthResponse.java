package Pera.Back.CU.CU22_RegistrarUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOAuthResponse {

    String token;
    String error;

    Collection<String> permisos;
}
