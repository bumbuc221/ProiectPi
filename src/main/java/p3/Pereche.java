package p3;

public class Pereche{
    private String cnp;
    private int id;

    public Pereche(String cnp, int id) {
        this.cnp = cnp;
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pereche{" +
                "cnp='" + cnp + '\'' +
                ", id=" + id +
                '}';
    }

}
