package p1;

import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa prezinta cautarea cu descriere a unei carti
 * <h1>Descrierea va avea maxim 30 caractere in care poti specifica descrierea carti</h1>
 */
public class FereastraCautareDescriere extends AspectFereastra{
    private JTextField baraCautare;
    private JButton butonCautare,informatiiCarte;
    private JTextPane text,textInfo;
    private JScrollPane f;
    private JTable tabel;
    private JComboBox categori;
    public FereastraCautareDescriere(Date carti,String s)
    {
        super(carti,s);
        caracteristiciText();
        caracteristiciBara();
        categoriSetare();
        caracteristicibutonCautare(carti);
        informatiiCarte(carti);
    }

    /**
     * Metoda pentru caracterisici text
     */
    private void caracteristiciText()
    {
        text=new JTextPane();
        text.setEditable(false);
        text.setBackground(Color.white);
        text.setBounds(10,130,230,40);
        text.setForeground(new Color(99,12,120));
        text.setFont(new Font("cnp",Font.BOLD,12));
        text.setText("* Introduceti cuvinte cheie despre \ncarte (Ex:natura, magie...)");
        window.add(text);
    }
    private void caracteristiciBara()
    {
        baraCautare=new JTextField();
        baraCautare.setBounds(250,138,300,30);
        baraCautare.setFont(new Font("cnp",Font.BOLD,12));
        baraCautare.setBorder(new LineBorder(Color.black, 2));
        window.add(baraCautare);
    }
    private void caracteristicibutonCautare(Date a)
    {
        butonCautare=new JButton("Cauta");
        setareCuloareActiune(butonCautare);
        butonCautare.setBounds(600,138,80,25);
        butonCautare.setBackground(new Color(255,240,0));   // fundalul
        butonCautare.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        butonCautare.setFont(new Font(Font.DIALOG,  Font.BOLD, 12)); // fontul textului
        butonCautare.setForeground(Color.black);
        butonCautare.setBorder(new LineBorder(Color.black, 2));
        butonCautare.addActionListener(e -> {
            creareTabel(conectareBaza(baraCautare.getText()));
           // SwingUtilities.updateComponentTreeUI(window);
        });
        window.add(butonCautare);
    }
    private void categoriSetare()
    {
        String[] lista={"Toate","Roman","Poezii","Nuvela","Matematica","Fizica","Romana","Geografie","Chimie","Informatica","Programare","Povestiri","Stiinta","Filozofie"}; // taote categoriile de carti
        categori=new JComboBox(lista); // decalrare si setare cu categorii
        categori.setMaximumRowCount(3);
        categori.setBounds(460,170,90,20); // marimea si pozitia
        categori.setBackground(Color.WHITE);    // fontul alb
        categori.setForeground(new Color(99,12,120)); // fontul textului
        categori.getEditor().getEditorComponent().setBackground(Color.WHITE); // fontul in timpul selectarii
        window.add(categori);  // adaugare
    }
    private  String[][] conectareBaza(String txt)
    {
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        String[][] d=null;
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String[] nou=txt.split(" ");
            String patern="";
            if(!categori.getSelectedItem().toString().equals("Toate"))
                patern=" (Categorie='"+categori.getSelectedItem().toString()+"') and (";
            for(int i=0;i<nou.length-1;i++)
                if(nou[i].length()>2)
                    patern+="CuvinteCheie like '%"+nou[i]+"%' or ";
            patern+="CuvinteCheie like '%"+nou[nou.length-1]+"%'";
            if(!categori.getSelectedItem().toString().equals("Toate"))
                patern+=")";
            String selectSql = "select * from Carti where "+patern+" order by Disponibilitate Asc";
            resultSet = statement.executeQuery(selectSql);
            List<String[]> infromatii=new ArrayList<>();
            //int i=0;
            while (resultSet.next()) {
                String[] k=new String[8];
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
                k[6]=resultSet.getString(5);
                if(resultSet.getString(15)!=null)
                    k[7]=resultSet.getString(15);
                else
                    k[7]="0";
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
            creareTabel(d);
        } catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return d;
    }
    private void creareTabel(String[][] date)
    {
        String coloane[] = {"Id", "Nume", "Autor", "Sertar", "Raft", "Disponibil","Puncte","Puncte Bonus"};
        tabel = new JTable(date, coloane);
        tabel.setBounds(150, 150, 50, 300);
        tabel.setFont(new Font("tabel", Font.BOLD, 13));
        tabel.getColumnModel().getColumn(1).setPreferredWidth(350);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabel.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(5).setPreferredWidth(180);
        tabel.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(7).setPreferredWidth(205);
        tabel.setRowHeight(40);
        tabel.getTableHeader().setForeground(new Color(99,12,120));
        tabel.getTableHeader().setBackground(Color.white);
        tabel.getTableHeader().setFont(new Font("tabel", Font.BOLD, 15));
        tabel.setEnabled(false);
        tabel.getTableHeader().setForeground(Color.white);
        tabel.getTableHeader().setBackground(new Color(99, 12, 120));
        tabel.getTableHeader().setBorder(new LineBorder(new Color(99, 12, 120),2));
        tabel.setGridColor(new Color(134, 15, 129));
        tabel.setBackground(new Color(134, 15, 129));
        tabel.setForeground(Color.white);
        tabel.setSelectionBackground(Color.white);
        tabel.setBorder(new LineBorder(new Color(134, 15, 129),2));
        f=new JScrollPane(tabel);
        f.setBounds(30, 200, 730, 300);
        f.setBackground(Color.WHITE);
        window.add(f);
    }

    private void informatiiCarte(Date a)
    {
        textInfo=new JTextPane();
        textInfo.setText("Pentru mai multe informatii despre o anumita carte apasa aici");
        textInfo.setBounds(300,530,350,30);
        textInfo.setBackground(Color.white);
        textInfo.setForeground(new Color(99,12,120));
        textInfo.setEditable(false);
        window.add(textInfo);
        informatiiCarte=new JButton("Informatii");
        setareCuloareActiune(informatiiCarte);
        informatiiCarte.setBounds(670,533,100,20);
        informatiiCarte.setBackground(new Color(255,240,0));   // fundalul
        informatiiCarte.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        informatiiCarte.setFont(new Font("Butoninfo",  Font.BOLD, 12)); // fontul textului
        informatiiCarte.setForeground(Color.BLACK);
        informatiiCarte.setBorder(new LineBorder(Color.black, 2));
        window.add(informatiiCarte);
        informatiiCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraInformatiiCarte x=new FereastraInformatiiCarte(a,"Informatii despre carti",window);
                window.setVisible(false);
            }
        });
    }

    /**
     * Functia de caracteristici fereastra Utilizator
     */
    /*
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
*/
    /**
     * Functia de caracteristici a logo-ului care este imaginea mare in stanga sus
     */
    /*
    private void caracteristiciLogo() {
        logo=new JLabel();
        logo.setBounds(70,0,90,90); // dimensiunea
        logo.setBackground(Color.white);     // background alb
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logo2.2.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT))); //setarea imagini
        logo.setOpaque(true); // sa fie vizibil
        window.add(logo);
    }

    private void caracteristiciNume(){
        nume=new JLabel();
        nume.setBounds(230,0,400,100); // marimea si pozitia
        nume.setText("Cautare dupa descriere");                    // numele (textul setat)
        nume.setFont(new Font("User", Font.BOLD, 30));  // fontul textului
        nume.setForeground(new Color(102,0,51));     // culoarea textului
        nume.setBackground(Color.WHITE);         // backround ul alb
        nume.setOpaque(true);// sa fie vizibil
        window.add(nume);
    }*/

    /**
     * Functia de caracteristici a liniei care este sub logo si numele de utilizator
     */
    /*
    private void caracteristiciLinie() {
        linie=new JLabel();
        linie.setBounds(0 ,100,800,5);  // marimea si pozitia
        linie.setBackground(new Color(102,0,51)); // culoarea la fundal ala acesteia
        linie.setOpaque(true);        // sa fie vizibila
        window.add(linie);
    }

    private void butonInapoi(Date a)
    {
        inapoi=new JButton();
        inapoi.setText("Inapoi");
        inapoi.setBounds(   10,520,90,25);
        inapoi.setBackground(new Color(240,240,0));   // fundalul
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 12)); // fontul textului
        inapoi.setForeground(new Color(102,0,51));     // culoarea textului
        window.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserWindow u=new UserWindow(a);
                window.dispose();
            }
        });
    }*/
}
