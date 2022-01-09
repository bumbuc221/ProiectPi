package p2;

public class Istoricul {
    private long id;
    private String cnp,numeUtilizator;
    private int idCarte;
    private String numeCarte,categorieCarte;
    private String dataInchiriere,dataReturnare;
    private String status;

    public Istoricul(long id, String cnp, String numeUtilizator, int idCarte, String numeCarte, String categorieCarte, String dataInchiriere, String dataReturnare, String status) {
        this.id = id;
        this.cnp = cnp;
        this.numeUtilizator = numeUtilizator;
        this.idCarte = idCarte;
        this.numeCarte = numeCarte;
        this.categorieCarte = categorieCarte;
        this.dataInchiriere = dataInchiriere;
        this.dataReturnare = dataReturnare;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNumeUtilizator() {
        return numeUtilizator;
    }

    public void setNumeUtilizator(String numeUtilizator) {
        this.numeUtilizator = numeUtilizator;
    }

    public int getIdCarte() {
        return idCarte;
    }

    public void setIdCarte(int idCarte) {
        this.idCarte = idCarte;
    }

    public String getNumeCarte() {
        return numeCarte;
    }

    public void setNumeCarte(String numeCarte) {
        this.numeCarte = numeCarte;
    }

    public String getCategorieCarte() {
        return categorieCarte;
    }

    public void setCategorieCarte(String categorieCarte) {
        this.categorieCarte = categorieCarte;
    }

    public String getDataInchiriere() {
        return dataInchiriere;
    }

    public void setDataInchiriere(String dataInchiriere) {
        this.dataInchiriere = dataInchiriere;
    }

    public String getDataReturnare() {
        return dataReturnare;
    }

    public void setDataReturnare(String dataReturnare) {
        this.dataReturnare = dataReturnare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Istoricul{" +
                "id=" + id +
                ", cnp='" + cnp + '\'' +
                ", numeUtilizator='" + numeUtilizator + '\'' +
                ", idCarte=" + idCarte +
                ", numeCarte='" + numeCarte + '\'' +
                ", categorieCarte='" + categorieCarte + '\'' +
                ", dataInchiriere='" + dataInchiriere + '\'' +
                ", dataReturnare='" + dataReturnare + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
