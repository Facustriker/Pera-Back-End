package Pera.Back.CU.CU10_AbrirCuentaBancaria;

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
public class MemoriaAbrirCuentaBancaria implements Serializable {
    private CuentaBancaria cuenta;
}
