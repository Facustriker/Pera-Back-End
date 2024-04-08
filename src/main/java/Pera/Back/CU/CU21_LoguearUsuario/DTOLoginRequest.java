package Pera.Back.CU.CU21_LoguearUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DTOLoginRequest {

    String email;
    String password;
}
