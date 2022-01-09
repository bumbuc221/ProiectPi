package p1;
/**
 * Clasa <h1>Conectare Utilizator</h1>
 * Clasa creaza o interfata de conecatre a utilizatorilor abonati la biblioteca
 * @version 1.9
 * @author Bumbuc Ivan
 */

import p2.Utilizator;
import p2.UtilizatorNormal;
import p3.Date;
import p4.Butoane;
import p4.FereastraAdministrator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.Map;

/**
 * Clasa pentru a limita scrierea cnp-ului cu 13 cifre
 */
class JTextFieldLimit extends PlainDocument {
    private int limit;

    /**
     * Constructor cu paramentrii
     * @param limit limita de cifre pt text
     */
    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    /**
     * Contructor cu doua parametrii
     * @param limit
     * @param upper
     */
    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if (str == null)
            return;
        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

/**
 * Clasa de conectare a Utilizatorilor abonati <h1>ConectareUtilizator</h1>
 * Utilizatori abonati la biblioteca vor putea verifica
 */
public class ConectareUtilizator {
    private JFrame window;
    private JButton conectare;
    private JTextPane serie,cnp;
    private JTextField cnpText,serieText;
    private JLabel logo;

    /**
     * Contructor pentru conectare
     *
     */
    public ConectareUtilizator(JFrame a, Date utilizatori)
    {
        window = new JFrame("Conectare");
        conectare=new JButton();
        caracteristiciFereastra();
        caracteristiciButon();
        caracteristiciScris();
        caracteristiciLogo();
        caracteristiciTextField();
        actiuneButonConectare(a,utilizatori);
        window.add(logo);
        window.setLayout(null);
        window.setVisible(true);
    }

    /**
     *Metoda cu caracteristicile feresteri
     */
    private void caracteristiciFereastra()
    {
       // window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 600);
        window.setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);
        window.getContentPane().setBackground(Color.WHITE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - window.getSize().width / 2, dim.height / 2 - window.getSize().height / 2);
    }

    /**
     * Metoda cu caractersticile logo-ului
     */
    private void caracteristiciLogo()
    {
        logo = new JLabel();
        logo.setBounds(0, 0, 385, 570);
        logo.setBackground(Color.white);
        //logo.setIcon(new ImageIcon("Imagini\\user_login.png"));
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logoConectareUser.png").getImage().getScaledInstance(385, 570, Image.SCALE_DEFAULT)));
        logo.setOpaque(true);
    }

    /**
     * Metoda cu caracteristicile scrisului cnp si serie
     */
    private void caracteristiciScris()
    {
        //cnp
        cnp=new JTextPane();
        cnp.setEditable(false);
        cnp.setBounds(90,250,100,30);
        cnp.setFont(new Font("cnp",Font.BOLD,25));
        cnp.setText("CNP");
        cnp.setForeground(Color.black);
        cnp.setOpaque(false);

        //serie
        serie=new JTextPane();
        serie.setEditable(false);
        serie.setBounds(90,330,100,30);
        serie.setFont(new Font("cnp",Font.BOLD,25));
        serie.setText("Serie");
        serie.setForeground(Color.black);
        serie.setOpaque(false);
        window.add(cnp);
        window.add(serie);
    }

    /**
     * Metoda cu caracteristicile jtextField
     */
    private void caracteristiciTextField()
    {
        cnpText= Butoane.textField2(95,290,200,30,13,Color.black);
        serieText=Butoane.textField2(95,370,200,30,0,Color.black);
        window.add(serieText);
        window.add(cnpText);
    }

    /**
     * Metoda cu caracteristicile butonului de logare
     */
    private void caracteristiciButon()
    {
        conectare=Butoane.normal2(125,490,150,35,"Conectare",20);
        window.add(conectare);
    }

    /**
     * Metoda de actiune a butonului de conectare
     * <p>In caz in care nu se gaseste utilizatorul se afiseaza un mesaj de ereaore
     * altfel se conecateaza </p>.
     * @param a reprezinta frame-ul de la fereastra de meniu
     * @param utilizatori generarea tuturor utilizatorilor
     */
    private void actiuneButonConectare(JFrame a,Date utilizatori)
    {
        conectare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              if(!conectareUtilizator(cnpText.getText(),serieText.getText(),utilizatori))
                    fereastraAvertisment();
                else {
                    a.dispose();
                    window.dispose();
                    FereastraProfilUtilizator utilizatorProfil=new FereastraProfilUtilizator(utilizatori.getListaUtilizatori().get(cnpText.getText()),utilizatori);
                }
            }
        });
    }

    /**
     * Metoada care verifica daca s a gasit un utilizator
     * <p>Aceasta metoda este apelata la apasarea butonului de conectare</p>
     * @param cnp
     * @param s
     * @param a
     * @return
     */
    private boolean conectareUtilizator(String cnp,String s,Date a)
    {
        if(cnp==null || cnp.length()==0)
            return false;
        if(serie==null || s.length()==0)
            return false;
        if(!a.getListaUtilizatori().containsKey(cnp))
            return false;
        if(!a.getListaUtilizatori().get(cnp).getSerieUtilizator().equals(s))
            return false;
        return true;
    }

    /**
     * Metoda creaza o noua fereastra cu un mesaj de avertisment
     */
    private void fereastraAvertisment()
    {
        JFrame a=new JFrame("Eroare Conectare");
        a.setResizable(false);
        a.setSize(500, 300);
        a.getContentPane().setBackground(Color.WHITE);
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        a.setIconImage(icon);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        a.setLocation(dim.width / 2 - a.getSize().width / 2, dim.height / 2 - a.getSize().height / 2);
        JTextPane mesaj=new JTextPane();
        mesaj.setEditable(false);
        mesaj.setBounds(40,40,500,150);
        mesaj.setFont(new Font("cnp",Font.BOLD,18));
        mesaj.setText("                   Eroare conectare\nUtilizator nerecunoscut. Datele sunt incorecte.\n Daca nu sunteti abonati abonarea se face la \n               primul imprumut efectuat.");
        JButton ok=new JButton("Ok");
        setareCuloareActiune(ok);
        ok.setBounds(195,200,80,30);
        ok.setBackground(new Color(255,240,0));   // fundalul
        ok.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        ok.setFont(new Font("Buton1",  Font.BOLD, 18)); // fontul textului
        ok.setForeground(Color.black);
        ok.setBorder(new LineBorder(Color.black, 2));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.dispose();
            }
        });
        a.add(ok);
        a.add(mesaj);
        a.setLayout(null);
        a.setVisible(true);
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
}
