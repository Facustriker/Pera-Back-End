package Pera.Back.CU.CU24_TransferirDinero;

import Pera.Back.Entities.CuentaBancaria;
import Pera.Back.Entities.Transferencia;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@Data
@NoArgsConstructor
@SessionScope
public class MemoriaTransferirDinero implements Serializable {

    private Transferencia transferencia;
}
