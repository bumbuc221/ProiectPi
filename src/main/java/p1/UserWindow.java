package p1;
import p2.*;
import p3.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

/**
 * Clasa <h1>UserWindow</h1>
 * <p>Reprezinta interfata utilizatorului normal al biblioteci
 * unde poate sa verifice daca exisat carti si ce fel de carti exista
 * sau sa caute o carte sa afiseze cartile sa verifice propriul cont asupra
 * punctelor sau a istoricului pe care il are.</p>
 * @version 1.2
 * @author Bumbuc Ivan
 */
public class UserWindow {
    private JFrame window;   // este fereastra de utilizator
    private JLabel logo;     // reprezinta logo-ul din stanga sus
    private JLabel nume;       // numele de Utilizator
    private JLabel linie,font,linieSus,linieStanga,linieDreapta,linieJos;  // linia si fontul din cadrul unde sunt amplasate butaoanele, si cele 4 linii in jurul textului
    private JTextArea cartiTop;    // text area pentru afisarea cartilor
    private JButton cautare,verificareDisponibil,verificarePuncte,afisareCarti,selectareCategorie,getSelectareCategorieInformati,cartiSpeciale; // toate butoanele care se afla
    private JComboBox categori,categoriInformatii; // combobox de selectare primul este dreapta sus si al doilea dreapta jos
    private JList<String> listaCarti;
    private JScrollPane sc1;
    private JTextPane topDeSus;
    /**
     * Contructor cu parametru Date al clasei <h1>UserWindow</h1>
     * Aici sunt adaugate toate elementele in fereastra
     * @param carti
     */
    public UserWindow(Date carti) {
        window=new JFrame();  // crearea ferestrei
        caracteristiciFereasra();   // caracteristicile ferestrei (Ex: marime ,poitionare etc.)
        logo=new JLabel();         // logo-ul ferestrei amplasat sus  stanga
        caracteristiciLogo();      // caracteristici logo (marime ,asezare etc.)
        nume=new JLabel();         // numele (Utilizatori)
        caracteristiciNume();      // caracteristici nume
        linie=new JLabel();         // linia de sub nume
        caracteristiciLinie();        //caracteristici linie
        caracteristicContruTextArea();
        caracteristiciTextArea(carti);                    //caracteristici text area unde sunt afisate cartile
        categoriSetare(carti);
        caracteristiciButonSelectareCategorie(carti);
        butoaneMeniu();// butoane meniu care sunt aplasate in stanga
        cautare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraCautare.creare(carti);
                window.dispose();
            }
        });
        verificareDisponibil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraInformatiiCarte x=new FereastraInformatiiCarte(carti,"Informatii despre carti",window);
                window.setVisible(false);
            }
        });
        verificarePuncte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConectareUtilizator c=new ConectareUtilizator(window,carti);
            }
        });
        afisareCarti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraCautareDescriere x=new FereastraCautareDescriere(carti,"Cautare dupa descriere");
                window.dispose();
            }
        });
        cartiSpeciale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraCarteSpeciala x=new FereastraCarteSpeciala(carti,"Carti Speciale");
                window.dispose();
            }
        });
        setFont();
        // fonutl care este sub butoane
        window.add(logo);  // adaugare logo
        window.add(linie);  // adaugare linie
           //adaugare nume
        //window.add(cartiTop);     // adaugare text area
        window.add(sc1);
        window.add(font);
        window.add(nume);
        window.setLayout(null);
        window.setVisible(true);

    }

    /**
     * Functia de caracteristici fereastra Utilizator
     */
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
        logo.setBounds(70,0,90,90); // dimensiunea
        logo.setBackground(Color.white);     // background alb
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logoStanga.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT))); //setarea imagini
        logo.setOpaque(true); // sa fie vizibil
    }

    /**
     * Functia caracteristici Nume care este sus in mijloc
     */
    private void caracteristiciNume(){
        nume.setBounds(230,0,300,100); // marimea si pozitia
        nume.setText("Utilizator");                    // numele (textul setat)
        nume.setFont(new Font("User", Font.BOLD, 30));  // fontul textului
        nume.setForeground(new Color(82, 17, 127));     // culoarea textului
        nume.setBackground(Color.WHITE);         // backround ul alb
        nume.setOpaque(true);                   // sa fie vizibil
    }

    /**
     * Functia de caracteristici a liniei care este sub logo si numele de utilizator
     */
    private void caracteristiciLinie() {
        linie.setBounds(210 ,90,800,5);  // marimea si pozitia
        linie.setBackground(new Color(82, 17, 127)); // culoarea la fundal ala acesteia
        linie.setOpaque(true);        // sa fie vizibila
    }

    /**
     * Functia de caracteristici a Text area care se afla in mijloc si afiseaza cartile,
     * topul cartilor din fiecare gen
     * @param a reprezinta datele care le primeste cartile (toupul cartilor)
     */
    //-------------------------
    private void caracteristiciTextArea(Date a){
      //  cartiTop.setBounds(300,120,370,312); //  dimensiune
        topDeSus=new JTextPane();
        listaCarti=new JList<>(afisareCarti(a,"Toate"));
      //  listaCarti.setBounds(300,120,370,312);
       // cartiTop.setBackground(new Color(255,255,204)); // background -ul
        listaCarti.setBackground(new Color(255,255,204));
        //cartiTop.setFont(new Font("User", Font.BOLD, 18)); // fontul textului si marimea  is setarea
        listaCarti.setFont(new Font("User", Font.BOLD, 18));
      //  cartiTop.setForeground(new Color(99,12,120)); // culoarea textului
        listaCarti.setForeground(new Color(82, 17, 127));
        sc1=new JScrollPane(listaCarti);
        topDeSus.setBounds(300,120,370,55);
        topDeSus.setBackground(new Color(255,255,204));
        topDeSus.setForeground(new Color(82, 17, 127));
        topDeSus.setFont(new Font("User", Font.BOLD, 18));
        topDeSus.setEditable(false);
        window.add(topDeSus);
        sc1.setBounds(300,175,370,262);
        sc1.setBorder(new LineBorder(new Color(255,255,204)));
      //  afisareCarti(a,"Toate");                // functia de afisare a catilor care este apelata prima data pentru toate categoriile de carti
       // cartiTop.setEditable(false);                // oprirea sa fie editabil
    }

    /**
     * Functia de caracteristci a butoanelor de meniu
     * @param a reprezinta butonul
     * @param s textul care o sa contina butonul
     * @param x pozitia orizontal
     * @param y pozitia vertical
     */
    private void caracteristiciButon(JButton a,String s,int x,int y,String f,int x1,int x2)
    {
        JLabel iconLabel = new JLabel(new ImageIcon(new ImageIcon(f).getImage().getScaledInstance(x1, x2, 4)));
        JLabel clickMe = new JLabel(s, SwingConstants.CENTER);
        a.setBounds(x,y,200,35);     // setarea pozitiei si a marimi
        a.setBackground(new Color(255,240,0));   // fundalul
        a.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        clickMe.setFont(new Font("c",  Font.BOLD, 12)); // fontul textului
        clickMe.setForeground(Color.BLACK);     // culoarea textului
        a.setLayout(new BorderLayout());
        a.add(iconLabel, BorderLayout.CENTER);
        a.add(clickMe, BorderLayout.EAST);
        a.setBorder(BorderFactory.createEmptyBorder(4, 4, 2, 0));
        a.setBorder(new LineBorder(Color.black, 2));
    }
    private void setareCuloareActiune(JButton x)
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

    /**
     * Functia de setare a butoanelor din meniu se foloseste de functia de mai sus caracteristiciButon()
     */
    private void butoaneMeniu()
    {
        cautare=new JButton(); // crearea butonului de cautare
        caracteristiciButon(cautare,"Cautare dupa nume           ",30,120,"Imagini\\\\cautare.png",20,20); //apealrea functiei si setarea caracteristicilor
        setareCuloareActiune(cautare);
        verificareDisponibil=new JButton(); // creare buton verificare disponibilitate
        caracteristiciButon(verificareDisponibil,"Informatii detaliate carti    ",30,360,"Imagini\\\\book.png",20,20); // apealrea functiei si setarea caracteristicilor
        setareCuloareActiune(verificareDisponibil);
        verificarePuncte=new JButton();  // crearea buton de afisare puncte al utilizaotrului
        caracteristiciButon(verificarePuncte,"Verificare puncte profil    ",30,280,"Imagini\\\\user.png",20,20); //apealrea functiei si setarea caracteristicilor
        setareCuloareActiune(verificarePuncte);
        afisareCarti=new JButton();      // crearea buton afisare carti
        caracteristiciButon(afisareCarti,"Cautare dupa descriere    ",30,200,"Imagini\\\\search-engine.png",30,30); //apealrea functiei si setarea caracteristicilor
        setareCuloareActiune(afisareCarti);
        cartiSpeciale=new JButton();
        caracteristiciButon(cartiSpeciale,"Carti Speciale                  ",30,440,"Imagini\\\\badge.png",30,30);
        setareCuloareActiune(cartiSpeciale);
        window.add(cautare);   // adaugarea  celor 4 butaone in fereastra
        window.add(verificareDisponibil); // -- , , --
        window.add(verificarePuncte);  // -- , , --
        window.add(afisareCarti);
        window.add(cartiSpeciale);
    }

    /**
     * Functia de setare a fundalului unde se afla butoanele de meniu
     * Partea unde se afla butoanele colorata cu un violet (partea de meniu)
     */
    private void setFont() {
        font=new JLabel();      // creare
        font.setBounds(0 ,90,260,472); // setare lungime,latime si pozitia
        font.setBackground(new Color(82, 17, 127)); // culaorea de fundal
        font.setOpaque(true);   // sa fie vizibil
        font.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\cautareBaraF.png").getImage().getScaledInstance(260, 472, Image.SCALE_DEFAULT)));

    }

    /**
     * Functia de setare a primului JcomboBox care se afla categoriile de carti pentru top
     */
    private void categoriSetare(Date info)
    {
        String[] lista={"Toate","Roman","Poezii","Nuvela","Matematica","Fizica","Romana","Geografie","Chimie","Informatica","Programare","Povestiri","Stiinta","Filozofie"}; // taote categoriile de carti
        categori=new JComboBox(lista); // decalrare si setare cu categorii
        categori.setBounds(680,130,90,20); // marimea si pozitia
        categori.setBackground(Color.WHITE);    // fontul alb
        categori.setForeground(new Color(82, 17, 127)); // fontul textului
        categori.getEditor().getEditorComponent().setBackground(Color.WHITE); // fontul in timpul selectarii
        categori.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               listaCarti.removeAll();
               listaCarti.setModel(afisareCarti(info,(String) categori.getSelectedItem()));
           }
       });
        listaCarti.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(topDeSus.getText().contains("Top")!=false)
                {
                    int f=listaCarti.getSelectedIndex();
                    listaCarti.removeAll();
                    listaCarti.setModel(afisareInformatiiCarte(info,f+1,(String) categori.getSelectedItem()));
                }
            }
        });
        window.add(categori);  // adaugare
    }

    /**
     * Funtcia de afisare a toupurilor de carti imprumutate
     * @param a datele care vin cartile (topul catilor)
     * @param text prezinta categoria (ex: matematica,fizica,roman,...,etc.)
     */
    private DefaultListModel<String> afisareCarti(Date a,String text)
    {
        List<Integer> list=a.afisareTop(text); //lista care retine indicele cartilor
        DefaultListModel<String> l1 = new DefaultListModel<>();
        String g="";
        if(list.size()!=0) {
           g+="              Top "+list.size()+" cele mai imprumutate\n"+"                   din categoria "+text;
           // adaugarea titlului in text area
            for (int i = 0; i < list.size(); i++) { //parcurgerea listei si adaugarea cartilor
                if (i == 10)                        // parcurgerea se face maxim pana la 10
                    break;
                l1.addElement(" "+(i + 1) + ". " + a.getListaCarti().get(list.get(i)).getNumeCarte() + " de " + a.getListaCarti().get(list.get(i)).getAutorCarte() + "\n");  // adaugare in text area
            }
        }
        else{                    // in cazul in care nu exista inca nici-o carte din acesta categorie afiseaza acest mesaj
            g+="        Nu exista inca carti\n"+"        din categoria aceasta!";
        }
        topDeSus.setText(g);
        return l1;
    }

    /**
     * Functia de definire a ambelor butoane de selectare atat a categoriei cat si care carte din cele prezentate
     * in categorie
     * @param a datele care vin cartile
     */
    private void caracteristiciButonSelectareCategorie(Date a)
    {
        Icon d=new ImageIcon(new ImageIcon("Imagini\\\\refresh.png").getImage().getScaledInstance(15, 12, 4));
        selectareCategorie=new JButton(d); // primul buton de selectare a categoriei
        setareCuloareActiune(selectareCategorie);
        selectareCategorie.setBounds(630,440,40,20); //marime si pozitie primului buton
        selectareCategorie.setBackground(new Color(255,240,0));            // fundalul primului buton
        selectareCategorie.setBorder(new LineBorder(Color.black, 2));
        selectareCategorie.setFocusable(false);                                  // contur la primul buton
        selectareCategorie.setFont(new Font(Font.DIALOG,  Font.BOLD, 12));  // fontul textului la primul buton
        selectareCategorie.setForeground(Color.BLACK);    // culoarea textului la primul buton
        selectareCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaCarti.removeAll();
                listaCarti.setModel(afisareCarti(a,(String)categori.getSelectedItem()));
                listaCarti.removeAll();
                listaCarti.setModel(afisareCarti(a,(String)categori.getSelectedItem()));

            }
        });
        JTextPane informatie=new JTextPane();
        informatie.setText("* Apasati pe oricare dintre cartile de mai sus pentru a vedea mai multe informatii despre aceste *");
        informatie.setFont(new Font("c",Font.BOLD,11));
        informatie.setBackground(Color.white);
        informatie.setForeground(new Color(82, 17, 127));
        informatie.setBounds(300,440,300,45);
        window.add(informatie);
        window.add(selectareCategorie); // adaougare in fereastra
    }

    /**
     * Functia de caracteristici a Jcombobox 2 care rerezinta care carte sa afiseze informatia
     * @param a date care vin (cartile)
     */
    /*
    private void caracteristiciCategorieInformatii(Date a)
    {
        String[] s=new String[a.afisareTop(categori.getSelectedItem().toString()).size()]; // setare lungime cat are si lista de carti poate avea maxim 10
        for(int i=0;i<s.length;i++)
        {
            s[i]=((Integer)(i+1)).toString(); // adaugarea de text 1,2,...,10  in functie de lungime
        }
        categoriInformatii=new JComboBox(s);  // setare Jcombobox 2 in care prezinta cartile care le selectam
        categoriInformatii.setBounds(680,370,90,20);  // pozitia si marimea
        categoriInformatii.setBackground(Color.WHITE);  // fontul
        categoriInformatii.setForeground(new Color(99,12,120));  // culoarea textului
        categoriInformatii.getEditor().getEditorComponent().setBackground(Color.WHITE); // font la selecatare
        window.add(categoriInformatii);  // adaugare in fereastra
    }*/

    /**
     * Functia de setare a categoriilor in jcombobox 2 in functie de categoria selectata in jcombobox1
     * Poate fii minim 0 sau maxim 10 carti
     * @param a date care vin cartile
     */
    private void setareCategorieInformatii(Date a)
    {
        String[] s=new String[a.afisareTop(categori.getSelectedItem().toString()).size()]; // lungimea sirului de string in functie de lista
        for(int i=0;i<s.length;i++)
        {
            s[i]=((Integer)(i+1)).toString();  // transfromare in string
            categoriInformatii.addItem(s[i]);  // adaugare item in Jcombobox 2
        }
    }

    /**
     * Functia de afisare a informatiilor in functie de nr selectat in Jcombobox 2
     * @param a datele care vin
     * @param n nr cartii de la 0-10 (daca este 0 nu se afiseaza nimic)
     * @param text tipul cartii in functie de jcomboox1 (categoria din care se afla)
     */
    private DefaultListModel<String> afisareInformatiiCarte(Date a,int n,String text) {
        DefaultListModel<String> l1 = new DefaultListModel<>();
        if (n > 0) {
            List<Integer> list = a.afisareTop(text);  // lista cu cartile (indicile cartilor)
            topDeSus.setText("  Informatii despre Carte"); // titlu
            l1.addElement("  Nume: " + a.getListaCarti().get(list.get(n - 1)).getNumeCarte()); //numele cartii
            l1.addElement("  Autor: " + a.getListaCarti().get(list.get(n - 1)).getAutorCarte()); // autorul cartii
            l1.addElement("  Tema : " + a.getListaCarti().get(list.get(n - 1)).getTema());  // tema cartii
            l1.addElement("  Pozitia in biblioteca: " + "Raft: " + a.getListaCarti().get(list.get(n - 1)).getRaft() + ", " + "Sertar: " + a.getListaCarti().get(list.get(n - 1)).getSertar()); //pozitia in biblioteca a cartii
            l1.addElement("  Nr pagini: " + a.getListaCarti().get(list.get(n - 1)).getNrPagini());  // numarul de pagini a cartii
        }
        return l1;
    }

    /**
     * Functia de conturare a conturului de linii in jurul afisarii infromatiilor
     * Caractersitici ale celro 4 linii
     */
    private void caracteristicContruTextArea()
    {
        linieSus=new JLabel();
        linieSus.setBounds(295 ,118,378,3);  // marimea si pozitia
      //  cartiTop.setBounds(300,120,370,312);
       linieSus.setBackground(new Color(82, 17, 127)); // culoarea la fundal ala acestei
        linieSus.setOpaque(true);
        linieStanga=new JLabel();
        linieStanga.setBounds(295 ,118,3,320);
        linieStanga.setBackground(new Color(82, 17, 127));
        linieStanga.setOpaque(true);
        linieDreapta=new JLabel();
        linieDreapta.setBounds(670 ,118,3,320);
        linieDreapta.setBackground(new Color(82, 17, 127));
        linieDreapta.setOpaque(true);
        linieJos=new JLabel();
        linieJos.setBounds(295 ,435,378,3);
        linieJos.setBackground(new Color(82, 17, 127));
        linieJos.setOpaque(true);
        window.add(linieSus);
        window.add(linieStanga);
        window.add(linieDreapta);
        window.add(linieJos);
    }
}
