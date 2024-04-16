package Pera.Back.CU.CU24_TransferirDinero;

import Pera.Back.Entities.CuentaBancaria;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
@NoArgsConstructor
public class MemoriaTransferirDinero implements Serializable {

    private String aliasCBDestino;
    private String aliasCBOrigen;
    private DTODetallesTransferencia dto;
    private CuentaBancaria cuentaDestino;
    private CuentaBancaria cuentaOrigen;
    private String moneda;
}
