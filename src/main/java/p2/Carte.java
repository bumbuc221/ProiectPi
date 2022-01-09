package p2;

/**
 * Clasa <h1>Carte</h1> reprezinta fiecare carte din biblioteca
 * @version 1.0
 * @author Bumbuc Ivan
 */
public class Carte implements Comparable<Carte>{
    protected long id;                         // id-ul cartii care este unic pe fiecare carte
    protected String numeCarte;                 // numele cartii
    protected String autorCarte;                 // autorul cartii
    protected boolean disponibilitate;             // disponibilitatea cartii
    protected int puncte;                            //punctele oferite de catre carte in urma imprumutului
    protected String categorie;                       // categorie carte (genul cartii ,Ex: roman,poezii ...)
    protected String tema;                             // tema cartii pe ce se bazeaza cartea respectiva
    protected String descriere;                          // O scurta descriere a carti in cateva cuvinte subiectul cartii
    protected String cuvinteCheie;                     // cuvintele cheie asupra cartii ( Ex: iubire,razboi,natura, ...)
    protected int nrPagini;                           // nuamarul de pagini al cartii
    protected int raft;                              // numarul raftului unde se afla
    protected int sertar;                           // numarul sertarului unde se afla in raft
    public static long numarulDeCarti=0;           // numarul de carti in toatal in biblioteca
    protected String data;                        // in cazul in care este imprumutata prezinta data cand va fi disponibila
    protected int numarImprumuturi;
    /**
     * Constructor al clase <h1>Carte</h1>
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
     */
    public Carte(long id, String numeCarte, String autorCarte, String disponibil, int puncte,
                 String categorie, String tema, String descriere, String cuvinteCheie, int nrPagini, int raft, int sertar,String data,int nr)
    {
        this.id=id;
        this.numeCarte=numeCarte;
        this.autorCarte=autorCarte;
        this.disponibilitate= disponibil.equals("Disponibil");
        if(!disponibilitate)
            this.data=data;
        this.puncte=puncte;
        this.categorie=categorie;
        this.tema=tema;
        this.descriere=descriere;
        this.cuvinteCheie=cuvinteCheie;
        this.nrPagini=nrPagini;
        this.raft=raft;
        this.sertar=sertar;
        this.numarImprumuturi=nr;
        numarulDeCarti++;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setNumarImprumuturi(int numarImprumuturi) {
        this.numarImprumuturi = numarImprumuturi;
    }

    public String getData() {
        return data;
    }

    public int getNumarImprumuturi() {
        return numarImprumuturi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeCarte() {
        return numeCarte;
    }

    public void setNumeCarte(String numeCarte) {
        this.numeCarte = numeCarte;
    }

    public String getAutorCarte() {
        return autorCarte;
    }

    public void setAutorCarte(String autorCarte) {
        this.autorCarte = autorCarte;
    }

    public boolean isDisponibilitate() {
        return disponibilitate;
    }

    public void setDisponibilitate(boolean disponibilitate) {
        this.disponibilitate = disponibilitate;
    }

    public int getPuncte() {
        return puncte;
    }

    public void setPuncte(int puncte) {
        this.puncte = puncte;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getCuvinteCheie() {
        return cuvinteCheie;
    }

    public void setCuvinteCheie(String cuvinteCheie) {
        this.cuvinteCheie = cuvinteCheie;
    }

    public int getNrPagini() {
        return nrPagini;
    }

    public void setNrPagini(int nrPagini) {
        this.nrPagini = nrPagini;
    }

    public int getRaft() {
        return raft;
    }

    public void setRaft(int raft) {
        this.raft = raft;
    }

    public int getSertar() {
        return sertar;
    }

    public void setSertar(int sertar) {
        this.sertar = sertar;
    }

    public static long getNumarulDeCarti() {
        return numarulDeCarti;
    }

    public static void setNumarulDeCarti(long numarulDeCarti) {
        Carte.numarulDeCarti = numarulDeCarti;
    }

    /**
     * Metoda toString de afisare a obiectelor
     * @return
     */
    @Override
    public String toString() {
        return "Carte{" +
                "id=" + id +
                ", numeCarte='" + numeCarte + '\'' ;/*+
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
                '}';*/
    }
    @Override
    public int compareTo(Carte o) {
        if(this.numarImprumuturi<o.numarImprumuturi)
            return 1;
        else
            if(this.numarImprumuturi>o.numarImprumuturi)
                return -1;
            else return this.numeCarte.compareTo(o.numeCarte);
    }
}
