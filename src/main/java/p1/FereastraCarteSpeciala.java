package p1;

import p2.CarteSpeciala;
import p2.Utilizator;
import p2.UtilizatorNormal;
import p3.Date;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FereastraCarteSpeciala extends AspectFereastra{
    private JLabel parteaDeJos;
    private JButton p1,p2,p3,p4,p5;
    private JPanel cartiImg;
    private JTextPane textInfo,info,cont;
    private JTextField cnp;
    private JButton informatiiCarte,verifica;
    private String[] img=new String[5];
    private List<Integer> raritati=new ArrayList<>();
    public FereastraCarteSpeciala(Date informatii, String s) {
        super(informatii, s);
        setSir();
        cartiRecomandate(img);
        informatiiCarte(informatii);
        aspectParteaJos();
        caracteristiciCarti(informatii);
        caracteristiciVerificare();
        verifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificaCont(informatii);
            }
        });
    }
    private void caracteristiciVerificare()
    {
        info=new JTextPane();
        info.setEditable(false);
        info.setBackground(Color.WHITE);
        info.setText("* Introduceti CNP-ul pentru a\n vedea daca puteti imprumuta o carte *");
        info.setForeground(new Color(82, 17, 127));
        info.setBounds(10,110,200,30);
        info.setFont(new Font("font",Font.TYPE1_FONT,10));
        cnp=new JTextField();
        cnp.setBounds(10,150,120,20);
        cnp.setBorder(new LineBorder(Color.black, 2));
        verifica=new JButton("Verifica");
        setareCuloareActiune(verifica);
        verifica.setBounds(150,150,80,20);
        verifica.setBackground(new Color(255,240,0));   // fundalul
        verifica.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        verifica.setFont(new Font("Butoninfo",  Font.BOLD, 12)); // fontul textului
        verifica.setForeground(Color.black);
        verifica.setBorder(new LineBorder(Color.black, 2));
        cont=new JTextPane();
        cont.setFont(new Font("c",Font.BOLD,15));
        cont.setForeground(new Color(82, 17, 127));
        cont.setBackground(Color.white);
        cont.setEditable(false);
        cont.setBounds(10,200,240,200);
        window.add(cnp);
        window.add(info);
        window.add(verifica);
        window.add(cont);
    }
    private void verificaCont(Date a)
    {
        String s="";
        if(cnp.getText().length()>0)
        {
            if(a.getListaUtilizatori().containsKey(cnp.getText()))
            {
                UtilizatorNormal x=a.getListaUtilizatori().get(cnp.getText());
                s+="Utilizator: "+x.getNumeUtilizator()+"\n"+
                        "Puncte cont: "+x.getPunctePlus()+"\n";
                int d=0;
                for(int i=0;i<raritati.size();i++)
                {
                    if(x.getPunctePlus()>raritati.get(i))
                        d=raritati.get(i);
                }
                if(d!=0)
                {
                    s+="Aveti acces pana la raritatea "+d;
                }
                else
                {
                    s+="\n** Nu aveti puncte suficiente acumulate pentru a avea acces la una dintre aceste carti!";
                }
            }
            else
            {
                s+="Utilizator nerecunoscut!";
            }
            cont.setText(s);
        }
    }
    private void caracteristiciCarti(Date carti)
    {
        File x=new File("ImaginiCarti/");
        List<String> imagini=new ArrayList<>();
        for(File d:x.listFiles())
            imagini.add(d.getName().split("\\.")[0]);
        int z= imagini.size()/2;
        for(int i=0;i<imagini.size()-1;i++)
        {
            for(int j=i+1;j<imagini.size();j++)
                if(((CarteSpeciala)carti.getListaCarti().get(Integer.parseInt(imagini.get(i)))).getRaritate()>((CarteSpeciala)carti.getListaCarti().get(Integer.parseInt(imagini.get(j)))).getRaritate())
                {
                    String aux=imagini.get(j);
                    imagini.set(j,imagini.get(i));
                    imagini.set(i,aux);
                }
        }
        cartiImg=new JPanel();
        cartiImg.setBackground(Color.white);
        cartiImg.setLayout(new GridLayout(2*z,2));
        int i=0,j=0,k=0;
        while(k<imagini.size()/2)
        {
            int q=0;
            while(q<2)
            {
                JLabel p = new JLabel();
                p.setBackground(Color.white);
                p.setIcon(new ImageIcon(new ImageIcon("ImaginiCarti\\\\"+imagini.get(i)+".jpg").getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT)));
                p.setOpaque(true);
                cartiImg.add(p);
                i++;
                q++;
            }
            int w=0;
            while(w<2)
            {
                JTextPane t=new JTextPane();
                raritati.add(((CarteSpeciala)carti.getListaCarti().get(Integer.parseInt(imagini.get(j)))).getRaritate());
                t.setText(carti.getListaCarti().get(Integer.parseInt(imagini.get(j))).getNumeCarte()+",\n"+((CarteSpeciala)carti.getListaCarti().get(Integer.parseInt(imagini.get(j)))).getRaritate()+" raritate");
                t.setEditable(false);
                cartiImg.add(t);
                w++;
                j++;
            }
            k++;
        }
        final Color newColor =new Color(82, 17, 127);
        ScrollBarUI yourUI = new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(newColor);
                return button;
            }
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(82, 17, 127);
            }
        };
        JScrollPane g=new JScrollPane(cartiImg);
        g.getVerticalScrollBar().setBackground(Color.WHITE);
        g.getHorizontalScrollBar().setUI(yourUI);
        g.getVerticalScrollBar().setUI(yourUI);
        g.setBorder(new BevelBorder(0,Color.WHITE,Color.WHITE));
        g.setBounds(280, 120, 505, 300);
        window.add(g);
    }
    private void setSir()
    {
        File x=new File("ImaginiCarti/");
        List<String> imagini=new ArrayList<>();
        for(File d:x.listFiles())
            imagini.add(d.getName());
        List<Integer> r=new ArrayList<>();
        Random a=new Random();
        while (r.size() != 5) {
            int y = a.nextInt(imagini.size());
            if (!r.contains(y))
                r.add(y);
        }
        for(int i=0;i<5;i++)
        {
         img[i]=imagini.get(r.get(i));
        }
    }
    private void aspectParteaJos()
    {
        parteaDeJos=new JLabel();
        parteaDeJos.setBackground(new Color(191, 11, 131));
        parteaDeJos.setBounds(0,440,800,125);
        parteaDeJos.setOpaque(true);
        window.add(parteaDeJos);
    }
    private void cartiRecomandate(String[] s)
    {
        aspecteButoane(p1,170,450,s[0]);
        aspecteButoane(p2,300,450,s[1]);
        aspecteButoane(p3,430,450,s[2]);
        aspecteButoane(p4,560,450,s[3]);
        aspecteButoane(p5,690,450,s[4]);
    }
    private void aspecteButoane(JButton c1,int a,int b,String s)
    {
        c1=new JButton();
        c1.setIcon(new ImageIcon(new ImageIcon("ImaginiCarti\\\\"+s).getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT)));
        c1.setBounds(a,b,70,80);
        //c1.setBorderPainted(false);
        c1.setBorder(new LineBorder(Color.black, 1));
        window.add(c1);
    }
    private void informatiiCarte(Date a)
    {
        textInfo=new JTextPane();
        textInfo.setText("Pentru mai multe informatii despre o anumita carte apasa aici");
        textInfo.setBounds(300,540,350,30);
        textInfo.setBackground(new Color(191, 11, 131));
        textInfo.setEditable(false);
        textInfo.setForeground(new Color(255,240,0));
        window.add(textInfo);
        informatiiCarte=new JButton("Informatii");
        setareCuloareActiune(informatiiCarte);
        informatiiCarte.setBounds(670,540,100,20);
        informatiiCarte.setBackground(new Color(255,240,0));   // fundalul
        informatiiCarte.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        informatiiCarte.setFont(new Font("Butoninfo",  Font.BOLD, 12)); // fontul textului
        informatiiCarte.setForeground(Color.black);
        informatiiCarte.setBorder(new LineBorder(Color.black, 2));
        window.add(informatiiCarte);
        informatiiCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                FereastraInformatiiCarte x=new FereastraInformatiiCarte(a,"Informatii despre carti",window);
            }
        });
    }
}
