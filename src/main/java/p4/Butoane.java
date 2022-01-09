package p4;

import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Butoane {

    public static JButton setNext(int x,int y)
    {
        JButton next=new JButton();
        next.setBounds(x,y,50,50);
        next.setBackground(Color.white);
        next.setBorderPainted(false);
        next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next.png").getImage().getScaledInstance(50, 50, 4)));
        next.setOpaque(false);
        next.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                next.setBounds(x-3,y-3,60,60);

                next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                next.setBounds(x,y,50,50);
                next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        next.addMouseListener(flup);
        return next;
    }
    public static JButton butonInapoi(int a)
    {
        JButton inapoi=new JButton();
        inapoi.setBounds(   8,a,50,50);
        inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin.png").getImage().getScaledInstance(50, 50,4)));
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setBorder(new LineBorder(Color.white, 0));
        inapoi.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                inapoi.setBounds(   5,a-3,60,60);
                inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin2.png").getImage().getScaledInstance(60, 60,4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                inapoi.setBounds(   8,a,50,50);
                inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin.png").getImage().getScaledInstance(50, 50,4)));
            }
        };
        inapoi.addMouseListener(flup);
        return inapoi;
    }

    public static JButton normal(int x,int y,int z,int q,String s)
    {
        JButton inapoi=new JButton();
        inapoi.setText(s);
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   x,y,z,q);
        inapoi.setBackground(new Color(68, 140, 203));   // fundalul
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, 15)); // fontul textului
        inapoi.setForeground(Color.white);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.white, 2));
        return  inapoi;
    }
    public static JButton normal2(int x,int y,int z,int q,String s,int e)
    {
        JButton inapoi=new JButton();
        inapoi.setText(s);
        setareCuloareActiune(inapoi);
        inapoi.setBounds(   x,y,z,q);
        inapoi.setBackground(new Color(68, 140, 203));   // fundalul
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setFont(new Font("Buton1",  Font.BOLD, e)); // fontul textului
        inapoi.setForeground(Color.white);     // culoarea textului
        inapoi.setBorder(new LineBorder(Color.white, 2));
        return  inapoi;
    }
    public static void setareCuloareActiune(JButton x)
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
    public static JTextField textField(int x,int y,int l,int L,int limit)
    {
        JTextField id=new JTextField();
        id.setBounds(x,y,l,L);
        id.setFont(new Font("f",Font.BOLD,20));
        if(limit!=0)
            id.setDocument(new LimitaCaractere(limit));
        id.setOpaque(false);
        id.setForeground(Color.white);
        id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        return id;
    }
    public static JTextField textField2(int x,int y,int l,int L,int limit,Color d)
    {
        JTextField id=new JTextField();
        id.setBounds(x,y,l,L);
        id.setFont(new Font("f",Font.BOLD,22));
        if(limit!=0)
            id.setDocument(new LimitaCaractere(limit));
        id.setOpaque(false);
        id.setForeground(d);
        id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, d));
        return id;
    }

    public static JPasswordField passwordField(int x,int y,int l,int L)
    {
        JPasswordField id=new JPasswordField();
        id.setBounds(x,y,l,L);
        id.setFont(new Font("f",Font.BOLD,20));
        id.setOpaque(false);
        id.setForeground(Color.white);
        id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        return id;
    }
    public static JPasswordField passwordField2(int x,int y,int l,int L,int nr,Color d)
    {
        JPasswordField id=new JPasswordField();
        id.setBounds(x,y,l,L);
        id.setFont(new Font("f",Font.BOLD,nr));
        id.setOpaque(false);
        id.setForeground(d);
        id.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, d));
        return id;
    }

    public static JTextPane textPane(int x,int y,int l,int L,String s)
    {
        JTextPane cnpText=new JTextPane();
        cnpText.setBounds(x,y,l,L);
        cnpText.setText(s);
        cnpText.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,25));
        cnpText.setForeground(Color.WHITE);
        cnpText.setOpaque(false);
        cnpText.setEditable(false);
        return cnpText;
    }
    public static JTextPane textPane2(int x,int y,int l,int L,String s,int nr)
    {
        JTextPane cnpText=new JTextPane();
        cnpText.setBounds(x,y,l,L);
        cnpText.setText(s);
        cnpText.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,nr));
        cnpText.setForeground(Color.WHITE);
        cnpText.setOpaque(false);
        cnpText.setEditable(false);
        return cnpText;
    }

    public static JTextPane textPane3(int x,int y,int l,int L,String s,int nr,Color g)
    {
        JTextPane cnpText=new JTextPane();
        cnpText.setBounds(x,y,l,L);
        cnpText.setText(s);
        cnpText.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,nr));
        cnpText.setForeground(g);
        cnpText.setOpaque(false);
        cnpText.setEditable(false);
        return cnpText;
    }

    public static void setFereastraAux(String a,String b)
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
    public static boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isDigit(String name){return name.matches("[0-9]+");}
    public static void setFereastraAux2(Date info,String a,String b,JFrame j2,String cnp)
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
                if(j2!=null)
                    j2.dispose();
                FereastraAdministrator x=new FereastraAdministrator(info);
                FereastraImprumutId q=new FereastraImprumutId(info,cnp,x.getWindow());
                //j2.setVisible(true);
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

    public static void setFereastraAux2_2(Date info,String a,String b,JFrame j2,String cnp,String nume,String email,String serie)
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
                if(j2!=null)
                    j2.dispose();
                FereastraAdministrator x=new FereastraAdministrator(info);
                FereastraImprumutId q=new FereastraImprumutId(info,cnp,nume,serie,email,x.getWindow());
                //j2.setVisible(true);
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

    public static void setFereastraAuxRestrictie(String a,String b)
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
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\"+b).getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextPane x=new JTextPane();
        x.setText(a);
        x.setOpaque(false);
        x.setForeground(Color.white);
        x.setEditable(false);
        x.setFont(new Font("Arial",Font.BOLD,15));
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
