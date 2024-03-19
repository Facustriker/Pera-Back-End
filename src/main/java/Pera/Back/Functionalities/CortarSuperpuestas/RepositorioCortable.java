package Pera.Back.Functionalities.CortarSuperpuestas;

import java.util.Date;

public interface RepositorioCortable {


    public void cortarPrevia(Date fha, Date fhb, Long id);
    public void cortarIntermedias(Date fha, Date fhb, Long id);
    public void cortarPosterior(Date fha, Date fhb, Long id);
    public void dividirEnvolvente(Date fha, Date fhb, Long id);
    public void cortarEnvolvente(Date fha, Date fhb, Long id);

}
