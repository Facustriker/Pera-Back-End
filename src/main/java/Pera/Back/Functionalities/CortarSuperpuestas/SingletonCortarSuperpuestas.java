package Pera.Back.Functionalities.CortarSuperpuestas;

import java.util.Date;

public class SingletonCortarSuperpuestas {
    private static SingletonCortarSuperpuestas instancia;

    public static SingletonCortarSuperpuestas getInstancia() {
        if(instancia == null) instancia = new SingletonCortarSuperpuestas();
        return instancia;
    }

    public void cortar(RepositorioCortable repo, Date fha, Date fhb, Long id) {
        repo.cortarPrevia(fha, fhb, id);
        repo.cortarPosterior(fha, fhb, id);
        repo.cortarIntermedias(fha, fhb, id);
        repo.dividirEnvolvente(fha, fhb, id);
        repo.cortarEnvolvente(fha, fhb, id);
    }

}
