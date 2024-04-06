package Pera.Back.CU.CU25_TransferirDominioDeBanco;

import Pera.Back.Entities.Banco;
import Pera.Back.Entities.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
@Data
@NoArgsConstructor
public class MemoriaTransferirDominioDeBanco implements Serializable {
    private Banco banco;
    private Usuario usuario;
}
