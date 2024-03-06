package Pera.Back.CU.LoguearUsuario;

import Pera.Back.CU.RegistrarUsuario.DTOAuthResponse;

public interface ExpertoLoguearUsuario {

    public DTOAuthResponse login(DTOLoginRequest request);
}
