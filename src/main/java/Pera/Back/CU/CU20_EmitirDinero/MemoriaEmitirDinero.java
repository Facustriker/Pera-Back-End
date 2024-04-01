package Pera.Back.CU.CU20_EmitirDinero;

import Pera.Back.Entities.CuentaBancaria;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

@Component
@SessionScope
@Data
@NoArgsConstructor
public class MemoriaEmitirDinero implements Serializable {
    private DTOIDEMListaCB dto;

    private CuentaBancaria cuentaBanquero;

    private boolean ok = false;

    private String errorMessage = "";
}
