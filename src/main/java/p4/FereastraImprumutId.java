package p4;

import p2.CarteSpeciala;
import p2.Imprumuturi;
import p2.UtilizatorNormal;
import p3.Date;
import p3.Pereche;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FereastraImprumutId {
    JFrame window;
    JLabel fundal;
    JPanel jp;
    JTextField id;
    JButton next;
    JTextPane idC;
    public FereastraImprumutId(Date info,String a,JFrame x)
    {
        setWindow(a,info);
        setId();
        setButoane();
        setText();
        setFundal();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Butoane.isAlpha(id.getText()))
                    Butoane.setFereastraAux("ID format incorect!","auxImprumut.png");
                else {
                    if (id.getText().length() > 0) {
                        if (info.getListaCarti().containsKey(Integer.parseInt(id.getText()))) {
                            if(info.getListaCarti().get(Integer.parseInt(id.getText())).isDisponibilitate())
                            {
                                if(info.getListaCarti().get(Integer.parseInt(id.getText())) instanceof CarteSpeciala)
                                {
                                    if(((CarteSpeciala)info.getListaCarti().get(Integer.parseInt(id.getText()))).getRaritate()>info.getListaUtilizatori().get(a).getPunctePlus())
                                        Butoane.setFereastraAuxRestrictie("Restrictionat, insuficiente puncte in cont! \nPuncte: "+info.getListaUtilizatori().get(a).getPunctePlus()+"\nCarte raritate: "+((CarteSpeciala)info.getListaCarti().get(Integer.parseInt(id.getText()))).getRaritate(), "auxImprumut.png");
                                    else {
                                        if(info.getListaNumarImprumutariCont().containsKey(a) && info.getListaNumarImprumutariCont().get(a)>=calculCartiNrMaxim(info,a))
                                            Butoane.setFereastraAuxRestrictie("Numarul de imprumuturi disponibile este la maxim, returnati o carte pentru a putea imprumuta!","auxImprumut.png");
                                        else
                                            setConfirm("Doriti sa continuati?", "confirmImprumutare.png", info, Integer.parseInt(id.getText()), a, x);
                                    }
                                }
                                else {
                                    if(info.getListaNumarImprumutariCont().containsKey(a) && info.getListaNumarImprumutariCont().get(a)>=calculCartiNrMaxim(info,a))
                                        Butoane.setFereastraAuxRestrictie("Numarul de imprumuturi disponibile este la maxim, returnati o carte pentru a putea imprumuta!","auxImprumut.png");
                                    else
                                        setConfirm("Doriti sa continuati?", "confirmImprumutare.png", info, Integer.parseInt(id.getText()), a, x);
                                }
                            }
                            else
                                Butoane.setFereastraAux("Carte Indisponibila!", "auxImprumut.png");
                        } else
                            Butoane.setFereastraAux("ID invalid!", "auxImprumut.png");
                    }
                }
            }
        });
        jp.add(fundal);
        window.add(jp);
        window.setLayout(null);
        window.setVisible(true);
    }
    public FereastraImprumutId(Date info,String cnp,String nume,String serie,String email,JFrame x)
    {
        setWindow(cnp,info,nume);
        setId();
        setButoane();
        setText();
        setFundal();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Butoane.isAlpha(id.getText()))
                    Butoane.setFereastraAux("ID format incorect!","auxImprumut.png");
                else {
                    if (id.getText().length() > 0) {
                        if (info.getListaCarti().containsKey(Integer.parseInt(id.getText()))) {
                            if(info.getListaCarti().get(Integer.parseInt(id.getText())).isDisponibilitate())
                            {
                                if(info.getListaCarti().get(Integer.parseInt(id.getText())) instanceof CarteSpeciala)
                                    Butoane.setFereastraAux("Fara acces la carti speciale!", "auxImprumut.png");
                                else {
                                    if(info.getListaNumarImprumutariCont().containsKey(cnp) && info.getListaNumarImprumutariCont().get(cnp)>=calculCartiNrMaxim(info,cnp))
                                        Butoane.setFereastraAuxRestrictie("Numarul de imprumuturi disponibile este la maxim, returnati o carte pentru a putea imprumuta!","auxImprumut.png");
                                    else
                                        setConfirm2("Doriti sa continuati?", "confirmImprumutare.png", info, Integer.parseInt(id.getText()), cnp, nume, serie, email, x);
                                }
                            }
                            else
                                Butoane.setFereastraAux("Carte Indisponibila!", "auxImprumut.png");
                        } else
                            Butoane.setFereastraAux("ID invalid!", "auxImprumut.png");
                    }
                }
            }
        });
        jp.add(fundal);
        window.add(jp);
        window.setLayout(null);
        window.setVisible(true);
    }

    private int calculCartiNrMaxim(Date info,String s)
    {
        int nr=5;
        if(info.getListaUtilizatori().get(s).getPuncteMinus()<10)
            if(info.getListaUtilizatori().get(s).getPunctePlus()>0)
            {
                nr+=(info.getListaUtilizatori().get(s).getPunctePlus()/100);
            }
        return nr;
    }

    //editare carte constructor
    public FereastraImprumutId(Date info,JFrame x)
    {
        setWindow("",info);
        setId();
        setButoane();
        setText();
        setFundal2();
        jp.add(fundal);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (id.getText().length() > 0) {
                    if (Butoane.isAlpha(id.getText()))
                        Butoane.setFereastraAux("ID format incorect!", "auxImprumut.png");
                    else {
                        if (info.getListaCarti().containsKey(Integer.parseInt(id.getText()))) {
                            FereastraAdaugareCarte g = new FereastraAdaugareCarte(info, Integer.parseInt(id.getText()));
                            window.dispose();
                            x.dispose();
                        } else
                            Butoane.setFereastraAux("Carte nagasita!", "auxImprumut.png");
                    }
                }
                else
                    Butoane.setFereastraAux("Introduceti ID-ul!", "auxImprumut.png");
            }
        });
        window.add(jp);
        window.setLayout(null);
        window.setVisible(true);
    }

    //Constructor pentru eliminare Carte
    public FereastraImprumutId(Date info,JFrame x,String s)
    {
        setWindow();
        setId();
        setButoane();
        setText();
        setFundal2();
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\stergereCarteF.png").getImage().getScaledInstance(485, 270, 4))); //setarea imagini
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(id.getText().length()>0)
                {
                    if(Butoane.isDigit(id.getText()))
                    {
                        if(info.getListaCarti().containsKey(Integer.parseInt(id.getText())))
                        {
                            EliminareCarte d=new EliminareCarte(info,id.getText(),x);
                            window.dispose();
                        }
                        else
                            Butoane.setFereastraAux("Id incorect!","avertismentAddC.png");
                    }
                    else
                        Butoane.setFereastraAux("Format incorect!","avertismentAddC.png");
                }
                else
                    Butoane.setFereastraAux("Introduceti id-ul!","avertismentAddC.png");
            }
        });
        jp.add(fundal);
        window.add(jp);
        window.setLayout(null);
        window.setVisible(true);
    }


    private void setWindow(String s,Date b)
    {
        if(s.length()==13)
        window=new JFrame("Utilizator "+b.getListaUtilizatori().get(s).getNumeUtilizator());
        else
            window=new JFrame("Id Carte");
        jp=new JPanel();
        window.setSize(500,300);
        jp.setBounds(0,0,485,270);// dimensiunea
        jp.setLayout(null);
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }
    private void setWindow(String s,Date b,String nume)
    {
        if(s.length()==13)
            window=new JFrame("Utilizator "+nume);
        else
            window=new JFrame("Id Carte");
        jp=new JPanel();
        window.setSize(500,300);
        jp.setBounds(0,0,485,270);// dimensiunea
        jp.setLayout(null);
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }

    private void setWindow() // pentru eliminare carte
    {

        window=new JFrame("Sterge cartea");
        jp=new JPanel();
        window.setSize(500,300);
        jp.setBounds(0,0,485,270);// dimensiunea
        jp.setLayout(null);
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului

    }

    private void setId()
    {
        id=new JTextField();
        id.setBounds(150,100,200,30);
        id.setFont(new Font("f",Font.BOLD,20));
        id.setDocument(new LimitaCaractere(10));
        id.setOpaque(false);
        id.setForeground(Color.white);
        id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        jp.add(id);
    }
    private void setButoane()
    {
        next=Butoane.setNext(220,200);
        jp.add(next);
    }
    private void setText()
    {
        idC=new JTextPane();
        idC.setBounds(30,100,150,30);
        idC.setText("ID Carte");
        idC.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,25));
        idC.setForeground(Color.WHITE);
        idC.setOpaque(false);
        idC.setEditable(false);
        jp.add(idC);
    }
    private void setFundal()
    {
        fundal=new JLabel();
        fundal.setBounds(0,0,485,270);
        fundal.setBackground(Color.white);     // background alb
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\imprumutCarteId.png").getImage().getScaledInstance(485, 270, 4))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
    }
    private void setFundal2()
    {
        fundal=new JLabel();
        fundal.setBounds(0,0,485,270);
        fundal.setBackground(Color.white);     // background alb
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\carteIdEditare.png").getImage().getScaledInstance(485, 270, 4))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
    }
    private void setImprumutare(Date info,int n,String s)
    {
        int puncte;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        UtilizatorNormal x=info.getListaUtilizatori().get(s);
        if(info.getListaCarti().get(n) instanceof CarteSpeciala)
            puncte=x.getPunctePlus()+info.getListaCarti().get(n).getPuncte()+((CarteSpeciala) info.getListaCarti().get(n)).getPuncteBonus();
        else
            puncte=x.getPunctePlus()+info.getListaCarti().get(n).getPuncte();
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();) {
            String updateSql = "UPDATE Carti SET Disponibilitate ='Indisponibil', DataDisponibilitate="+"'"+dtf.format(now.plusDays(14))+"'"+",NumarImprumutari="+"'"+(info.getListaCarti().get(n).getNumarImprumuturi()+1)+"'"+"where CarteId="+n;
            statement.executeUpdate(updateSql);
            String adaugareSql="INSERT INTO  Imprumuturi(CnpUtilizator,IdCarte,NumeUtilizator,Email,SerieUtilizator,NumeCarte,DataImprumutare,DataReturnare,TipUtilizator)"+
                    "VALUES"+"("+"'"+s+"'"+","+"'"+n+"'"+","+"'"+x.getNumeUtilizator()+"'"+","+"'"+x.getEmailUtilizator()+"'"+","+"'"+x.getSerieUtilizator()+"'"+","+"'"+info.getListaCarti().get(n).getNumeCarte()+"'"+","+"'"+dtf.format(now)+"'"+","+"'"+dtf.format(now.plusDays(14))+"'"+","+"'"+"Abonat"+"'"+")";
            statement.executeUpdate(adaugareSql);
            String updateUtil="UPDATE Utilizatori SET PunctePlus="+"'"+puncte+"'"+"where CNP="+"'"+s+"'";
            statement.executeUpdate(updateUtil);
        }
        catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        info.getListaCarti().get(n).setDisponibilitate(false);
        int y=info.getListaCarti().get(n).getNumarImprumuturi();
        info.getListaCarti().get(n).setNumarImprumuturi(y+1);
        info.getListaCarti().get(n).setData(dtf.format(now.plusDays(14)));
        info.getListaUtilizatori().get(s).setPunctePlus(puncte);
        info.adaugareImprumut(new Pereche(s,n),new Imprumuturi(s,n,x.getNumeUtilizator(),x.getEmailUtilizator(),x.getSerieUtilizator(),info.getListaCarti().get(n).getNumeCarte(),dtf.format(now),dtf.format(now.plusDays(14))));

        if(info.getListaNumarImprumutariCont().containsKey(s))
        {
            info.getListaNumarImprumutariCont().put(s,info.getListaNumarImprumutariCont().get(s)+1);
        }
        else
        {
            info.getListaNumarImprumutariCont().put(s,1);
        }
    }

    private void setConfirm(String a,String b,Date info,int nr,String cnp,JFrame q)
    {
        JFrame windowAux=new JFrame("Confirmare");
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+b).getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextField x=new JTextField();
        x.setText(a);
        x.setOpaque(false);
        x.setForeground(Color.white);
        x.setEditable(false);
        x.setFont(new Font("Arial",Font.BOLD,20));
        x.setBounds(90,30,300,30);
        x.setBorder(null);
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
                setImprumutare(info,nr,cnp);
                //if(q!=null)
                  //  q.dispose();
               // window.setVisible(false);
               // Butoane.setFereastraAux2(info,"Imprumutare cu succes!","auxImprumut.png",window);
               // id.setText("");
                window.dispose();
                Butoane.setFereastraAux2(info,"Imprumutare cu succes!","auxImprumut.png",q,cnp);
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
    private void setConfirm2(String a,String b,Date info,int nr,String cnp,String nume,String serie,String email,JFrame q)
    {
        JFrame windowAux=new JFrame("Confirmare");
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+b).getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextField x=new JTextField();
        x.setText(a);
        x.setOpaque(false);
        x.setForeground(Color.white);
        x.setEditable(false);
        x.setFont(new Font("Arial",Font.BOLD,20));
        x.setBounds(90,30,300,30);
        x.setBorder(null);
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
                setImprumutare2(info,nr,cnp,nume,serie,email);
               /* if(q!=null)
                    q.dispose();
                window.setVisible(false);
                Butoane.setFereastraAux2(info,"Imprumutare cu succes!","auxImprumut.png",window);
                id.setText("");
                */
                window.dispose();
                Butoane.setFereastraAux2_2(info,"Imprumutare cu succes!","auxImprumut.png",q,cnp,nume,email,serie);
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
    private void setImprumutare2(Date info,int n,String s,String nume,String serie,String email)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();) {
            String updateSql = "UPDATE Carti SET Disponibilitate ='Indisponibil', DataDisponibilitate="+"'"+dtf.format(now.plusDays(14))+"'"+",NumarImprumutari="+"'"+(info.getListaCarti().get(n).getNumarImprumuturi()+1)+"'"+"where CarteId="+n;
            statement.executeUpdate(updateSql);
            String adaugareSql="INSERT INTO  Imprumuturi(CnpUtilizator,IdCarte,NumeUtilizator,Email,SerieUtilizator,NumeCarte,DataImprumutare,DataReturnare,TipUtilizator)"+
                    "VALUES"+"("+"'"+s+"'"+","+"'"+n+"'"+","+"'"+nume+"'"+","+"'"+email+"'"+","+"'"+serie+"'"+","+"'"+info.getListaCarti().get(n).getNumeCarte()+"'"+","+"'"+dtf.format(now)+"'"+","+"'"+dtf.format(now.plusDays(14))+"'"+","+"'"+"Neabonat"+"'"+")";
            statement.executeUpdate(adaugareSql);
        }
        catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        info.getListaCarti().get(n).setDisponibilitate(false);
        int y=info.getListaCarti().get(n).getNumarImprumuturi();
        info.getListaCarti().get(n).setNumarImprumuturi(y+1);
        info.getListaCarti().get(n).setData(dtf.format(now.plusDays(14)));
        info.adaugareImprumut(new Pereche(s,n),new Imprumuturi(s,n,nume,email,serie,info.getListaCarti().get(n).getNumeCarte(),dtf.format(now),dtf.format(now.plusDays(14))));
        if(info.getListaNumarImprumutariCont().containsKey(s))
        {
            info.getListaNumarImprumutariCont().put(s,info.getListaNumarImprumutariCont().get(s)+1);
        }
        else
        {
            info.getListaNumarImprumutariCont().put(s,1);
        }
    }

}
