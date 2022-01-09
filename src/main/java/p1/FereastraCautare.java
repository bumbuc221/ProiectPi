package p1;

import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FereastraCautare implements MouseListener {

    private String[] defaultValues;
    private JList jList;
    private JTextField x;
    private JFrame window;
    private JLabel logo;
    private JLabel nume,linie,font;
    private JButton cautare,inapoi,informatiiCarte;
    private JTable tabel;
    private JTextPane textInfo;

    public FereastraCautare(Date carti) {
        window=new JFrame("Cautare");
         x = new JTextField("Introduce numele");
        caracteristiciFereasra(carti);
        caracteristiciLogo();
        caracteristiciNume();
        caracteristiciLinie();
        caracteristiciFont();
        int i=0;
        defaultValues=new String[carti.getNumeCartiCautare().size()];
        for (String h:carti.getNumeCartiCautare()) {
            defaultValues[i++] = h;
        }
        cautare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String q=x.getText();
                x.setText("");
                x.setText(q);
                window.remove(jList);
                creareTabel(conectareBaza(q));
            }
        });
        // JScrollPane a=new JScrollPane();
        //JScrollPane scrollableTextArea = new JScrollPane(jList);
        // frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT,10,100));
        //frame.getContentPane().add(scrollableTextArea);
        createTextField();
        x.setEditable(false);
        window.addMouseListener(this);
        //frame.pack();
      //  frame.setLocationRelativeTo(null);
        informatiiCarte(carti);
        window.add(x);
        window.add(logo);
        window.add(nume);
        window.add(linie);
        window.add(font);
        window.setLayout(null);
        window.setVisible(true);
    }
    private void caracteristiciFereasra(Date a) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // optiune de inchidere la x
        window.setSize(800,600);                       // dimensiunea
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
        caracteristiciButoane(a);
         }
    private void caracteristiciLogo() {
        logo=new JLabel();
        logo.setBounds(70,0,90,90); // dimensiunea
        logo.setBackground(Color.white);     // background alb
        logo.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\logoStanga.png").getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT))); //setarea imagini
        logo.setOpaque(true); // sa fie vizibil
    }
    private void caracteristiciNume(){
        nume=new JLabel();
        nume.setBounds(250,0,300,90); // marimea si pozitia
        nume.setText("Cautare Carte");                    // numele (textul setat)
        nume.setFont(new Font("User", Font.BOLD, 30));  // fontul textului
        nume.setForeground(new Color(82, 17, 127));     // culoarea textului
        nume.setBackground(Color.WHITE);         // backround ul alb
        nume.setOpaque(true);                   // sa fie vizibil
    }
    private void caracteristiciLinie() {
        linie=new JLabel();
        linie.setBounds(100 ,90,800,5);  // marimea si pozitia
        linie.setBackground(new Color(82, 17, 127)); // culoarea la fundal ala acesteia
        linie.setOpaque(true);        // sa fie vizibila
    }

    private void caracteristiciFont() {
        font=new JLabel();      // creare
        font.setBounds(0 ,90,260,472); // setare lungime,latime si pozitia
        font.setBackground(new Color(82, 17, 127)); // culaorea de fundal
        font.setOpaque(true);   // sa fie vizibil
        font.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\cautareBaraF.png").getImage().getScaledInstance(260, 472, Image.SCALE_DEFAULT)));

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
    private void caracteristiciButoane(Date a)
    {
        cautare=new JButton();
        cautare.setText("Cauta");
        setareCuloareActiune(cautare);
        cautare.setBounds(185,102,68,25);
        cautare.setBackground(new Color(255,240,0));   // fundalul
        cautare.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        cautare.setFont(new Font("Buton1",  Font.BOLD, 12)); // fontul textului
        cautare.setForeground(Color.black);     // culoarea textului
        cautare.setBorder(new LineBorder(Color.black, 2));
        window.add(cautare);
        //buton inapoi
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

    private void createTextField() {
        x.setBounds(11,100, 169,30);
        x.setFont(new Font("Text",Font.BOLD,15));
        x.setBorder(new LineBorder(Color.white, 1));
        x.getDocument().addDocumentListener(new DocumentListener(){
            @Override public void insertUpdate(DocumentEvent e) { filter(); }
            @Override public void removeUpdate(DocumentEvent e) { filter(); }
            @Override public void changedUpdate(DocumentEvent e) {}
            private void filter() {
                String filter = x.getText().toUpperCase();
                filterModel((DefaultListModel<String>)jList.getModel(), filter);
            }
        });
    }

    private JList createJList() {
        JList list = new JList(createDefaultListModel());
        list.setVisibleRowCount(5);
        return list;
    }

    private ListModel<String> createDefaultListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : defaultValues) {
            model.addElement(s.toUpperCase());
        }
        return model;
    }

    public void filterModel(DefaultListModel<String> model, String filter) {
        for (String s : defaultValues) {
            if (!s.toUpperCase(Locale.ROOT).startsWith(filter)) {
                if (model.contains(s.toUpperCase(Locale.ROOT))) {
                    model.removeElement(s.toUpperCase(Locale.ROOT));
                }
            } else {
                if (!model.contains(s.toUpperCase(Locale.ROOT))){
                    model.addElement(s.toUpperCase(Locale.ROOT));
                }
            }
        }
    }

    public static void creare(Date carti) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new FereastraCautare(carti);
            }
        });
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        x.setEditable(true);
        jList=createJList();
        jList.setBounds(11,130,169,107);
        window.add(jList);
        jList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                x.removeAll();
                if(jList.getSelectedValue()!=null)
                    x.setText(jList.getSelectedValue().toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x.setEditable(true);
        jList=createJList();
        jList.setBounds(11,130,169,107);
        window.add(jList);
        jList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                x.removeAll();
                if(jList.getSelectedValue()!=null)
                    x.setText(jList.getSelectedValue().toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        x.setEditable(true);
        jList=createJList();
        jList.setBounds(11,130,169,107);
        window.add(jList);
        jList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                x.removeAll();
                if(jList.getSelectedValue()!=null)
                    x.setText(jList.getSelectedValue().toString());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void mouseExited(MouseEvent e) {
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
            for(int i=0;i<nou.length-1;i++)
                patern+="Nume like '%"+nou[i]+"%' and ";
            patern+="Nume like '%"+nou[nou.length-1]+"%'";
            String selectSql = "select * from Carti where "+patern+" order by Disponibilitate Asc";
            resultSet = statement.executeQuery(selectSql);
            List<String[]> infromatii=new ArrayList<>();
            int i=0;
            while (resultSet.next()) {
                if(i==8)
                    break;
                String[] k=new String[6];
               // System.out.println(resultSet.getString(1) + " " + resultSet.getString(2)+" "+resultSet.getString(3)+" "+resultSet.getString(8));
                k[0]=resultSet.getString(1);
                k[1]=resultSet.getString(2);
                k[2]=resultSet.getString(3);
                k[3]=resultSet.getString(11);
                k[4]=resultSet.getString(12);
                if(resultSet.getString(4).equals("Disponibil"))
                    k[5]=resultSet.getString(4);
                else
                    k[5]=resultSet.getString(14);
                infromatii.add(k);
                i++;
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
        String coloane[] = {"Id", "Nume", "Autor", "Sertar", "Raft", "Disponibil"};
        tabel = new JTable(date, coloane);

        tabel.setBounds(150, 150, 50, 300);
        tabel.setFont(new Font("tabel", Font.BOLD, 13));
        tabel.getColumnModel().getColumn(1).setPreferredWidth(400);
        tabel.getColumnModel().getColumn(2).setPreferredWidth(400);
        tabel.getColumnModel().getColumn(3).setPreferredWidth(130);
        tabel.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabel.getColumnModel().getColumn(5).setPreferredWidth(300);
        tabel.setRowHeight(40);
        tabel.getTableHeader().setForeground(Color.white);
        tabel.getTableHeader().setBackground(new Color(99, 12, 120));
        tabel.getTableHeader().setFont(new Font("tabel", Font.BOLD, 15));
        tabel.setForeground(Color.white);
        tabel.setBackground(new Color(99, 12, 120));
        tabel.setBorder(new LineBorder(new Color(99, 12, 120),0));
        tabel.getTableHeader().setForeground(Color.white);
        tabel.getTableHeader().setBackground(new Color(99, 12, 120));
        tabel.getTableHeader().setBorder(new LineBorder(new Color(99, 12, 120),2));
        tabel.setGridColor(new Color(134, 15, 129));
        tabel.setBackground(new Color(134, 15, 129));
        tabel.setForeground(Color.white);
        tabel.setSelectionBackground(Color.white);
        tabel.setBorder(new LineBorder(new Color(134, 15, 129),2));
        JPanel f = new JPanel();
        f.setBounds(280, 140, 500, 350);
        tabel.setEnabled(false);
        f.setBackground(Color.WHITE);
        f.add(new JScrollPane(tabel));
        window.add(f);
    }

    /**
     * Metoda pentru aflarea mai multor informatii despre o carte
     * @param a
     */
    private void informatiiCarte(Date a)
    {
        textInfo=new JTextPane();
        textInfo.setText("Pentru mai multe informatii despre o anumita carte apasa aici");
        textInfo.setBounds(300,530,350,30);
        textInfo.setBackground(Color.white);
        textInfo.setEditable(false);
        textInfo.setForeground(new Color(99,12,120));
        window.add(textInfo);
        informatiiCarte=new JButton("Informatii");
        setareCuloareActiune(informatiiCarte);
        informatiiCarte.setBounds(670,533,100,20);
        informatiiCarte.setBackground(new Color(255,240,0));   // fundalul
        informatiiCarte.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        informatiiCarte.setFont(new Font("Butoninfo",  Font.BOLD, 12)); // fontul textului
        informatiiCarte.setForeground(Color.black);
        informatiiCarte.setBorder(new LineBorder(Color.black, 2));
        window.add(informatiiCarte);
        informatiiCarte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x.setEditable(false);
                FereastraInformatiiCarte x=new FereastraInformatiiCarte(a,"Informatii despre carti",window);
                window.setVisible(false);
            }
        });
    }
}