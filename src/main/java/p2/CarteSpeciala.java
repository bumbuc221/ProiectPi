package p2;

/**
 * Clasa <h1>CarteSpeciala</h1> mostennita din clasa <h1>Carte</h1>
 * @version 1.1
 * @author Bumbuc Ivan
 */
public class CarteSpeciala extends Carte{
    private int puncteBonus;   // punctele (extra) bonus pe care le ofera cartea in plus
    private int raritate;     // reprezinta raritatea adica daca utilizaotrul are puncte>= raritate atunci poate imprumuta cartea

    /**
     * Constructor al clasei <h1>CarteSpeciala</h1>
     * @param id
     * @param numeCarte
     * @param autorCarte
     * @param disponibil
     * @param puncte
     * @param categorie
     * @param tema
     * @param descriere
     * @param cuvinteCheie
     * @param nrPagini
     * @param raft
     * @param sertar
     * @param data
     */
    public CarteSpeciala(long id, String numeCarte, String autorCarte, String disponibil, int puncte, String categorie, String tema, String descriere, String cuvinteCheie, int nrPagini, int raft, int sertar,int nr,int puncteBonus,int raritate, String data) {
        super(id, numeCarte, autorCarte, disponibil, puncte, categorie, tema, descriere, cuvinteCheie, nrPagini, raft, sertar, data,nr);
        this.puncteBonus=puncteBonus;
        this.raritate=raritate;
    }

    public int getPuncteBonus() {
        return puncteBonus;
    }

    public int getRaritate() {
        return raritate;
    }

    /**
     * Functia de afisare a obiectelor
     * @return
     */
    @Override
    public String toString() {
        return "CarteSpeciala{"+"id=" + id +
                ", numeCarte='" + numeCarte + '\'' +
                ", autorCarte='" + autorCarte + '\'' +
                ", disponibilitate=" + disponibilitate +
                ", puncte=" + puncte +
                ", categorie='" + categorie + '\'' +
                ", tema='" + tema + '\'' +
                ", descriere='" + descriere + '\'' +
                ", cuvinteCheie='" + cuvinteCheie + '\'' +
                ", nrPagini=" + nrPagini +
                ", raft=" + raft +
                ", sertar=" + sertar +
                "puncteBonus=" + puncteBonus +
                ", raritate=" + raritate +
                '}';
    }
}
