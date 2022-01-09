package p1;

import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AspectFereastra {
    protected JFrame window;
    protected JLabel logo,nume,linie;
    protected JButton inapoi;
    public AspectFereastra(Date informatii,String s)
    {
        window=new JFrame();
        caracteristiciFereasra();
        caracteristiciLogo();
        caracteristiciNume(s);
        caracteristiciLinie();
        butonInapoi(informatii);
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
        nume.setForeground(new Color(82, 17, 127));     // culoarea textului
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
        linie.setBackground(new Color(82, 17, 127)); // culoarea la fundal ala acesteia
        linie.setOpaque(true);        // sa fie vizibila
        window.add(linie);
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
    protected void butonInapoi(Date a)
    {
        inapoi=new JButton();
        inapoi.setText("Inapoi");
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   10,520,90,25);
        inapoi.setBackground(new Color(255,240,0));   // fundalul
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 12)); // fontul textului
        inapoi.setForeground(Color.BLACK);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.black, 2));
        window.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserWindow u=new UserWindow(a);
                window.dispose();
            }
        });
    }

}
