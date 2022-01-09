package p4;

import p2.Carte;
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
import java.io.File;
import java.sql.*;
import java.util.Map;

public class EliminareCarte {
    private JFrame window;
    private JPanel jp;
    private JLabel fundal;
    private JButton next;
    private JPasswordField parola;
    private JTextPane text,parolaText;

    public EliminareCarte(Date info,String id,JFrame x)
    {
        setWindow();
        setText(info,id);
        setParola();
        next=Butoane.setNext(210,280);
        jp.add(next);
        setFundal();
        window.add(jp);
        jp.add(fundal);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parola.getText().length()>0)
                {
                    if(info.getListaAdministatori().get(info.getEmail()).getParola().equals(parola.getText()))
                    {
                        stergereCarte(info,Integer.parseInt(id));
                        window.dispose();
                        setFereastraAux(info,x);
                    }
                    else
                        Butoane.setFereastraAux("Parola incorecta!", "auxImprumut.png");
                }
                else
                    Butoane.setFereastraAux("Introduceti parola!", "auxImprumut.png");
            }
        });
        window.setLayout(null);
        window.setVisible(true);
    }
    private void setWindow()
    {
        window=new JFrame("Confirmare Eliminare Carte");
        jp=new JPanel();
        window.setSize(500,400);
        jp.setBounds(0,0,485,370);// dimensiunea
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
        fundal=new JLabel();
        fundal.setBounds(0,0,485,370);
        fundal.setBackground(Color.white);     // background alb
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\confirmareStergereCarte.png").getImage().getScaledInstance(485, 370, 4))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
    }
    private void setText(Date info,String id)
    {
        Carte carte=info.getListaCarti().get(Integer.parseInt(id));
        text=new JTextPane();
        text.setOpaque(false);
        text.setForeground(Color.white);
        text.setEditable(false);
        text.setFont(new Font("Arial",Font.BOLD,16));
        text.setBounds(50,30,350,100);
        text.setBorder(null);
        text.setText("Doriti sa stergeti cartea? \n\n"+
                "Id: "+id+"\n"+
                "Nume: "+carte.getNumeCarte()+"\n"+
                "Autor: "+carte.getAutorCarte()+"\n");
        jp.add(text);
    }
    private void setParola()
    {
        parolaText=Butoane.textPane(50,160,300,30,"Parola Admin");
        jp.add(parolaText);
        parola=Butoane.passwordField(50,200,200,30);
        jp.add(parola);
    }
    private void stergereCarte(Date info,int id)
    {
        String cnp="";
        for(Map.Entry<Pereche, Imprumuturi> x:info.getListaImprumuturi().entrySet())
        {
            if(x.getKey().getId()==id)
            {
                cnp=x.getKey().getCnp();
                break;
            }
        }
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String deleteSql = "DELETE FROM Imprumuturi where IdCarte="+id;   // selectarea Istoricului
            String deleteSqlCarti="DELETE FROM Carti where CarteId="+id;
            statement.executeUpdate(deleteSql);
            statement.executeUpdate(deleteSqlCarti);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
        if(cnp.length()>0)
        {
            info.getListaImprumuturi().remove(new Pereche(cnp,id));
            if(info.getListaIntarzieri().containsKey(new Pereche(cnp,id)))
            {
                info.getListaIntarzieri().remove(new Pereche(cnp,id));
            }
            if(info.getListaUtimeleImprumuturiIntarizate().containsKey(cnp) && info.getListaUtimeleImprumuturiIntarizate().get(cnp).getId()==id)
            {
                info.getListaUtimeleImprumuturiIntarizate().remove(cnp);
            }
        }
        if(info.getListaCarti().get(id) instanceof CarteSpeciala)
        {
            File myObj = new File("ImaginiCarti\\\\"+id+".jpg");
            myObj.delete();
            info.getListaCarti().remove(id);
        }
        else
        {
            info.getListaCarti().remove(id);
        }
    }
    public static void setFereastraAux(Date info,JFrame fe)
    {
        JFrame windowAux=new JFrame("Mesaj Confirmare");
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+"confirmAddC.png").getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextPane x=new JTextPane();
        x.setText("Cartea a fost stearsa cu succes!");
        x.setOpaque(false);
        x.setForeground(Color.white);
        x.setEditable(false);
        x.setFont(new Font("Arial",Font.BOLD,17));
        x.setBounds(50,30,350,50);
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

                fe.dispose();
                FereastraAdministrator n=new FereastraAdministrator(info);
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
