package p3;
import p2.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

/**
 * Clasa <h1>Date</h1>
 * <p>Reprezinta calsa unde colectioneaza datele din baza de date sql.
 * Cartile din toate categoriile.</p>
 * @version 1.0
 * @author Bumbuc Ivan
 */
public class Date {
    private Map<Integer,Carte> listaCarti; // colectie de tip map de carti
    private List<String> numeCartiCautare=new ArrayList<>();
    private Map<String,UtilizatorNormal> listaUtilizatori; //colectie de tip map de Utilizatori
    private Map<String,Administrator> listaAdministatori;
    private Map<Pereche,Imprumuturi> listaImprumuturi; //
    private Map<Pereche,Imprumuturi> listaIntarzieri; //
    private Map<String,Imprumuturi> listaUtimeleImprumuturiIntarizate; //
    private Map<String,Suspendari> listaSuspendari;
    private Map<String,List<Istoricul>> listaIstoric;
    private Map<String,Integer> listaNumarImprumutariCont;
    private long nrIstoric;
    private String email;
    /**
     * Contructorul fara parametri al clasei <h1>Date</h1>
     * <p>Se foloseste de functia preluare carti cati si de sortByValues pentru a sorta cartile dupa valoare</p>
     */
    public Date()
    {
        nrIstoric=0;
        listaCarti=new TreeMap<>(); // declarare
        preluareCarti();           // adaugare in map
        Map<Integer,Carte> listaCart2=sortByValues(listaCarti); // sortare dupa valoare
        listaCarti=new TreeMap<>(listaCart2);                 // reinitializare
        listaUtilizatori=new TreeMap<>();   // decalarare utilizatori
        preluareUtilizatori();            // atribuire utilizatori
        listaAdministatori=new TreeMap<>();
        preluareAdministatori();
        listaNumarImprumutariCont=new TreeMap<>();
        listaImprumuturi=new TreeMap<>((o1, o2) -> {
            if(o1.getId()<o2.getId())
                return -1;
            else {
                if (o1.getId() > o2.getId())
                    return 1;
                else {
                    return o1.getCnp().compareTo(o2.getCnp());
                }
            }
        });
        preluareImprumuturi();
        listaIntarzieri=new TreeMap<>((o1, o2) -> {
            if(o1.getId()<o2.getId())
                return -1;
            else {
                if (o1.getId() > o2.getId())
                    return 1;
                else {
                    return o1.getCnp().compareTo(o2.getCnp());
                }
            }
        });
        preluareIntarzieri();
        listaUtimeleImprumuturiIntarizate=new TreeMap<>();
        preluareUltimeleIntarzieri();
        listaSuspendari=new TreeMap<>();
        preluareSuspendari();
        listaIstoric=new TreeMap<>();
        preluareIstoric();
    }

    /**
     * Functia de conectare la baza de date Biblioteca
     */
    private void preluareCarti() {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Carti";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {  // adaugare in map
                int carteId=r.getInt(1),cartePuncte=r.getInt(5),nrPagini=r.getInt(10),
                raft=r.getInt(11),sertar=r.getInt(12),nrImprumuturi=r.getInt(13),
                puncteBonus=r.getInt(15),raritate=r.getInt(16);
                String nume=r.getString(2),autor=r.getString(3),disponibilitate=r.getString(4),
                        categorie=r.getString(6),tema=r.getString(7),descriere=r.getString(8),
                        cuvinteCheie=r.getString(9),dataDisponibila=r.getString(14);
                numeCartiCautare.add(nume);
                if(puncteBonus==0)
                    listaCarti.put(carteId,new Carte(carteId,nume,autor,disponibilitate,cartePuncte,categorie,tema,descriere,cuvinteCheie,nrPagini,raft,sertar,dataDisponibila,nrImprumuturi));
                else
                    listaCarti.put(carteId,new CarteSpeciala(carteId,nume,autor,disponibilitate,cartePuncte,categorie,tema,descriere,cuvinteCheie,nrPagini,raft,sertar,nrImprumuturi,puncteBonus,raritate,dataDisponibila));
            }

        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    private void preluareUtilizatori()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Utilizatori";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {  // adaugare in map
                String cnp=r.getString(1),nume=r.getString(2),
                        email=r.getString(3),serie=r.getString(4),
                        dataInregistrare=r.getString(5);
                int punctePlus=r.getInt(6),puncteMinus=r.getInt(7);
                boolean suspendat=false;
                if(r.getString(8)!=null)
                    suspendat=true;
                String dataDisponibila=r.getString(9),istoric=r.getString(10);
                List<Istoric> lista=istoricGenerare(istoric);
                listaUtilizatori.put(cnp,new UtilizatorNormal(cnp,nume,email,serie,dataInregistrare,punctePlus,puncteMinus,suspendat,dataDisponibila,lista));
            }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }
    private void preluareAdministatori()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Administratori";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                String numeAdmin=r.getString(1),emailAdmin=r.getString(2),
                        p=r.getString(3),cnpAdmin=r.getString(4),serieAdmin=r.getString(5);
                listaAdministatori.put(emailAdmin,new Administrator(cnpAdmin,numeAdmin,emailAdmin,serieAdmin,p));
            }
            } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }

    }
    private void preluareImprumuturi()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Imprumuturi";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                String cnp=r.getString(1),numeU=r.getString(3),
                        email=r.getString(4),serie=r.getString(5),
                        numeC=r.getString(6),dataI=r.getString(7),dataL=r.getString(8);
                int id=r.getInt(2);
                listaImprumuturi.put(new Pereche(cnp,id),new Imprumuturi(cnp,id,numeU,email,serie,numeC,dataI,dataL));
                if(listaNumarImprumutariCont.containsKey(cnp))
                {
                    listaNumarImprumutariCont.put(cnp,listaNumarImprumutariCont.get(cnp)+1);
                }
                else {
                    listaNumarImprumutariCont.put(cnp, 1);
                }
                }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }
    private void preluareIntarzieri()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Imprumuturi where GETDATE()>DataReturnare";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                String cnp=r.getString(1),numeU=r.getString(3),
                        email=r.getString(4),serie=r.getString(5),
                        numeC=r.getString(6),dataI=r.getString(7),dataL=r.getString(8);
                int id=r.getInt(2);
                listaIntarzieri.put(new Pereche(cnp,id),new Imprumuturi(cnp,id,numeU,email,serie,numeC,dataI,dataL));
            }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    private void preluareUltimeleIntarzieri()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Imprumuturi where GETDATE()>DataReturnare order by DataReturnare DESC";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                String cnp=r.getString(1),numeU=r.getString(3),
                        email=r.getString(4),serie=r.getString(5),
                        numeC=r.getString(6),dataI=r.getString(7),dataL=r.getString(8);
                int id=r.getInt(2);
                listaUtimeleImprumuturiIntarizate.put(cnp,new Imprumuturi(cnp,id,numeU,email,serie,numeC,dataI,dataL));
            }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    private void preluareSuspendari()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Suspendari";   // selectarea cartilor
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                String cnp=r.getString(1),numeU=r.getString(2),
                        email=r.getString(3),serie=r.getString(4),
                        numeC=r.getString(6),dataI=r.getString(7);
                int id=r.getInt(5);
                listaSuspendari.put(cnp,new Suspendari(cnp,numeU,email,serie,id,numeC,dataI));
            }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    private void preluareIstoric()
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String selectSql = "select * from Istoric";   // selectarea Istoricului
            r = statement.executeQuery(selectSql); // impartire in set
            while (r.next()) {
                long idIstoric=r.getLong(1);
                String cnp=r.getString(2),numeU=r.getString(3);
                int id=r.getInt(4);
                String numeC=r.getString(5),categorie=r.getString(6),dataIn=r.getString(7),dataSf=r.getString(8),status=r.getString(9);
                if(listaIstoric.containsKey(cnp))
                {
                    List<Istoricul> aux=new ArrayList<>(listaIstoric.get(cnp));
                    aux.add(new Istoricul(idIstoric,cnp,numeU,id,numeC,categorie,dataIn,dataSf,status));
                    listaIstoric.put(cnp,aux);
                }
                else
                {
                    List<Istoricul> aux=new ArrayList<>();
                    aux.add(new Istoricul(idIstoric,cnp,numeU,id,numeC,categorie,dataIn,dataSf,status));
                    listaIstoric.put(cnp,aux);
                }
                nrIstoric=idIstoric;
            }
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getListaNumarImprumutariCont() {
        return listaNumarImprumutariCont;
    }

    public void adaugareImprumut(Pereche a, Imprumuturi b)
    {
        listaImprumuturi.put(a,b);
    }

    public Map<String, List<Istoricul>> getListaIstoric() {
        return listaIstoric;
    }

    public Map<String, Imprumuturi> getListaUtimeleImprumuturiIntarizate() {
        return listaUtimeleImprumuturiIntarizate;
    }

    public Map<String, Suspendari> getListaSuspendari() {
        return listaSuspendari;
    }

    public Map<Pereche, Imprumuturi> getListaIntarzieri() {
        return listaIntarzieri;
    }

    public Map<Pereche, Imprumuturi> getListaImprumuturi() {
        return listaImprumuturi;
    }

    public void adaugareCarteDate(Carte a, int x)
    {
        listaCarti.put(x,a);
    }
    public void adaugareUtilizator(UtilizatorNormal a,String cnp)
    {
        listaUtilizatori.put(cnp,a);
    }
    public Map<String, Administrator> getListaAdministatori() {
        return listaAdministatori;
    }

    public Map<String, UtilizatorNormal> getListaUtilizatori() {
        return listaUtilizatori;
    }

    public long getNrIstoric() {
        return nrIstoric;
    }

    public void setNrIstoric(long nrIstoric) {
        this.nrIstoric = nrIstoric;
    }

    /**
     * Metaoda genereaza continutul istoricului al fiecarui utilizaotr(desparte de virgula)
     * @param a
     * @return
     */
    private List<Istoric> istoricGenerare(String a)
    {
        List<Istoric> lista=new ArrayList<>();
        if(a!=null) {
            String[] nou = a.split(",");
            for (int i = 0; i < nou.length; i += 3) {
                lista.add(new Istoric(nou[i + 1], nou[i + 2], nou[i]));
            }
        }
        return lista;
    }

    public List<String> getNumeCartiCautare() {
        return numeCartiCautare;
    }

    public Map<Integer, Carte> getListaCarti() {
        return listaCarti;
    }

    /**
     * Functia de afisare a topului care returneaza o lista cu indicii cartilor in functie de top
     * @param categorie categoria de carti (ex:fizica,roman,poezii,...)
     * @return lista cu indicii cartilor in functie de categorie
     */
    public List<Integer> afisareTop(String categorie)
    {
        List<Integer> carti=new ArrayList<>(); // lista
        int i=1;
        if(categorie.equals("Toate"))  // daca este egala cu toate pur si simplu luam din toate
        {
            for(Integer x:listaCarti.keySet())
            {
                if(i==11)
                    break;
                carti.add(x);
                i++;
            }
        }else  // altfel luam maxim 10 carti din categorie (cartile sunt sortate in functie de categorie)
            for(Integer x:listaCarti.keySet())
            {
                if(i==11)
                    break;
                if(listaCarti.get(x).getCategorie().equals(categorie))
                {
                    i++;
                    carti.add(x);
                }
            }
            return carti;
    }

    /**
     * Functia de afisare a datelor preluate din baza de date
     */
    public void afis()
    {
        for(Map.Entry<Integer,Carte> x:listaCarti.entrySet())
            System.out.println(x.getKey()+"   "+ x.getValue());
    }

    /**
     * Functia de comaparare in functie de valoare.
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<V>> Map<K, V>
    sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k1).compareTo(map.get(k2));
                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };

        Map<K, V> sortedByValues =
                new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
