package p4;

import p2.Administrator;
import p3.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class FereastraAdaugareContNou {
    private JFrame window;
    private JButton next, back;
    private JTextPane numeT, emailT, parolaT, cnpT, serieT, parolaT2, parolaV;
    private JTextField nume, email, cnp, serie;
    private JPasswordField p, p2, p1;
    private JLabel fundal;

    public FereastraAdaugareContNou(Date info) {
        setWindow();
        setFundal();
        addText();
        addFields();
        next = Butoane.setNext(720, 500);
        window.add(next);
        back = Butoane.butonInapoi(500);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdministrator x = new FereastraAdministrator(info);
                window.dispose();
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificareCampuri(info);
            }
        });
        window.add(back);
        window.add(fundal);
        window.setLayout(null);
        window.setVisible(true);
    }

    private void setWindow() {
        window = new JFrame("Adaugare Administrator Nou");
        JFrame.setDefaultLookAndFeelDecorated(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // optiune de inchidere la x
        window.setSize(800, 600);                       // dimensiunea
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000, 6000, Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2); // afisarea in centru ecranului
    }

    private void setFundal() {
        fundal = new JLabel();
        fundal.setBounds(0, 0, 785, 570);
        fundal.setBackground(Color.white);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adaugareContAdministratorF.png").getImage().getScaledInstance(785, 570, Image.SCALE_DEFAULT)));
        fundal.setOpaque(true);
    }

    private void addText() {
        parolaV = Butoane.textPane(100, 150, 300, 30, "Parola cont curent");
        window.add(parolaV);
        numeT = Butoane.textPane(100, 200, 100, 30, "Nume");
        window.add(numeT);
        emailT = Butoane.textPane(100, 250, 100, 30, "Email");
        window.add(emailT);
        cnpT = Butoane.textPane(100, 300, 100, 30, "CNP");
        window.add(cnpT);
        serieT = Butoane.textPane(100, 350, 100, 30, "Serie");
        window.add(serieT);
        parolaT = Butoane.textPane(100, 400, 100, 30, "Parola");
        window.add(parolaT);
        parolaT2 = Butoane.textPane(100, 450, 300, 30, "Reintroduce Parola");
        window.add(parolaT2);
    }

    private void addFields() {
        p = Butoane.passwordField(400, 150, 300, 30);
        window.add(p);
        nume = Butoane.textField(400, 200, 300, 30, 0);
        window.add(nume);
        email = Butoane.textField(400, 250, 300, 30, 0);
        window.add(email);
        cnp = Butoane.textField(400, 300, 300, 30, 13);
        window.add(cnp);
        serie = Butoane.textField(400, 350, 300, 30, 0);
        window.add(serie);
        p1 = Butoane.passwordField(400, 400, 300, 30);
        window.add(p1);
        p2 = Butoane.passwordField(400, 450, 300, 30);
        window.add(p2);
    }

    private void verificareCampuri(Date info) {
        if (p.getText().length() > 0 && nume.getText().length() > 0 && email.getText().length() > 0 && cnp.getText().length() > 0 && serie.getText().length() > 0 && p1.getText().length() > 0 && p2.getText().length() > 0)
        {
            if (p.getText().equals(info.getListaAdministatori().get(info.getEmail()).getParola()))
            {
               if(isValidEmailAddress(email.getText()))
               {
                   if(!info.getListaAdministatori().containsKey(email.getText()))
                   {
                       if(p1.getText().length()>5)
                       {
                           if(p1.getText().equals(p2.getText()))
                           {
                               if(Butoane.isDigit(cnp.getText()))
                               {
                                   if(cnp.getText().length()==13)
                                   {
                                       adaugareCont(info);
                                       fereastraAux(info);
                                   }
                                   else
                                       Butoane.setFereastraAuxRestrictie("CNP prea scurt!", "avertismentFundal.png");
                               }
                               else
                                   Butoane.setFereastraAuxRestrictie("CNP format incorect!", "avertismentFundal.png");
                           }
                           else
                           {
                               Butoane.setFereastraAuxRestrictie("Parolele nu coincid!", "avertismentFundal.png");
                           }
                       }
                       else
                       {
                           Butoane.setFereastraAuxRestrictie("Introduceti o parola mai lunga!", "avertismentFundal.png");
                       }
                   }
                   else
                   {
                       Butoane.setFereastraAuxRestrictie("Aceasta adresa de email este deja folosita!", "avertismentFundal.png");
                   }
               }
               else
               {
                   Butoane.setFereastraAuxRestrictie("Email format incorect!", "avertismentFundal.png");
               }
            } else
                Butoane.setFereastraAuxRestrictie("Parola nu corespunde cu parola contului actual!", "avertismentFundal.png");
        }
        else
            Butoane.setFereastraAuxRestrictie("Completati toate campurile!", "avertismentFundal.png");
    }
    private boolean isValidEmailAddress(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private void adaugareCont(Date info)
    {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String updateSql="INSERT INTO Administratori (Nume,Email,Parola,CnpAdministrator,SerieAdministrator) "+
                    "VALUES('"+nume.getText()+"'"+","+"'"+email.getText()+"'"+","+"'"+p1.getText()+"'"+","+"'"+cnp.getText()+"'"+","+"'"+serie.getText()+"'"+")";
            statement.executeUpdate(updateSql);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        info.getListaAdministatori().put(email.getText(),new Administrator(cnp.getText(),nume.getText(),email.getText(),serie.getText(),p1.getText()));
    }
    private void fereastraAux(Date info)
    {
        JFrame windowAux=new JFrame("Mesaj");
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
        x.setText("Creat cu succes!");
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
                FereastraAdministrator x=new FereastraAdministrator(info);
                windowAux.dispose();
                window.dispose();
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
