package p4;

import p2.*;
import p3.Date;
import p3.Pereche;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FereastraReturnareImprumut {
    private JFrame window;
    private JPanel panel;
    private JLabel fundal;
    private JButton next;
    private JTextField cnp,id;
    private JTextPane cnpText,idText;
    public FereastraReturnareImprumut(Date info,JFrame x)
    {
        setWindow();
        setFundal();
        setText();
        setTextField();
        next=Butoane.setNext(190,220);
        panel.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnare(info,x);
            }
        });
        panel.add(fundal);
        window.add(panel);
        window.setLayout(null);
        window.setVisible(true);
    }
    private void setWindow()
    {
        window=new JFrame("Returnare Imprumut");
        panel=new JPanel();
        window.setSize(450,350);
        panel.setBounds(0,0,435,320);// dimensiunea
        panel.setLayout(null);
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }
    private void setFundal()
    {
        fundal=new JLabel();
        fundal.setBounds(0,0,435,320);
        fundal.setBackground(Color.white);     // background alb
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\retrunareImprumut.png").getImage().getScaledInstance(435, 320, 4))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
    }
    private void setText()
    {
        cnpText=Butoane.textPane(30,60,100,30,"CNP");
        panel.add(cnpText);
        idText=Butoane.textPane(30,120,100,30,"Id Carte");
        panel.add(idText);
    }
    private void setTextField()
    {
        cnp=Butoane.textField(150,60,200,30,13);
        panel.add(cnp);
        id=Butoane.textField(150,120,200,30,0);
        panel.add(id);
    }

    void returnare(Date info,JFrame f)
    {
        if(cnp.getText().length()>0 && id.getText().length()>0) {
            if (cnp.getText().length() == 13) {
                if (Butoane.isDigit(cnp.getText())) {
                    if (Butoane.isDigit(id.getText())) {
                        if(info.getListaImprumuturi().containsKey(new Pereche(cnp.getText(),Integer.parseInt(id.getText()))))
                        {
                            fereastraConfirm(info,f);
                        }
                        else
                            Butoane.setFereastraAux("Imprumut invalid!", "avertismentAddC.png");
                    } else
                        Butoane.setFereastraAux("Id carte format incorect!", "avertismentAddC.png");
                } else
                    Butoane.setFereastraAux("CNP format incorect!", "avertismentAddC.png");
            }
            else
                Butoane.setFereastraAux("Lungime cnp invalid!","avertismentAddC.png");
        }
        else
            Butoane.setFereastraAux("Completati toate campurile!","avertismentAddC.png");
    }

    private int setImprumut(Date info)
    {
        String data=info.getListaImprumuturi().get(new Pereche(cnp.getText(),Integer.parseInt(id.getText()))).getDataLimita();
        Imprumuturi imprumut=info.getListaImprumuturi().get(new Pereche(cnp.getText(),Integer.parseInt(id.getText())));
        long dif=diferenta(data);
        int zileSuspendare=0;
        if(dif>0)
            zileSuspendare=(int)dif/2;
        int punctePentruIntarziere=(int)dif*20;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(zileSuspendare>0) { // imprumut Intarziat
            if (info.getListaUtilizatori().containsKey(cnp.getText())) { // daca este abonat
                if (!info.getListaUtilizatori().get(cnp.getText()).isSuspendat()) { // daca nu este suspendat
                    LocalDateTime now = LocalDateTime.now();   // adaugam fata de data de azi altfel fata de data care a venit
                    String dataPlus = dtf.format(now.plusDays(zileSuspendare));
                    System.out.println(dataPlus);
                    actualizareReturnIntarziatAbonat(imprumut,info,cnp.getText(),id.getText(),punctePentruIntarziere,dataPlus);
                } else {// daca este suspendat adaugam la zile nr de zile
                    String dataS = info.getListaUtilizatori().get(cnp.getText()).getDataDisponibiLaImprumut();
                    String[] desparteCuvinte = dataS.split("-");
                    LocalDate d = LocalDate.of(Integer.parseInt(desparteCuvinte[0]), Integer.parseInt(desparteCuvinte[1]), Integer.parseInt(desparteCuvinte[2])).plusDays(zileSuspendare);
                    System.out.println(d);
                    String dataP=d+"";
                    actualizareReturnIntarziatAbonat(imprumut,info,cnp.getText(),id.getText(),punctePentruIntarziere,dataP);
                }
            } else {
                if (info.getListaSuspendari().containsKey(cnp.getText())) {
                    String dataS = info.getListaSuspendari().get(cnp.getText()).getDataAcces();
                    String[] desparteCuvinte = dataS.split("-");
                    LocalDate d = LocalDate.of(Integer.parseInt(desparteCuvinte[0]), Integer.parseInt(desparteCuvinte[1]), Integer.parseInt(desparteCuvinte[2])).plusDays(zileSuspendare);
                    System.out.println(d);
                    String dataAcces=d+"";
                    actualizareReturnIntarziatNeabonat(imprumut,info,cnp.getText(),id.getText(),dataAcces);
                } else {
                    LocalDateTime now = LocalDateTime.now();   // adaugam fata de data de azi altfel fata de data care a venit
                    String dataPlus = dtf.format(now.plusDays(zileSuspendare));
                    System.out.println(dataPlus);
                    actualizareReturnIntarziatNeabonat(imprumut,info,cnp.getText(),id.getText(),dataPlus);
                }
            }
        }
        else
        {
            returnImprumutNeintariatAbonat(imprumut,info,cnp.getText(),id.getText());
        }
        return (int)dif;
    }

    private void actualizareReturnIntarziatAbonat(Imprumuturi x,Date info,String cnp,String id,int pct,String dataD)
    {
        Carte carte=info.getListaCarti().get(Integer.parseInt(id));
        UtilizatorNormal ut=info.getListaUtilizatori().get(cnp);
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String deleteSql = "DELETE FROM Imprumuturi where CnpUtilizator='"+cnp+"' and IdCarte="+id;   // selectarea Istoricului
            String insertSql = "INSERT INTO  Istoric(CNP,NumeUtilizator,IdCarte,NumeCarte,Categorie,DataImprumutare,DataReturnare,StatusIntarziere)"+
                    "VALUES("+"'"+cnp+"'"+","+"'"+x.getNumeUtilizator()+"'"+","+Integer.parseInt(id)+","+"'"+carte.getNumeCarte()+"'"+","+"'"+carte.getCategorie()+"'"+","+"'"+x.getDataImprumut()+"'"+","+"GETDATE()"+","+"'"+"DA"+"'"+")";
            String updateSqlUtilizatori="UPDATE Utilizatori SET PunctePlus="+"'"+(ut.getPunctePlus()-pct)+"'"+", PuncteMinus='"+(ut.getPuncteMinus()+1)+"', Suspendat='DA', DataDisponibilaLaImprumut='"+dataD+"'"+" where CNP="+"'"+cnp+"'";
            String updateSqlCarte="UPDATE Carti SET DataDisponibilitate=NULL , Disponibilitate='Disponibil' where CarteId="+Integer.parseInt(id);
            statement.executeUpdate(deleteSql);
            statement.executeUpdate(insertSql);
            statement.executeUpdate(updateSqlUtilizatori);
            statement.executeUpdate(updateSqlCarte);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        String dataAzi=java.time.LocalDate.now()+"";
        if(info.getListaIstoric().containsKey(cnp))
        {
            List<Istoricul> aux=new ArrayList<>(info.getListaIstoric().get(cnp));
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        else
        {
            List<Istoricul> aux=new ArrayList<>();
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        carte.setDisponibilitate(true);
        ut.setPunctePlus(ut.getPunctePlus()-pct);
        ut.setPuncteMinus(ut.getPuncteMinus()+1);
        ut.setSuspendat(true);
        ut.setDataDisponibiLaImprumut(dataD);
        if(info.getListaIntarzieri().containsKey(new Pereche(cnp,Integer.parseInt(id))))
        {
            info.getListaIntarzieri().remove(new Pereche(cnp,Integer.parseInt(id)));
        }
        if(info.getListaUtimeleImprumuturiIntarizate().containsKey(cnp) && info.getListaUtimeleImprumuturiIntarizate().get(cnp).getId()==Integer.parseInt(id))
            info.getListaUtimeleImprumuturiIntarizate().remove(cnp);
        info.getListaImprumuturi().remove(new Pereche(cnp,Integer.parseInt(id)));
        info.getListaNumarImprumutariCont().put(cnp,info.getListaNumarImprumutariCont().get(cnp)-1);
    }
    private void actualizareReturnIntarziatNeabonat(Imprumuturi x,Date info,String cnp,String id,String dataD)
    {
        Carte carte=info.getListaCarti().get(Integer.parseInt(id));
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String deleteSql = "DELETE FROM Imprumuturi where CnpUtilizator='"+cnp+"' and IdCarte="+id;   // selectarea Istoricului
            String insertSql = "INSERT INTO  Istoric(CNP,NumeUtilizator,IdCarte,NumeCarte,Categorie,DataImprumutare,DataReturnare,StatusIntarziere)"+
                    "VALUES("+"'"+cnp+"'"+","+"'"+x.getNumeUtilizator()+"'"+","+Integer.parseInt(id)+","+"'"+carte.getNumeCarte()+"'"+","+"'"+carte.getCategorie()+"'"+","+"'"+x.getDataImprumut()+"'"+","+"GETDATE()"+","+"'"+"DA"+"'"+")";
            String updateSqlSuspendari="UPDATE Suspendari SET DataAcces='"+dataD+"'"+" where CnpUtilizator="+"'"+cnp+"'";
            String updateSqlCarte="UPDATE Carti SET DataDisponibilitate=NULL , Disponibilitate='Disponibil' where CarteId="+Integer.parseInt(id);
            statement.executeUpdate(deleteSql);
            statement.executeUpdate(insertSql);
            statement.executeUpdate(updateSqlSuspendari);
            statement.executeUpdate(updateSqlCarte);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        String dataAzi=java.time.LocalDate.now()+"";
        if(info.getListaIstoric().containsKey(cnp))
        {
            List<Istoricul> aux=new ArrayList<>(info.getListaIstoric().get(cnp));
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        else
        {
            List<Istoricul> aux=new ArrayList<>();
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        carte.setDisponibilitate(true);
        if(info.getListaIntarzieri().containsKey(new Pereche(cnp,Integer.parseInt(id))))
        {
            info.getListaIntarzieri().remove(new Pereche(cnp,Integer.parseInt(id)));
        }
        if(info.getListaUtimeleImprumuturiIntarizate().containsKey(cnp) && info.getListaUtimeleImprumuturiIntarizate().get(cnp).getId()==Integer.parseInt(id))
            info.getListaUtimeleImprumuturiIntarizate().remove(cnp);
        info.getListaImprumuturi().remove(new Pereche(cnp,Integer.parseInt(id)));
        info.getListaNumarImprumutariCont().put(cnp,info.getListaNumarImprumutariCont().get(cnp)-1);
    }
    private void returnImprumutNeintariatAbonat(Imprumuturi x,Date info,String cnp,String id)
    {
        Carte carte=info.getListaCarti().get(Integer.parseInt(id));
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String deleteSql = "DELETE FROM Imprumuturi where CnpUtilizator='"+cnp+"' and IdCarte="+id;   // selectarea Istoricului
            String insertSql = "INSERT INTO  Istoric(CNP,NumeUtilizator,IdCarte,NumeCarte,Categorie,DataImprumutare,DataReturnare,StatusIntarziere)"+
                    "VALUES("+"'"+cnp+"'"+","+"'"+x.getNumeUtilizator()+"'"+","+Integer.parseInt(id)+","+"'"+carte.getNumeCarte()+"'"+","+"'"+carte.getCategorie()+"'"+","+"'"+x.getDataImprumut()+"'"+","+"GETDATE()"+","+"'"+"NU"+"'"+")";
            String updateSqlCarte="UPDATE Carti SET DataDisponibilitate=NULL , Disponibilitate='Disponibil' where CarteId="+Integer.parseInt(id);
            statement.executeUpdate(deleteSql);
            statement.executeUpdate(insertSql);
            statement.executeUpdate(updateSqlCarte);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        String dataAzi=java.time.LocalDate.now()+"";
        if(info.getListaIstoric().containsKey(cnp))
        {
            List<Istoricul> aux=new ArrayList<>(info.getListaIstoric().get(cnp));
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        else
        {
            List<Istoricul> aux=new ArrayList<>();
            aux.add(new Istoricul(info.getNrIstoric(),cnp,x.getNumeUtilizator(),x.getId(),x.getNumeCarte(),carte.getCategorie(),x.getDataImprumut(),dataAzi,"DA"));
            info.getListaIstoric().put(cnp,aux);
            info.setNrIstoric(info.getNrIstoric()+1);
        }
        carte.setDisponibilitate(true);
        if(info.getListaIntarzieri().containsKey(new Pereche(cnp,Integer.parseInt(id))))
        {
            info.getListaIntarzieri().remove(new Pereche(cnp,Integer.parseInt(id)));
        }
        if(info.getListaUtimeleImprumuturiIntarizate().containsKey(cnp) && info.getListaUtimeleImprumuturiIntarizate().get(cnp).getId()==Integer.parseInt(id))
            info.getListaUtimeleImprumuturiIntarizate().remove(cnp);
        info.getListaImprumuturi().remove(new Pereche(cnp,Integer.parseInt(id)));
        info.getListaNumarImprumutariCont().put(cnp,info.getListaNumarImprumutariCont().get(cnp)-1);
    }

    private long diferenta(String a)
    {
        SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");
        String b = obj.format(new java.util.Date());
        long days_difference=-1;
        try {
            java.util.Date date1 = obj.parse(a);
            java.util.Date date2 = obj.parse(b);
            long time_difference = date2.getTime() - date1.getTime();
            days_difference = (time_difference / (1000*60*60*24)) % 365;
            return days_difference;
        }
        // Catch parse exception
        catch (ParseException excep) {
            excep.printStackTrace();
        }
        return days_difference;
    }

    private void fereastraConfirm(Date info,JFrame ferestraUt)
    {
        JFrame f=new JFrame();
        f.setSize(400,250);                       // dimensiunea
        f.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        f.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        f.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,230);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\confirmAddC.png").getImage().getScaledInstance(385, 230, 4)));
        fundal.setOpaque(true);
        JButton ok=new JButton();
        ok.setBounds(165,150,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,146,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,150,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ok=setImprumut(info);
                setFereastraAux(info,ferestraUt,cnp.getText(),ok);
                window.dispose();
                f.dispose();
            }
        });
        JTextPane text=new JTextPane();
        text.setBounds(100,40,250,100);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        text.setText("Doriti sa continuati ?");
        text.setFont(new Font("Arial",Font.BOLD,20));
        ok.addMouseListener(flup);
        f.add(text);
        f.add(ok);
        f.add(fundal);
        f.setLayout(null);
        f.setVisible(true);
    }
    public void setCnp(String x)
    {
        cnp.setText(x);
    }
    public static void setFereastraAux(Date info,JFrame wi,String cnp,int dif)
    {
        JFrame windowAux=new JFrame("Retrunare cu succes");
        windowAux.setSize(400,200);
        windowAux.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        windowAux.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        windowAux.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        windowAux.setLocation(dim.width/2-windowAux.getSize().width/2, dim.height/2-windowAux.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,170);
        fundal.setBackground(Color.white);     // background alb
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+"auxImprumut.png").getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextPane x=new JTextPane();
        x.setBounds(100,40,250,100);
        x.setForeground(Color.white);
        x.setOpaque(false);
        x.setEditable(false);
        if(dif>0)
            x.setText("Returnare cu succes!\nIntarziere cu "+dif+" zile!");
        else
            x.setText("Returnare cu succes!\nFara intarziere!");
        x.setFont(new Font("Arial",Font.BOLD,20));
        JButton ok=new JButton();
        ok.setBounds(165,100,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,100,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,100,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wi!=null)
                    wi.dispose();
                FereastraAdministrator a=new FereastraAdministrator(info);
                FereastraReturnareImprumut b=new FereastraReturnareImprumut(info,a.getWindow());
                b.setCnp(cnp);
                windowAux.dispose();
            }
        });
        ok.addMouseListener(flup);
        windowAux.add(ok);
        windowAux.add(x);
        windowAux.add(fundal);
        windowAux.setLayout(null);
        windowAux.setVisible(true);
    }
}
