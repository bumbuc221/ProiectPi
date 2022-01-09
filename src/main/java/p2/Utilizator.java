package p2;

/**
 * Clasa abstracta <h1>Utilizator</h1>
 * @version 1.2
 * @author Bumbuc Ivan
 */
public abstract class Utilizator {
    protected String cnpUtilizator; // cnp-ul utilizatorului
    protected String numeUtilizator; // numele utilizatorului
    protected String emailUtilizator; // email utilizator
    protected String serieUtilizator; // serie utilizator

    /**
     * Constructor cu parametri ai clase abstarcte <h1>Utilizator</h1>
     * @param cnp
     * @param nume
     * @param email
     * @param serie
     */
    public Utilizator(String cnp,String nume,String email,String serie)
    {
        this.cnpUtilizator=cnp;
        this.emailUtilizator=email;
        this.numeUtilizator=nume;
        this.serieUtilizator=serie;
    }

    public String getCnpUtilizator() {
        return cnpUtilizator;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public String getEmailUtilizator() {
        return emailUtilizator;
    }

    public String getSerieUtilizator() {
        return serieUtilizator;
    }

    public void setSerieUtilizator(String serieUtilizator) {
        this.serieUtilizator = serieUtilizator;
    }

    /**
     * Functia de afisare a clasei abstarcte <h1>Utilizator</h1>
     * @return
     */
    @Override
    public String toString()
    {
        return "Nume: "+numeUtilizator+", "+
                "CNP: "+cnpUtilizator+", "+
                "Email: "+emailUtilizator+", "+
                "Serie: "+serieUtilizator;
    }
}
