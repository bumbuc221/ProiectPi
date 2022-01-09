package p2;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa <h1>UtilizatorNormal</h1> este clasa de utilizatori abonati
 * @version 1.3
 * @author Bumbuc Ivan
 */
public class UtilizatorNormal extends Utilizator{
    private String dataInregistrare;      // data inregistrari
    private int punctePlus;              // punctele adunate de la imorumuturi
    private int puncteMinus;            // puntele minus in cazul intarzieri
    private boolean suspendat;         // suspendat reprezinta daca nu mai are voie o periaoda la imrpumutare
    private String dataDisponibiLaImprumut; // in cazul suspendarii prezinta data cand are voie la imprumut
    private List<Istoric> istoricImprumuturi; // reprezinta istoricul imprumuturilor e pana in ziua respectiva

    /**
     * Constructorul cu parametri ai clasei UtilizatorNormal
     * @param cnp
     * @param nume
     * @param email
     * @param serie
     * @param dataInregistrare
     * @param punctePlus
     * @param puncteMinus
     * @param suspendat
     * @param dataDisponibiLaImprumut
     * @param istoricImprumuturi
     */
    public UtilizatorNormal(String cnp, String nume, String email, String serie, String dataInregistrare, int punctePlus, int puncteMinus, boolean suspendat, String dataDisponibiLaImprumut, List<Istoric> istoricImprumuturi) {
        super(cnp, nume, email, serie);
        this.dataInregistrare = dataInregistrare;
        this.punctePlus = punctePlus;
        this.puncteMinus = puncteMinus;
        this.suspendat = suspendat;
        this.dataDisponibiLaImprumut = dataDisponibiLaImprumut;
        this.istoricImprumuturi = new ArrayList<>(istoricImprumuturi);
    }

    public String getDataInregistrare() {
        return dataInregistrare;
    }

    public int getPunctePlus() {
        return punctePlus;
    }

    public void setPunctePlus(int punctePlus) {
        this.punctePlus = punctePlus;
    }

    public int getPuncteMinus() {
        return puncteMinus;
    }

    public void setPuncteMinus(int puncteMinus) {
        this.puncteMinus = puncteMinus;
    }

    public boolean isSuspendat() {
        return suspendat;
    }

    public void setSuspendat(boolean suspendat) {
        this.suspendat = suspendat;
    }

    public String getDataDisponibiLaImprumut() {
        return dataDisponibiLaImprumut;
    }

    public void setDataDisponibiLaImprumut(String dataDisponibiLaImprumut) {
        this.dataDisponibiLaImprumut = dataDisponibiLaImprumut;
    }

    public List<Istoric> getIstoricImprumuturi() {
        return istoricImprumuturi;
    }

    public void setIstoricImprumuturi(List<Istoric> istoricImprumuturi) {
        this.istoricImprumuturi = istoricImprumuturi;
    }

    /**
     * Functia de afisare a utilizatorilor
     * @return
     */
    @Override
    public String toString() {
        return "UtilizatorNormal{" +
                "CNP='" + cnpUtilizator + '\'' +
                ", Nume='" + numeUtilizator + '\'' +
                ", Email='" + emailUtilizator + '\'' +
                ", Serie='" + serieUtilizator + '\'' +
                ", Data Inregistrare='" + dataInregistrare + '\'' +
                ", Puncte=" + punctePlus +
                ", Puncte Minus=" + puncteMinus +
                ", Suspendat=" + suspendat +
                ", Data Disponibi La Imprumut='" + dataDisponibiLaImprumut + '\'' +
                ", Istoric Imprumuturi=" + istoricImprumuturi +
                '}';
    }
}
