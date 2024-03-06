package Pera.Back.CU.RegistrarUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTORegisterRequest {

    String nombre;
    String email;
    String password;

}

