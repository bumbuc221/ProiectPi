package p4;

import p1.AspectFereastra;
import p1.FirstWindow;
import p1.UserWindow;
import p2.Administrator;
import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FereastraAdministrator {
    private JButton inapoi;
    private JFrame window;
    private JLabel meniu,t;
    private JButton imprumut,adaugareCarte,stergere,returnare,editare,contAdmin,contNouAdmin;
    private JLabel p1,p2,p3;
    private JTable tabelCarti,tabelImprumuturi,tabelIntarzieri;
    private  JScrollPane[] scr = new JScrollPane[3]; // tabelele
    private  int []z=new int[3];

    private JLabel profilAdmin;
    private JPanel profil;
    private JTextPane numeA,emailA,cnpA,seriaA,pVeche,pNoua,pNoua2,mesajP;
    private JButton resetareParolaA,resetareConfirma;
    private JPasswordField parolaVeche,parolaNoua,parolaNoua2;
    public FereastraAdministrator(Date informatii) {
       setWindow();
       setMeniu();
       setareButoaneMeniu();
       butonInapoi(informatii);
       setTabelCarti();
       setTabelImprumuturi();
       setTabelIntarzieri();
       setNumere(informatii);
       adaugareCarte.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               FereastraAdaugareCarte x=new FereastraAdaugareCarte(informatii);
               window.dispose();
           }
       });
       imprumut.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               FereastraImprumut x=new FereastraImprumut(informatii,window);
           }
       });
       editare.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               FereastraImprumutId x=new FereastraImprumutId(informatii,window);
           }
       });
       returnare.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               FereastraReturnareImprumut x=new FereastraReturnareImprumut(informatii,window);
           }
       });
       stergere.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               FereastraImprumutId x=new FereastraImprumutId(informatii,window,"Stergere");
           }
       });
       window.add(meniu);
       infoCont(informatii);
        contAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<3;i++)
                    if(scr[i]!=null)
                        window.remove(scr[i]);
                    if(t!=null)
                        window.remove(t);
                    mesajP.setText("");
                    addP2();
                    profil.remove(resetareConfirma);
                    profil.add(resetareParolaA);
                    profil.add(profilAdmin);
                window.add(profil);
                SwingUtilities.updateComponentTreeUI(window);
            }
        });
        contNouAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdaugareContNou x=new FereastraAdaugareContNou(informatii);
                window.dispose();
            }
        });
       window.setLayout(null);
       window.setVisible(true);
    }

    private void infoCont(Date info)
    {
        profil=new JPanel();
        profil.setLayout(null);
        profil.setBounds(270,200,450,330);
        profilAdmin=new JLabel();
        profilAdmin.setBounds(0,0,450,330);
        profilAdmin.setOpaque(true);
        profilAdmin.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adminProfil2.png").getImage().getScaledInstance(450, 330, Image.SCALE_DEFAULT)));
        emailA=Butoane.textPane2(100,50,300,20,info.getEmail(),15);
        numeA=Butoane.textPane2(30,120,200,20,"Nume: "+info.getListaAdministatori().get(info.getEmail()).getNumeUtilizator(),15);
        cnpA=Butoane.textPane2(30,150,200,20,"CNP: "+info.getListaAdministatori().get(info.getEmail()).getCnpUtilizator(),15);
        seriaA=Butoane.textPane2(30,180,200,20,"Seria: "+info.getListaAdministatori().get(info.getEmail()).getSerieUtilizator(),15);
        mesajP=Butoane.textPane3(270,150,100,100,"",15,new Color(217, 4, 4));
        resetareParolaA=Butoane.normal(30,250,100,25,"Reset Parola");
        resetareConfirma=Butoane.normal(70,275,100,25,"Confirma");
        setTexteAdmin();
        profil.add(resetareParolaA);
        profil.add(emailA);
        profil.add(numeA);
        profil.add(cnpA);
        profil.add(seriaA);
        profil.add(mesajP);
        resetareParolaA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profil.remove(resetareParolaA);
                addP();
                profil.add(resetareConfirma);
                profil.add(profilAdmin);
                SwingUtilities.updateComponentTreeUI(profil);
            }
        });
        resetareConfirma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetareParolaFunctie(info,parolaVeche.getText(),info.getListaAdministatori().get(info.getEmail()).getParola(),parolaNoua.getText(),parolaNoua2.getText());
            }
        });
        profil.add(profilAdmin);
    }

    private void setTexteAdmin()
    {
        pVeche=Butoane.textPane2(30,100,200,20,"Parola veche",15);
        parolaVeche=Butoane.passwordField(30,130,200,20);
        pNoua=Butoane.textPane2(30,160,200,20,"Parola noua",15);
        parolaNoua=Butoane.passwordField(30,190,200,20);
        pNoua2=Butoane.textPane2(30,220,200,20,"Reintroduce parola",15);
        parolaNoua2=Butoane.passwordField(30,250,200,20);
    }

    private void addP()
    {
        profil.remove(profilAdmin);
        profil.remove(numeA);
        profil.remove(cnpA);
        profil.remove(seriaA);
        profil.add(pVeche);
        profil.add(pNoua);
        profil.add(pNoua2);
        profil.add(parolaNoua);
        profil.add(parolaVeche);
        profil.add(parolaNoua2);
    }
    private  void addP2()
    {
        profil.remove(profilAdmin);
        profil.remove(pVeche);
        profil.remove(pNoua);
        profil.remove(pNoua2);
        profil.remove(parolaNoua);
        profil.remove(parolaVeche);
        profil.remove(parolaNoua2);
        profil.add(numeA);
        profil.add(cnpA);
        profil.add(seriaA);
    }

    private void resetareParolaFunctie(Date info,String p,String s,String pN,String pN2)
    {
        if(p.equals(s))
        {
            if(pN.length()>5)
            {
                if(pN.equals(pN2))
                {
                    resetParolaBaza(info,info.getEmail(),pN);
                    profil.remove(resetareConfirma);
                    addP2();
                    profil.add(resetareParolaA);
                    profil.add(profilAdmin);
                    mesajP.setText("Parola a fost schimbata cu succes!");
                    SwingUtilities.updateComponentTreeUI(profil);
                }
                else
                {
                    mesajP.setText("Parola noua si parola reintrodusa nu se potrivesc!");
                }
            }
            else
                mesajP.setText("Introduceti o parola mai mare!");
        }
        else
            mesajP.setText("Parola veche nu se potriveste!");
    }

    private void resetParolaBaza(Date info,String email,String p)
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String updateSql="UPDATE Administratori SET Parola='"+p+"'"+" where Email='"+email+"'";
            statement.executeUpdate(updateSql);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        ((Administrator) info.getListaAdministatori().get(email)).setParola(p);
    }

    public JFrame getWindow()
    {
        return window;
    }
    /**
     * Functia pentru setarea ferestrei de administartor
     */
    private void setWindow()
    {
        window=new JFrame("Administrator");
        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(Color.gray));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));
        JFrame.setDefaultLookAndFeelDecorated(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // optiune de inchidere la x
        window.setSize(800,600);                       // dimensiunea
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }

    /**
     * Functia pentru setare meniu
     */
    private void setMeniu()
    {
        meniu = new JLabel();
        meniu.setBounds(0, 0, 215, 570);
        meniu.setBackground(Color.white);
        // parteaSus.setIcon(new ImageIcon("Imagini\\logo2.png"));
        meniu.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\admin1_2.png").getImage().getScaledInstance(215, 570, Image.SCALE_DEFAULT)));
        meniu.setOpaque(true);
    }
    private JButton setButoane(String b,int x,int y,String d)
    {
        JButton a=new JButton();
        JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon(d).getImage().getScaledInstance(40, 38, 4)));
        JLabel clickMe = new JLabel(b, SwingConstants.CENTER);
        a.setBounds(x,y,195,40);     // setarea pozitiei si a marimi
        a.setBackground(new Color(68, 140, 203));   // fundalul
        a.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        clickMe.setFont(new Font("c",  Font.BOLD, 17)); // fontul textului
        clickMe.setForeground(Color.white);     // culoarea textului
        a.setBorderPainted(false);
        a.setOpaque(false);
        a.setLayout(new BorderLayout());
        a.add(iconLabel, BorderLayout.WEST);
        a.add(clickMe, BorderLayout.CENTER);
        setareCuloareActiune(a);
        return a;
    }

    private void setareButoaneMeniu()
    {
        imprumut=setButoane("Imprumut",10,100,"Imagini\\\\imprumut.png");
        adaugareCarte=setButoane("Adaugare",10,150,"Imagini\\\\adaugareCarte .png");
        stergere=setButoane("Eliminare",10,200,"Imagini\\\\stergereCarte.png");
        returnare=setButoane("Returnare",10,250,"Imagini\\\\returnCarte.png");
        editare=setButoane("Editare",10,300,"Imagini\\\\editareCarte.png");
        contAdmin=setButoane("Cont",10,350,"Imagini\\\\iconAdminCont.png");
        contNouAdmin=setButoane("Cont Nou",10,400,"Imagini\\\\adaugareContNouAdmin.png");
        window.add(imprumut);
        window.add(adaugareCarte);
        window.add(stergere);
        window.add(returnare);
        window.add(editare);
        window.add(contAdmin);
        window.add(contNouAdmin);
    }

    private JTextPane setNr(int n,int x,int y)
    {
        JTextPane a=new JTextPane();
        a.setBounds(x,y,50,50);
        a.setFont(new Font("C",Font.BOLD,30));
        a.setForeground(Color.white);
        a.setOpaque(false);
        a.setText(n+"");
        a.setEditable(false);
        return a;
    }
    private void setActiuneText(JTextPane d,JLabel p1,String x,int i)
    {
        d.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(profil!=null)
                    window.remove(profil);
                if(i==1)
                {
                    if(scr[1]!=null)
                        window.remove(scr[1]);
                    if(scr[2]!=null)
                        window.remove(scr[2]);
                    if(z[0]!=0){
                        scr[0] = new JScrollPane(tabelCarti);
                        scr[0].getViewport().add(tabelCarti);
                        scr[0].setBackground(new Color(63, 239, 159));
                        scr[0].setBounds(250, 200, 500, 300);
                        scr[0].setBorder(new LineBorder(new Color(63, 239, 159), 1));
                        scr[0].getVerticalScrollBar().setBackground(new Color(63, 239, 159));
                        window.add(scr[0]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[0].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[0]=0;z[1]=1;z[2]=1;
                    }
                }
                else if(i==2)
                {
                    if (scr[0] != null)
                        window.remove(scr[0]);
                    if (scr[2] != null)
                        window.remove(scr[2]);
                    if (z[1] != 0) {
                        scr[1] = new JScrollPane(tabelImprumuturi);
                        scr[1].getViewport().add(tabelImprumuturi);
                        scr[1].setBackground(new Color(252, 157, 55));
                        scr[1].setBounds(250, 200, 500, 300);
                        scr[1].setBorder(new LineBorder(new Color(252, 157, 55), 1));
                        scr[1].getVerticalScrollBar().setBackground(new Color(252, 157, 55));
                        window.add(scr[1]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[1].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[1] = 0;
                        z[0] = 1;
                        z[2] = 1;
                    }
                }
                else
                {
                    if (scr[0] != null)
                        window.remove(scr[0]);
                    if (scr[1] != null)
                        window.remove(scr[1]);
                    if (z[2] != 0) {
                        scr[2] = new JScrollPane(tabelIntarzieri);
                        scr[2].getViewport().add(tabelIntarzieri);
                        scr[2].setBackground(new Color(170, 59, 104));
                        scr[2].setBounds(250, 200, 500, 300);
                        scr[2].setBorder(new LineBorder(new Color(170, 59, 104), 1));
                        scr[2].getVerticalScrollBar().setBackground(new Color(170, 59, 104));
                        window.add(scr[2]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[2].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[1]=1;z[0]=1;z[2]=0;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                p1.setSize(153,123);
                p1.setIcon(new ImageIcon(new ImageIcon(x).getImage().getScaledInstance(153, 123, Image.SCALE_DEFAULT)));
                d.setFont(new Font("C",Font.BOLD,32));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p1.setSize(150,120);
                p1.setIcon(new ImageIcon(new ImageIcon(x).getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
                d.setFont(new Font("C",Font.BOLD,30));
            }
        });
    }

    private void setNumere(Date info)
    {
        z[0]=-1;z[1]=-1;z[2]=-1;
        p1 = new JLabel();
        p1.setBounds(230, 30, 150, 120);
        p1.setBackground(Color.white);
        p1.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\p1Administartor.png").getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
        p1.setOpaque(true);
        JTextPane p1T=setNr(info.getListaCarti().size(),285,90);
        setActiuneText(p1T,p1,"Imagini\\\\p1Administartor.png",1);
        window.add(p1T);
        setAtiune(p1T,p1,"Imagini\\\\p1Administartor.png",1);

        p2 = new JLabel();
        p2.setBounds(420, 30, 150, 120);
        p2.setBackground(Color.white);
        p2.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\p2Administrator.png").getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
        p2.setOpaque(true);
        JTextPane p2T=setNr(info.getListaImprumuturi().size(),475,90);
        setActiuneText(p2T,p2,"Imagini\\\\p2Administrator.png",2);
        window.add(p2T);
        setAtiune(p2T,p2,"Imagini\\\\p2Administrator.png",2);

        p3 = new JLabel();
        p3.setBounds(610, 30, 150, 120);
        p3.setBackground(Color.red);
        p3.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\p3Administrator.png").getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
        p3.setOpaque(true);
        JTextPane p3T=setNr(info.getListaIntarzieri().size(),665,90);
        setActiuneText(p3T,p3,"Imagini\\\\p3Administrator.png",3);
        window.add(p3T);
        setAtiune(p3T,p3,"Imagini\\\\p3Administrator.png",3);
        window.add(p1);
        window.add(p2);
        window.add(p3);
    }

    public  void setAtiune(JTextPane d,JLabel p1,String x,int i)
    {
        p1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(profil!=null)
                    window.remove(profil);
                if(i==1)
                {
                    if(scr[1]!=null)
                        window.remove(scr[1]);
                    if(scr[2]!=null)
                        window.remove(scr[2]);
                    if(z[0]!=0){
                        scr[0] = new JScrollPane(tabelCarti);
                        scr[0].getViewport().add(tabelCarti);
                        scr[0].setBackground(new Color(63, 239, 159));
                        scr[0].setBounds(250, 200, 500, 300);
                        scr[0].setBorder(new LineBorder(new Color(63, 239, 159), 1));
                        scr[0].getVerticalScrollBar().setBackground(new Color(63, 239, 159));
                        window.add(scr[0]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[0].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[0]=0;z[1]=1;z[2]=1;
                    }
                }
                else if(i==2) {
                    if (scr[0] != null)
                        window.remove(scr[0]);
                    if (scr[2] != null)
                        window.remove(scr[2]);
                    if (z[1] != 0) {
                        scr[1] = new JScrollPane(tabelImprumuturi);
                        scr[1].getViewport().add(tabelImprumuturi);
                        scr[1].setBackground(new Color(252, 157, 55));
                        scr[1].setBounds(250, 200, 500, 300);
                        scr[1].setBorder(new LineBorder(new Color(252, 157, 55), 1));
                        scr[1].getVerticalScrollBar().setBackground(new Color(252, 157, 55));
                        window.add(scr[1]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[1].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[1]=0;z[0]=1;z[2]=1;
                    }
                }
                else
                {
                    if (scr[0] != null)
                        window.remove(scr[0]);
                    if (scr[1] != null)
                        window.remove(scr[1]);
                    if (z[2] != 0) {
                        scr[2] = new JScrollPane(tabelIntarzieri);
                        scr[2].getViewport().add(tabelIntarzieri);
                        scr[2].setBackground(new Color(170, 59, 104));
                        scr[2].setBounds(250, 200, 500, 300);
                        scr[2].setBorder(new LineBorder(new Color(170, 59, 104), 1));
                        scr[2].getVerticalScrollBar().setBackground(new Color(170, 59, 104));
                        window.add(scr[2]);
                        window.add(t);
                        SwingUtilities.updateComponentTreeUI(window);
                        scr[2].getVerticalScrollBar().setUI(setareCuloare(Color.white));
                        z[1]=1;z[0]=1;z[2]=0;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                p1.setSize(153,123);
                p1.setIcon(new ImageIcon(new ImageIcon(x).getImage().getScaledInstance(153, 123, Image.SCALE_DEFAULT)));
                d.setFont(new Font("C",Font.BOLD,32));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p1.setSize(150,120);
                p1.setIcon(new ImageIcon(new ImageIcon(x).getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
                d.setFont(new Font("C",Font.BOLD,30));
            }
        });
    }

    /**
     * Functia de conectare la baza pentru primul tabel
     */
    private  String[][] conectareBazaCarti()
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String selectSql = "select * from Carti";
            resultSet = statement.executeQuery(selectSql);
            List<String[]> infromatii=new ArrayList<>();
            //int i=0;
            while (resultSet.next()) {
                String[] k=new String[6];
                // System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(11));
                k[0]=resultSet.getString(1);
                k[1]=resultSet.getString(2);
                k[2]=resultSet.getString(3);
                k[3]=resultSet.getString(11);
                k[4]=resultSet.getString(12);
                if(resultSet.getString(4).equals("Disponibil"))
                    k[5]=resultSet.getString(4);
                else
                    k[5]=resultSet.getString(14);
               // k[5]=resultSet.getString(4);
                infromatii.add(k);
            }
            d=new String[infromatii.size()][];
            int g=0;
            for(String[] x:infromatii)
            {
                d[g]=new String[x.length];
                System.arraycopy(x, 0, d[g], 0, x.length);
                g++;
            }
        } catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return d;
    }
    /**
     * Functia pentru tabelul 1
     */
    private void setTabelCarti()
    {
        String coloane[] = {"ID", "Nume", "Autor", "Sertar", "Raft", "Disponibilitate"};
        tabelCarti=new JTable(conectareBazaCarti(),coloane);
        setareJtabel(tabelCarti,new Color(63, 239, 159),new Color(63, 239, 159));
        t=new JLabel();
        t.setBounds(240, 190, 520, 320);
        t.setBackground(Color.white);
        t.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\tabel.png").getImage().getScaledInstance(520, 320, Image.SCALE_DEFAULT)));

    }

    /**
     * Conectare baza imprumuturi
     */
    private  String[][] conectareBazaCImprumuturi()
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String selectSql = "select * from Imprumuturi";
            resultSet = statement.executeQuery(selectSql);
            List<String[]> infromatii=new ArrayList<>();
            //int i=0;
            while (resultSet.next()) {
                String[] k=new String[8];
                // System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(11));
                k[0]=resultSet.getString(1);
                k[1]=resultSet.getString(2);
                k[2]=resultSet.getString(3);
                k[3]=resultSet.getString(4);
                k[4]=resultSet.getString(5);
                k[5]=resultSet.getString(6);
                k[6]=resultSet.getString(7);
                k[7]=resultSet.getString(8);
                infromatii.add(k);
            }
            d=new String[infromatii.size()][];
            int g=0;
            for(String[] x:infromatii)
            {
                d[g]=new String[x.length];
                System.arraycopy(x, 0, d[g], 0, x.length);
                g++;
            }
        } catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return d;
    }
    private void setTabelImprumuturi()
    {
        String coloane[] = {"CNP", "Id Carte", "Nume", "Email", "Serie", "Nume Carte","DataImprumut","DataLimita"};
        tabelImprumuturi=new JTable(conectareBazaCImprumuturi(),coloane);
        setareJtabel(tabelImprumuturi,new Color(252, 157, 55),new Color(254, 131, 64));
        tabelImprumuturi.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelImprumuturi.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelImprumuturi.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelImprumuturi.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelImprumuturi.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelImprumuturi.getColumnModel().getColumn(6).setPreferredWidth(100);
        t=new JLabel();
        t.setBounds(240, 190, 520, 320);
        t.setBackground(Color.white);
        t.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\tabel.png").getImage().getScaledInstance(520, 320, Image.SCALE_DEFAULT)));

    }

    /**
     * Conectare baza Imprumuturi intarziate
     */
    private  String[][] conectareBazaCIntarzieri()
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String selectSql = "select * from Imprumuturi where GETDATE()>DataReturnare";
            resultSet = statement.executeQuery(selectSql);
            List<String[]> infromatii=new ArrayList<>();
            //int i=0;
            while (resultSet.next()) {
                String[] k=new String[8];
                // System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(11));
                k[0]=resultSet.getString(1);
                k[1]=resultSet.getString(2);
                k[2]=resultSet.getString(3);
                k[3]=resultSet.getString(4);
                k[4]=resultSet.getString(5);
                k[5]=resultSet.getString(6);
                k[6]=resultSet.getString(7);
                k[7]=resultSet.getString(8);
                infromatii.add(k);
            }
            d=new String[infromatii.size()][];
            int g=0;
            for(String[] x:infromatii)
            {
                d[g]=new String[x.length];
                System.arraycopy(x, 0, d[g], 0, x.length);
                g++;
            }
        } catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return d;
    }
    private void setTabelIntarzieri()
    {
        String coloane[] = {"CNP", "Id Carte", "Nume", "Email", "Serie", "Nume Carte","DataImprumut","DataLimita"};
        tabelIntarzieri=new JTable(conectareBazaCIntarzieri(),coloane);
        setareJtabel(tabelIntarzieri,new Color(170, 59, 104),new Color(136, 59, 119));
        tabelIntarzieri.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelIntarzieri.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabelIntarzieri.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabelIntarzieri.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelIntarzieri.getColumnModel().getColumn(5).setPreferredWidth(100);
        tabelIntarzieri.getColumnModel().getColumn(6).setPreferredWidth(100);
        t=new JLabel();
        t.setBounds(240, 190, 520, 320);
        t.setBackground(Color.white);
        t.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\tabel.png").getImage().getScaledInstance(520, 320, Image.SCALE_DEFAULT)));

    }

    private void setareJtabel(JTable tabel,Color a,Color b)
    {
        tabel.setBounds(150, 150, 50, 300);
        tabel.setFont(new Font("tabel", Font.BOLD, 15));
        tabel.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabel.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(5).setPreferredWidth(400);
        tabel.setRowHeight(40);
        tabel.getTableHeader().setForeground(Color.white);
        tabel.getTableHeader().setBackground(b);
        tabel.getTableHeader().setBorder(new LineBorder(b,2));
        tabel.setGridColor(a);
        tabel.setBackground(a);
        tabel.setForeground(Color.white);
        tabel.setSelectionBackground(Color.white);
        tabel.setBorder(new LineBorder(a,1));
        tabel.getTableHeader().setFont(new Font("tabel", Font.BOLD, 15));
    }
    /**
     * Funtia pentru setarea butonului de admin
     * @param a
     */
    private void butonInapoi(Date a)
    {
        inapoi=new JButton();
        inapoi.setText("Deconectare");
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   50,520,110,25);
        inapoi.setBackground(new Color(68, 140, 203));   // fundalul
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 15)); // fontul textului
        inapoi.setForeground(Color.white);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.white, 2));
        window.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FirstWindow u=new FirstWindow(a);
                window.dispose();
            }
        });
    }

    /**
     * Functia pentru hover
     * @param x
     */
    protected void setareCuloareActiune(JButton x)
    {
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                x.setOpaque(true);
                x.setBackground(new Color(0, 71, 133));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                x.setOpaque(false);
            }
        };
        x.addMouseListener(flup);
    }

    private ScrollBarUI setareCuloare(Color newColor)
    {
        ScrollBarUI yourUI = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = newColor;
            }
        };
        return yourUI;
    }
}
