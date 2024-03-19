package Pera.Back.Functionalities.RealizarPagos;

import Pera.Back.Entities.MedioDePago;

public class FactoriaARP {
    private static FactoriaARP instancia;

    public static FactoriaARP getInstancia() {
        if (instancia == null) instancia = new FactoriaARP();
        return instancia;
    }

    public AdaptadorRealizarPago obtenerARP(MedioDePago mdp) throws Exception{
        switch (mdp.getNombreMDP()) {
            case "Mercado Pago":
                return new AdaptadorRealizarPagoMercadoPago();
            default:
                throw new Exception("Medio de pago no soportado");
        }
    }
}
