package p1;

import p3.Date;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class FereastraInformatiiCarte{
    private JFrame window;
    private JLabel logo,nume,linie;
    private JButton inapoi;
    private JTextPane text;
    private JTextField bara;
    private JButton verifica;
    private JTextPane informatiiCarte,f;
    private JTextPane descriere;
    private JScrollPane x;
    public FereastraInformatiiCarte(Date informatii, String s,JFrame x) {
        window=new JFrame();
        caracteristiciFereasra();
        caracteristiciLogo();
        caracteristiciNume(s);
        caracteristiciLinie();
        butonInapoi(informatii,x);
        caracteristiciText();
        caracteristiciBara();
        caracteristicibutonCautare(informatii);
        verifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caracteristiciTextInformatii(generareDateCarte());
            }
        });
        window.setLayout(null);
        window.setVisible(true);
    }
    private void caracteristiciFereasra() {
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
     * Functia de caracteristici a logo-ului care este imaginea mare in stanga sus
     */
    private void caracteristiciLogo() {
        logo=new JLabel();
        logo.setBounds(70,0,90,90); // dimensiunea
        logo.setBackground(Color.white);     // background alb
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logoStanga.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT))); //setarea imagini
        logo.setOpaque(true); // sa fie vizibil
        window.add(logo);
    }

    private void caracteristiciNume(String s){
        nume=new JLabel();
        nume.setBounds(230,0,400,100); // marimea si pozitia
        nume.setText(s);                    // numele (textul setat)
        nume.setFont(new Font("User", Font.BOLD, 30));  // fontul textului
        nume.setForeground(new Color(99,12,120));     // culoarea textului
        nume.setBackground(Color.WHITE);         // backround ul alb
        nume.setOpaque(true);// sa fie vizibil
        window.add(nume);
    }

    /**
     * Functia de caracteristici a liniei care este sub logo si numele de utilizator
     */
    private void caracteristiciLinie() {
        linie=new JLabel();
        linie.setBounds(0 ,100,800,5);  // marimea si pozitia
        linie.setBackground(new Color(99,12,120)); // culoarea la fundal ala acesteia
        linie.setOpaque(true);        // sa fie vizibila
        window.add(linie);
    }



    private void caracteristiciText()
    {
        text=new JTextPane();
        text.setEditable(false);
        text.setBackground(Color.white);
        text.setBounds(10,120,250,20);
        text.setForeground(new Color(99,12,120));
        text.setFont(new Font("cnp",Font.BOLD,11));
        text.setText("* Introduceti id-ul sau numele cartii intreg *");
        window.add(text);
    }

    private void caracteristiciBara()
    {
        bara=new JTextField();
        bara.setBounds(30,150,200,30);
        bara.setFont(new Font("bara",Font.BOLD,14));
        bara.setBorder(new LineBorder(Color.black, 2));
        window.add(bara);
    }
    protected void setareCuloareActiune(JButton x)
    {
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                x.setBackground(new Color(155, 201, 255));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                x.setBackground(new Color(255,240,0));
            }
        };
        x.addMouseListener(flup);
    }
    private void caracteristicibutonCautare(Date a)
    {
        verifica=new JButton("Verifica");
        setareCuloareActiune(verifica);
        verifica.setBounds(245,153,80,25);
        verifica.setBackground(new Color(255,240,0));   // fundalul
        verifica.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        verifica.setFont(new Font(Font.DIALOG,  Font.BOLD, 12)); // fontul textului
        verifica.setForeground(Color.black);
        verifica.setBorder(new LineBorder(Color.black, 2));
        f=new JTextPane();
        f.setEditable(false);
        f.setBackground(Color.white);
        f.setForeground(new Color(99,12,120));
        f.setBounds(420,170,100,30);
        f.setFont(new Font("cnp",Font.BOLD,16));
        window.add(f);
        window.add(verifica);
    }
    private String[] generareDateCarte()
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[] s=new String[12];
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String text=bara.getText();
            String selectSql="";
            if(isNumeric(text))
                 selectSql= "select * from Carti where CarteId='"+text+"'";
            else
            {
                String[] nou=text.split(" ");
                String patern="";
                for(int i=0;i<nou.length-1;i++)
                    patern+="Nume like '%"+nou[i]+"%' and ";
                patern+="Nume like '%"+nou[nou.length-1]+"%'";
                selectSql="select * from Carti where "+patern;
            }
            resultSet = statement.executeQuery(selectSql);
            while(resultSet.next())
            {
                 //System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(8));
                s[0]= resultSet.getString(2);
                s[1]=resultSet.getString(3);
                s[2]=resultSet.getString(4);
                s[3]=resultSet.getString(5);
                s[4]=resultSet.getString(6);
                s[5]=resultSet.getString(7);
                s[6]=resultSet.getString(8);
                s[7]=resultSet.getString(10);
                s[8]=resultSet.getString(11);
                s[9]=resultSet.getString(12);
                s[10]=resultSet.getString(14);
                s[11]=resultSet.getString(15);
                break;
            }
        }
        catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return s;
    }
    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    private void caracteristiciTextInformatii(String[] s)
    {
        informatiiCarte=new JTextPane();
        informatiiCarte.setEditable(false);
        informatiiCarte.setBackground(Color.white);
        informatiiCarte.setForeground(new Color(99,12,120));
        informatiiCarte.setBounds(10,190,400,300);
        informatiiCarte.setFont(new Font("cnp",Font.BOLD,16));
        if(s[0]!=null) {
            String d, sp;
            if (s[11] == null)
                sp = "NU";
            else
                sp = "DA";
            if (s[10] != null)
                d = s[10];
            else
                d = "Disponibil";
            String t = "  Infromatii despre carte :\n\n" +
                    "    Nume : " + s[0] + "\n" +
                    "    Autor : " + s[1] + "\n" +
                    "    Disponibilitate : " + s[2] + "\n" +
                    "    Puncte : " + s[3] + "\n" +
                    "    Categorie : " + s[4] + "\n" +
                    "    Tema : " + s[5] + "\n" +
                    "    Numar pagini : " + s[7] + "\n" +
                    "    Pozitia in biblioteca: " + "\n" +
                    "       Raft : " + s[8] + "\n" +
                    "       Sertar : " + s[9] + "\n" +
                    "    Disponibilitate : " + d + "\n" +
                    "    Categoria Speciala : " + sp + "\n";
            if (sp.equals("DA"))
                t += " Puncte Bonus : " + s[11];
            informatiiCarte.setText(t);
            descriere(s[6]);
        }
        else {
            informatiiCarte.setText(" Nu s-a gasit nici-o potrivire\n Introduceti datele corecte !");
            f.setText("");
            if(descriere!=null) {
                descriere.setText("");
            }
        }
            window.add(informatiiCarte);
    }
    private void descriere(String s)
    {
        f.setText("Descriere :");
        descriere=new JTextPane();
        descriere.setBackground(Color.white);
        descriere.setFont(new Font("User", Font.BOLD, 14));
        descriere.setBounds(10,190,300,800);
        descriere.setText(s);
        descriere.setEditable(false);
        x=new JScrollPane(descriere);
        x.setBorder(new BevelBorder(-1));
        x.setBounds(420,220,320,280);
        x.setBackground(Color.white);
        window.add(x);
    }

    protected void butonInapoi(Date a,JFrame x)
    {
        inapoi=new JButton();
        inapoi.setText("Inapoi");
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   10,520,90,25);
        inapoi.setBackground(new Color(255,240,0));   // fundalul
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 12)); // fontul textului
        inapoi.setForeground(Color.black);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.black, 2));
        window.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x.setVisible(true);
                window.dispose();
            }
        });
    }
}
