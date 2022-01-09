package p2;

/**
 * Clasa <h1>UtilizatorNeabonat</h1>
 *<p> Clasa reprezinta utilizatorii care nu vor sa se aboneze
 * ci vor doar un imprumut simplu asadar nu se vor contoriza punctele plus
 * pentru a avea beneficii la unele carti speciale in schimb punctele minus se aduna
 * in cazul in care nu aduce la timp si va fi retinut ca sa nu poata imprumuta o perioada de timp
 * in cazul in care nu aduce la timp cartea </p>
 * @version 1.6
 * @author Bumbuc Ivan
 */
public class UtilizatorNeabonat extends Utilizator{
    private String dataImprumut;               //data imprumutului
    private String dataAducere;           // data de aducere
    private int puncteMinus;                 // punctele minus in cazul in care acesta nu aduce
                                             // la timp tot va avea puncte minus si va fii retinut
                                             // ca sa nu poata imrumuta pana in data in care a primit interzicere
    private String Intarziat;             // statusul de intarziat sau nu ( chiar daca nu este abonat nu va avea acces daca este intarziat )

    /**
     * Constructorul cu parametrii ai clase <h1>Utilizator Neabonat</h1>
     * @param cnp
     * @param nume
     * @param email
     * @param serie
     * @param dataImprumut
     * @param dataDataAducere
     * @param puncteMinus
     * @param intarziat
     */
    public UtilizatorNeabonat(String cnp, String nume, String email, String serie, String dataImprumut, String dataDataAducere, int puncteMinus, String intarziat) {
        super(cnp, nume, email, serie);
        this.dataImprumut = dataImprumut;
        this.dataAducere = dataDataAducere;
        this.puncteMinus = puncteMinus;
        Intarziat = intarziat;
    }

    public String getDataImprumut() {
        return dataImprumut;
    }

    public String getDataAducere() {
        return dataAducere;
    }

    public int getPuncteMinus() {
        return puncteMinus;
    }

    public void setPuncteMinus(int puncteMinus) {
        this.puncteMinus = puncteMinus;
    }

    public String getIntarziat() {
        return Intarziat;
    }

    public void setIntarziat(String intarziat) {
        Intarziat = intarziat;
    }

    /**
     * Functia de afisare a calsei <h1>Utilizator Neabonat</h1>
     * @return
     */
    @Override
    public String toString() {
        return "UtilizatorNeabonat{" +
                " CNP='" + cnpUtilizator + '\'' +
                ", Nume='" + numeUtilizator + '\'' +
                ", Email='" + emailUtilizator + '\'' +
                ", Serie='" + serieUtilizator + '\'' +
                ", Data Imprumut='" + dataImprumut + '\'' +
                ", Data Aducere='" + dataAducere + '\'' +
                ", Puncte Minus=" + puncteMinus +
                ", Intarziat='" + Intarziat + '\'' +
                '}';
    }
}
