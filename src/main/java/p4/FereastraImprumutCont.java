package p4;

import p1.FirstWindow;
import p2.UtilizatorNormal;
import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class FereastraImprumutCont {
    private JFrame window;
    private JPanel jp;
    private JLabel fundal;
    private JTextPane cnp,serie,nume,email;
    private JButton inapoi,next;
    private JTextField cnpT,serieT,numeT,emailT;
    public FereastraImprumutCont(Date info,String a)
    {
        setWindow();
        setFundal();
        setText();
        setFields(a);
        inapoi=Butoane.butonInapoi(500);
        jp.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdministrator x=new FereastraAdministrator(info);
                FereastraImprumut x2=new FereastraImprumut(info,x.getWindow());
                x2.setCnp(a);
                window.dispose();
            }
        });
        next=Butoane.setNext(725,500);
        jp.add(next);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actiuneButonNext(info);
            }
        });
        jp.add(fundal);
        window.add(jp);
        window.setLayout(null);
        window.setVisible(true);
    }
    private void setWindow()
    {
        window=new JFrame("Date utilizator");
        jp=new JPanel();
        window.setSize(800,600);
        jp.setBounds(0,0,785,570);// dimensiunea
        jp.setLayout(null);
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }
    private void setFundal()
    {
        fundal = new JLabel();
        fundal.setBounds(0, 0, 785, 570);
        fundal.setBackground(Color.white);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\imprumutCont.png").getImage().getScaledInstance(785, 570, Image.SCALE_DEFAULT)));
        fundal.setOpaque(true);
    }
    private JTextPane setTextPane(int x,int y,String a,int z,int c)
    {
        JTextPane d=new JTextPane();
        d.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, c));
        d.setBounds(x,y,z,30);
        d.setText(a);
        d.setForeground(Color.white);
        d.setOpaque(false);
        d.setEditable(false);
        return d;
    }
    private void setText()
    {
        cnp=setTextPane(100,120,"CNP",100,20);
        jp.add(cnp);
        serie=setTextPane(100,180,"Serie",100,20);
        jp.add(serie);
        nume=setTextPane(100,240,"Nume",100,20);
        jp.add(nume);
        email=setTextPane(100,300,"Email",100,20);
        jp.add(email);
    }
    private JTextField setTextField(int x,int y)
    {
        JTextField d=new JTextField();
        d.setBounds(x,y,200,30);
        d.setFont(new Font("f",Font.BOLD,17));
        d.setOpaque(false);
        d.setForeground(Color.white);
        d.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        return d;
    }
    private void setFields(String a)
    {
        cnpT=setTextField(250,120);
        cnpT.setDocument(new LimitaCaractere(13));
        cnpT.setText(a);
        cnpT.setEditable(false);
        jp.add(cnpT);
        serieT=setTextField(250,180);
        jp.add(serieT);
        numeT=setTextField(250,240);
        jp.add(numeT);
        emailT=setTextField(250,300);
        jp.add(emailT);
    }
    private boolean isValidEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private void actiuneButonNext(Date info)
    {
        if(numeT.getText().length()==0 || serieT.getText().length()==0 || email.getText().length()==0)
            Butoane.setFereastraAux("Completati toate campurile!","auxImprumut.png");
        else {
            String first = numeT.getText().charAt(0) + "";
            if (!first.equals(first.toUpperCase()) || numeT.getText().startsWith(" "))
            {
                Butoane.setFereastraAux("Numele format incorect!", "auxImprumut.png");
            }
            else
            {
                if (!isValidEmailAddress(emailT.getText()))
                    Butoane.setFereastraAux("Email format incorect!", "auxImprumut.png");
                else
                {
                    setFereastraAux(info);
                }
            }
        }
    }
    private void setFereastraAux(Date info)
    {
        JFrame windowAux=new JFrame("Avertisment");
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+"confirmImprumutare.png").getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextField x=new JTextField();
        x.setText("Doriti sa continuati?");
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
                setFereastraAux2(info);
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

    private void setFereastraAux2(Date info)
    {
        JFrame windowAux=new JFrame("Tip imrpumut");
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+"confirmImprumutare.png").getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextPane x=new JTextPane();
        x.setText("Doriti imprumut normal sau abonare?");
        x.setOpaque(false);
        x.setForeground(Color.white);
        x.setEditable(false);
        x.setFont(new Font("Arial",Font.BOLD,20));
        x.setBounds(20,30,380,30);
        JButton inapoi=new JButton();
        inapoi.setText("Normal");
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   50,100,90,25);
        inapoi.setBackground(new Color(68, 140, 203));   // fundalul
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 15)); // fontul textului
        inapoi.setForeground(Color.white);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.white, 2));
        windowAux.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdministrator x=new FereastraAdministrator(info);
                FereastraImprumutId x2=new FereastraImprumutId(info,cnpT.getText(),numeT.getText(),serieT.getText(),emailT.getText(),x.getWindow());
                window.dispose();
                windowAux.dispose();
            }
        });
        JButton abonat=new JButton("Abonat");
        setareCuloareActiune(abonat);
        abonat.setBounds(   240,100,90,25);
        abonat.setBackground(new Color(68, 140, 203));   // fundalul
        abonat.setOpaque(false);
        abonat.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        abonat.setFont(new Font("Buton1",  Font.BOLD, 15)); // fontul textului
        abonat.setForeground(Color.white);     // culoarea textului
        abonat.setBorder(new LineBorder(Color.white, 2));
        windowAux.add(abonat);
        abonat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                info.adaugareUtilizator(new UtilizatorNormal(cnpT.getText(),numeT.getText(),emailT.getText(),serieT.getText(),dtf.format(now),0,0,false,null,new ArrayList<>()),cnpT.getText());
                adaugareBazaDateUtilizator();
                FereastraAdministrator x=new FereastraAdministrator(info);
                FereastraImprumutId x2=new FereastraImprumutId(info,cnpT.getText(),x.getWindow());
                window.dispose();
                windowAux.dispose();
            }
        });
        windowAux.add(x);
        windowAux.add(fundal);
        windowAux.setLayout(null);
        windowAux.setVisible(true);
    }

    private void adaugareBazaDateUtilizator()
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();) {
            String adaugareSql="INSERT INTO  Utilizatori(CNP,Nume,Email,Serie,DataInregistrare,PunctePlus,PuncteMinus)"+
                    "VALUES"+"("+"'"+cnpT.getText()+"'"+","+"'"+numeT.getText()+"'"+","+"'"+emailT.getText()+"'"+","+"'"+serieT.getText()+"'"+","+"GETDATE()"+","+"'"+0+"'"+","+"'"+0+"'"+")";
            statement.executeUpdate(adaugareSql);
        }
        catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
    }
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
}
