package p4;

import jdk.jshell.execution.Util;
import p2.Imprumuturi;
import p2.Suspendari;
import p2.Utilizator;
import p2.UtilizatorNormal;
import p3.Date;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FereastraImprumut {
    JFrame windowCnp;
    JLabel fundalCnp;
    JPanel jpCnp;
    JTextField cnp;
    JTextPane cnpText;
    JButton next;
    public FereastraImprumut(Date informatii,JFrame x)
    {
        setWindowCnp();
        setNext();
        setFundalCnp();
        setCnpText();
        setCnp();
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isAlpha(cnp.getText()))
                    setFereastraAux("CNP format incorect!");
                else if(cnp.getText().length()<13)
                    setFereastraAux("Insuficiente cifre in CNP!");
                else
                    afiramreImprumut(informatii,cnp.getText(),x);
            }
        });
        jpCnp.add(fundalCnp);
        windowCnp.add(jpCnp);
        windowCnp.setLayout(null);
        windowCnp.setVisible(true);
    }

    /**Functia pentru fereastra de cnp
     *
     */
    private void setWindowCnp()
    {
        windowCnp=new JFrame("Introducere Cnp");
        jpCnp=new JPanel();
        windowCnp.setSize(500,400);
        jpCnp.setBounds(0,0,485,370);// dimensiunea
        jpCnp.setLayout(null);
        windowCnp.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        windowCnp.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        windowCnp.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        windowCnp.setLocation(dim.width/2-windowCnp.getSize().width/2, dim.height/2-windowCnp.getSize().height/2); // afisarea in centru ecranului
    }
    private void setFundalCnp()
    {
        fundalCnp=new JLabel();
        fundalCnp.setBounds(0,0,485,370);
        fundalCnp.setBackground(Color.white);     // background alb
        fundalCnp.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\imprumutCarteCnp.png").getImage().getScaledInstance(485, 370, 4))); //setarea imagini
        fundalCnp.setOpaque(true); // sa fie vizibil
    }
    private void setCnpText()
    {
        cnpText=new JTextPane();
        cnpText.setBounds(50,150,70,30);
        cnpText.setText("CNP");
        cnpText.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,25));
        cnpText.setForeground(Color.WHITE);
        cnpText.setOpaque(false);
        cnpText.setEditable(false);
        jpCnp.add(cnpText);
    }
    private void setCnp()
    {
        cnp=new JTextField();
        cnp.setBounds(150,150,200,30);
        cnp.setFont(new Font("f",Font.BOLD,17));
        cnp.setDocument(new LimitaCaractere(13));
        cnp.setOpaque(false);
        cnp.setForeground(Color.white);
        cnp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        jpCnp.add(cnp);
    }
   private void setNext()
   {
       next=new JButton();
       next.setBounds(220,250,50,50);
       next.setBackground(Color.white);
       next.setBorderPainted(false);
       next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next.png").getImage().getScaledInstance(50, 50, 4)));
       next.setOpaque(false);
       next.setContentAreaFilled(false);
       MouseAdapter flup = new MouseAdapter() {
           @Override
           public void mouseEntered(MouseEvent m) {
               next.setBounds(217,247,60,60);

               next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next2.png").getImage().getScaledInstance(60, 60, 4)));
           }
           @Override
           public void mouseExited(MouseEvent m) {
               next.setBounds(220,250,50,50);
               next.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\next.png").getImage().getScaledInstance(50, 50, 4)));
           }
       };
       next.addMouseListener(flup);
       jpCnp.add(next);
   }
   private void setFereastraAux(String a)
   {
       JFrame windowAux=new JFrame("Avertisment");
       windowAux.setSize(400,200);
       windowAux.getContentPane().setBackground(Color.WHITE);        // culoarea de background
       windowAux.setResizable(false);                             // sa nu fie marit
       Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
       windowAux.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
       windowAux.setLocation(dim.width/2-windowAux.getSize().width/2, dim.height/2-windowAux.getSize().height/2+40); // afisarea in centru ecranului
       JLabel fundal=new JLabel();
       fundal.setBounds(0,0,385,170);
       fundal.setBackground(Color.white);     // background alb
       fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\auxImprumut.png").getImage().getScaledInstance(385, 170, Image.SCALE_DEFAULT))); //setarea imagini
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
    private boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    private void afiramreImprumut(Date informatii,String s,JFrame q)
    {
        if(informatii.getListaUtilizatori().containsKey(s))
        {
            if(informatii.getListaUtilizatori().get(s).isSuspendat())
            {
                UtilizatorNormal d=informatii.getListaUtilizatori().get(s);
                fereastraAux2("Utlizator abonat suspendat!","Nume: "+d.getNumeUtilizator(),"Data acces: "+d.getDataDisponibiLaImprumut(),0,"");
            }
            else
            {
                if(informatii.getListaUtimeleImprumuturiIntarizate().containsKey(s)) {
                    Imprumuturi x=informatii.getListaUtimeleImprumuturiIntarizate().get(s);
                    fereastraAux2("Utilizator abonat aflat in intarziere!", "Nume: " + x.getNumeUtilizator(), "Data imrpumut: " + x.getDataImprumut()+", Data limita: "+x.getDataLimita(),x.getId(),x.getNumeCarte());
                }
                else {
                    if(informatii.getListaNumarImprumutariCont().containsKey(s) && informatii.getListaNumarImprumutariCont().get(s)>=calculCartiNrMaxim(informatii,s))
                    {
                        Butoane.setFereastraAuxRestrictie("Numarul de imprumuturi disponibile este la maxim, returnati o carte pentru a putea imprumuta!","auxImprumut.png");
                    }
                    else
                    {
                        FereastraImprumutId x = new FereastraImprumutId(informatii, cnp.getText(), q);
                        windowCnp.dispose();
                    }
                }
            }
        }
        else
        {
            if (informatii.getListaUtimeleImprumuturiIntarizate().containsKey(s))
            {
                Imprumuturi x=informatii.getListaUtimeleImprumuturiIntarizate().get(s);
                fereastraAux2("Utilizator neabonat aflat in intarziere!", "Nume: " + x.getNumeUtilizator(), "Data imrpumut: " + x.getDataImprumut()+", Data limita: "+x.getDataLimita(),x.getId(),x.getNumeCarte());
            }
            else
            {
                if (informatii.getListaSuspendari().containsKey(s)) {
                    Suspendari x=informatii.getListaSuspendari().get(s);
                    fereastraAux2("Utilizator neabonat suspendat!","Nume: "+x.getNumeUtilizator(),"Data acces: "+x.getDataAcces(),x.getIdCarte(),x.getNumeCarte());
                }
                else {

                    if(informatii.getListaNumarImprumutariCont().containsKey(s) && informatii.getListaNumarImprumutariCont().get(s)>=3)
                    {
                        Butoane.setFereastraAuxRestrictie("Numarul de imprumuturi disponibile este la maxim, returnati o carte pentru a putea imprumuta!","auxImprumut.png");
                    }
                    else
                    {
                        FereastraImprumutCont x = new FereastraImprumutCont(informatii, s);
                        q.dispose();
                        windowCnp.dispose();
                    }
                    // System.out.println("Aveti acces la imrpumut!");
                }
            }
        }
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

    private void fereastraAux2(String a,String b,String c,int nr,String numeC)
    {
        JFrame windowAux=new JFrame("Acces refuzat");
        windowAux.setSize(400,300);
        windowAux.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        windowAux.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        windowAux.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        windowAux.setLocation(dim.width/2-windowAux.getSize().width/2, dim.height/2-windowAux.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,270);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\aux2Imprumut.png").getImage().getScaledInstance(385, 270, Image.SCALE_DEFAULT))); //setarea imagini
        fundal.setOpaque(true); // sa fie vizibil
        JTextField x1=setTextF(a);
        x1.setBounds(20,30,350,30);
        JTextField x2=setTextF(b);
        x2.setBounds(20,60,350,30);
        JTextField x3=setTextF(c);
        x3.setBounds(20,90,350,30);
        if(nr!=0) {
            JTextField x4 = setTextF("Id Carte: "+nr);
            x4.setBounds(20, 120, 350, 30);
            windowAux.add(x4);
            JTextField x5 = setTextF("Nume Carte: "+numeC);
            x5.setBounds(20, 150, 350, 30);
            windowAux.add(x5);
        }
        JButton ok=new JButton();
        ok.setBounds(165,200,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,196,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,200,50,50);
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
        windowAux.add(x1);
        windowAux.add(x2);
        windowAux.add(x3);
        windowAux.add(fundal);
        windowAux.setLayout(null);
        windowAux.setVisible(true);
    }
    private JTextField setTextF(String a)
    {
        JTextField x1=new JTextField();
        x1.setText(a);
        x1.setOpaque(false);
        x1.setForeground(Color.white);
        x1.setEditable(false);
        x1.setFont(new Font("Arial",Font.BOLD,15));
        x1.setBorder(null);
        return x1;
    }
    public void setCnp(String a)
    {
        cnp.setText(a);
    }
}
