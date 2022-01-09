package p4;


import p2.Administrator;
import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FereastraConectareAdmin {
    private JFrame window;
    private JTextField email;
    private JPasswordField parola;
    private JLabel img;
    private JTextPane emailT,parolaT;
    private JButton conectare;
    public FereastraConectareAdmin(Date informatii,JFrame x)
    {
        caracteristiciFereastra();
        carcateristiciButton();
        caracteristiciJlabel();
        caracteristiciTextField();
        conectare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conectareCont(informatii,x);
            }
        });
        setText();
        window.add(img);
        window.setLayout(null);
        window.setVisible(true);
    }
    private void caracteristiciFereastra()
    {
        window=new JFrame("Conectare Admin");
       // window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // optiune de inchidere la x
        window.setSize(515,530);                       // dimensiunea
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }
    private void setText()
    {
        emailT=Butoane.textPane3(100,180,200,30,"Email",25,Color.black);
        window.add(emailT);
        parolaT=Butoane.textPane3(100,270,200,30,"Parola",25,Color.black);
        window.add(parolaT);
    }
    private void caracteristiciJlabel()
    {
        img=new JLabel();
        img.setBounds(0,0,500,500); // dimensiunea
        img.setBackground(Color.white);     // background alb
        img.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\admin3.png").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT))); //setarea imagini
        img.setOpaque(true); // sa fie vizibil
    }
    private void caracteristiciTextField()
    {
        email=Butoane.textField2(100,220,290,25,0,Color.black);
        window.add(email);
        parola=Butoane.passwordField2(100,310,300,25,20,Color.black);
        window.add(parola);
    }
    private void carcateristiciButton()
    {
        conectare=Butoane.normal2(180,390,140,40,"Conectare",22);
        window.add(conectare);
    }
    private void conectareCont(Date conturi,JFrame x)
    {
        String e=email.getText();
        String p=parola.getText();
        if(e!=null && p!=null && e.length()>0 && p.length()>0)
        {
            if(conturi.getListaAdministatori().containsKey(e))
            {
                if(((Administrator)conturi.getListaAdministatori().get(e)).getParola().equals(p))
                {
                    x.dispose();
                    window.dispose();
                    conturi.setEmail(e);
                    FereastraAdministrator n=new FereastraAdministrator(conturi);
                }
                else
                    creareFereastraAux("Parola incorecta.");
            }
            else
            {
                creareFereastraAux("Email incorect.");
            }
        }
        else
              creareFereastraAux("Introduceti datele in toate campurile.");
    }
    private void creareFereastraAux(String s)
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
        mesaj.setBounds(40,60,500,150);
        mesaj.setFont(new Font("cnp",Font.BOLD,18));
        mesaj.setText(""+s+"\nDaca nu detineti functia de administrator\nva rugam sa parasiti aceasta zona!");
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
