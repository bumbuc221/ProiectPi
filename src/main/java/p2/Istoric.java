package p2;

/**
 * Clasa <h1>Istoric</h1>
 * <d>Reprezinta tot istoricul utilizatorului de imprumuturi ale cartilor</d>
 * @version 1.5
 * @author Bumbuc Ivan
 */
public class Istoric {
    private String dataImprumut; //data imprumutari cartii respective
    private String dataAducere;  // data aducerii cartii respective
    private String c;            // cartea imprumutata in periaoda respectiva

    /**
     * Constructorul cu parametri ai clasei <h1>Istoric</h1>
     * @param dataImprumut
     * @param dataAducere
     * @param c
     */
    public Istoric(String dataImprumut,String dataAducere,String c)
    {
        this.dataAducere=dataAducere;
        this.dataImprumut=dataImprumut;
        this.c=c;
    }

    public String getDataImprumut() {
        return dataImprumut;
    }

    public String getDataAducere() {
        return dataAducere;
    }

    public String getC() {
        return c;
    }

    /**
     * Functia de afisare a clasei <h1>Istoric</h1>
     * @return
     */
    @Override
    public String toString()
    {
        return "Nume Carte: "+c+", "+
                "Data Imprumutari: "+dataImprumut+", "+
                "Data Aduceri: "+dataAducere+"\n";
    }
}
