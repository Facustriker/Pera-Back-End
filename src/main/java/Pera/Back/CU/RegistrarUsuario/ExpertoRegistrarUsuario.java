package Pera.Back.CU.RegistrarUsuario;

public interface ExpertoRegistrarUsuario {

    public String register(DTORegisterRequest request) throws Exception;
    public DTOAuthResponse ingresarCodigo(int codigo) throws Exception;
}
