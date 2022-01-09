package p1;

/**
 * Clasa reprezinta clasa de profil al utilizatorului
 * <p>Este prezentat numele email,istoric,si punctele acumulate</p>
 */

import p2.*;
import p3.Date;
import p4.Butoane;

import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.multi.MultiToolTipUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class FereastraProfilUtilizator {
    private JFrame window;
    private JLabel logo,nume,linie,fundal,mesajF;
    private JTextPane informatiiProfil,mesaj;
    private JButton inapoi,generareIstoric;
    private JTextArea textIstoric;

    public FereastraProfilUtilizator(UtilizatorNormal utilizator,Date informatii)
    {
        window=new JFrame("Profil");
        caracteristiciFereastra();
        caracteristiciLogo();
        caracteristiciNume(utilizator);
        caracteristiciLinie();
        caracteriticiInformatiiProfil(utilizator);
        caracteristiciButonInapoi(informatii);
        caracteristiciButonGenerareIstoric(utilizator);
        setFundal();
        setMesaj(utilizator,recomandari(utilizator,informatii),informatii);
        generareIstoric.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                caracteristiciIstoric(utilizator,informatii);

            }
        });
        window.add(fundal);
        window.add(mesajF);
        window.setLayout(null);
        window.setVisible(true);
    }

    /**
     * Metoda prezinta caracteristicile ferestrei al profilului de utilizator
     */
    private void caracteristiciFereastra()
    {
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
        logo.setBackground(Color.white);     // background alb
        logo.setBounds(70,0,90,90); // dimensiunea
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logoStanga.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT))); //setarea imagini
        logo.setOpaque(true); // sa fie vizibil
        window.add(logo);
    }
    /**
     * Functia caracteristici Nume care este sus in mijloc
     */
    private void caracteristiciNume(Utilizator a){
        nume=new JLabel();
        nume.setBounds(230,0,400,100); // marimea si pozitia
        nume.setText("Bun venit    "+a.getNumeUtilizator());                    // numele (textul setat)
        nume.setFont(new Font("User", Font.BOLD, 30));  // fontul textului
        nume.setForeground(new Color(99,12,120));     // culoarea textului
        nume.setBackground(Color.WHITE);         // backround ul alb
        nume.setOpaque(true);                   // sa fie vizibil
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
    /**
     * Metoda de caracteristici a informatiilor afisate persoanei care s a conectat
     */
    private void caracteriticiInformatiiProfil(UtilizatorNormal a)
    {
        informatiiProfil=new JTextPane();
        informatiiProfil.setForeground(Color.white);
        informatiiProfil.setOpaque(false);
        informatiiProfil.setEditable(false);
        informatiiProfil.setBounds(20,120,390,380);
        informatiiProfil.setFont(new Font("cnp",Font.BOLD,18));
        String f="Fara restrictie";
        if(a.isSuspendat())
            f="Restrictie pana in "+a.getDataDisponibiLaImprumut();
        String s="  Nume: "+a.getNumeUtilizator()+"\n\n"+
                 "  Email: "+ a.getEmailUtilizator()+"\n\n"+
                 "  CNP: "+a.getCnpUtilizator()+"\n\n"+
                 "  Serie: "+a.getSerieUtilizator()+"\n\n"+
                 "  Status: "+f+"\n\n"+
                 "  Puncte total: "+a.getPunctePlus()+"\n\n"+
                 "  Numar Intarzieri: "+a.getPuncteMinus()+"\n\n"+
                 "  Data abonare: "+a.getDataInregistrare();

        informatiiProfil.setText(s);
        window.add(informatiiProfil);
    }
    private void setFundal()
    {
        fundal = new JLabel();
        fundal.setBounds(10,110,400,400);
        fundal.setBackground(Color.white);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\profilUtilizatorDate.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT)));
        fundal.setOpaque(true);
    }
    private void setMesaj(Utilizator a,int i,Date info)
    {
        mesajF = new JLabel();
        mesajF.setBounds(440,120,330,150);
        mesajF.setBackground(Color.red);
        mesajF.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\mesajUtil.png").getImage().getScaledInstance(330, 150, Image.SCALE_DEFAULT)));
        mesajF.setOpaque(true);
        String s="";
        if(info.getListaIstoric().get(a.getCnpUtilizator())!=null && info.getListaIstoric().get(a.getCnpUtilizator()).size()>0)
        {
            s+=" Hei, bine ai revenit!\n\n Avem o recomandare pentru tine, \n Cartea: "+info.getListaCarti().get(i).getNumeCarte();
        }
        else
        {
            s+=" Hei, bine ai venit!\n\n Avem o recomandare pentru tine, \n Cartea: "+info.getListaCarti().get(i).getNumeCarte();
        }
        mesaj= Butoane.textPane2(450,150,310,100,s,15);
        window.add(mesaj);
    }
    /**
     * Caracteristici Buton inapoi la Userwindow
     */
    private void caracteristiciButonInapoi(Date a)
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
                UserWindow u=new UserWindow(a);
                window.dispose();
            }
        });
    }

    /**
     * Buton pentru generarea istroicului
     * @param a
     */
    private void caracteristiciButonGenerareIstoric(UtilizatorNormal a)
    {
        generareIstoric=new JButton();
        generareIstoric.setText("Istoric");
        setareCuloareActiune(generareIstoric);
        generareIstoric.setBounds(   680,280,90,20);
        generareIstoric.setBackground(new Color(255,240,0));   // fundalul
        generareIstoric.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        generareIstoric.setFont(new Font("Buton1",  Font.BOLD, 12)); // fontul textului
        generareIstoric.setForeground(Color.black);     // culoarea textului
        generareIstoric.setBorder(new LineBorder(Color.black, 2));
        window.add(generareIstoric);
    }

    /**
     * Metoda pentru crearea teaxt areea unde vor fii afisate istoricul
     * @param a infromatiile
     */
    private void caracteristiciIstoric(UtilizatorNormal a,Date informatii)
    {
        textIstoric=new JTextArea(a.getIstoricImprumuturi().size(),20);
        textIstoric.setBackground(Color.white);
        textIstoric.setFont(new Font("User", Font.BOLD, 18));
        textIstoric.selectAll();
        textIstoric.replaceSelection("");
        textIstoric.setEditable(false);
        textIstoric.append("        Istoricul imprumuturilor:  \n\n");
        if(informatii.getListaIstoric().get(a.getCnpUtilizator())!=null) {
            for (Istoricul k : informatii.getListaIstoric().get(a.getCnpUtilizator())) {
                if (k.getStatus().equals("DA"))
                    textIstoric.append("Nume: " + k.getNumeCarte() + "\n" + "Data: " + k.getDataInchiriere() + "/" + k.getDataReturnare() + "\nStatus: returnare CU INTARZIERE" + "\n\n");
                else
                    textIstoric.append("Nume: " + k.getNumeCarte() + "\n" + "Data: " + k.getDataInchiriere() + "/" + k.getDataReturnare() + "\nStatus: returnare FARA INTARZIERE" + "\n\n");

            }
        }
        else
            textIstoric.append("Nu exista istoric!");
        JScrollPane x=new JScrollPane(textIstoric);
        x.setBounds(420,300,360,245);
        x.setBackground(Color.white);
        x.setBorder(new LineBorder(Color.white));
        window.add(x);
        SwingUtilities.updateComponentTreeUI(window);
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
    private int recomandari(UtilizatorNormal a,Date info)
    {
        int id=0;
        Random rn = new Random();
        List<Istoricul> x=info.getListaIstoric().get(a.getCnpUtilizator());
        if(x!=null && x.size()>0)
        {
            Map<String,Integer> categori=new TreeMap<>();
            Map<Integer,String> carte=new TreeMap<>();
            for (Istoricul g:x)
            {
                if(categori.containsKey(g.getCategorieCarte()))
                {
                    categori.put(g.getCategorieCarte(),categori.get(g.getCategorieCarte()+1));
                }
                else
                {
                    categori.put(g.getCategorieCarte(),1);
                }
                carte.put(g.getIdCarte(),g.getNumeCarte());
            }
            List<Integer> disponibile=new ArrayList<>();
            int k=0;
            for(Map.Entry<Integer, Carte> h:info.getListaCarti().entrySet())
            {
                if(k==10)
                    break;
                if(!carte.containsKey(h.getKey()))
                {disponibile.add(h.getKey());
                    k++;}
            }
            if(disponibile.size()>0)
            {
                id= disponibile.get(rn.nextInt(disponibile.size())-1);
            }
            if(id==0)
            {
                int j=0,e=0;
                while(!info.getListaCarti().containsKey(j))
                {
                    j=rn.nextInt(info.getListaCarti().size()-1);
                }
                id=j;
            }
        }
        else
        {
            int j=0,e=0;
            while(!info.getListaCarti().containsKey(j))
            {
                j=rn.nextInt(info.getListaCarti().size()-1);
            }
            id=j;
        }
        return id;
    }
}
