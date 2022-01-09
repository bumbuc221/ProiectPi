package p4;

import p1.FirstWindow;
import p2.Carte;
import p2.CarteSpeciala;
import p3.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class FereastraAdaugareCarte {
    private JFrame window;
    private JPanel x;
    private JLabel fundal;
    private JTextField nume,autor,puncte,categorie,tema,cuvinteCheie,nrPagini,raft,sertar,puncteBonus,raritate;
    private JTextPane numeT,autorT,puncteT,categorieT,temaT,desriereT,cuvinteCheieT,nrPaginiT,raftT,sertarT,puncteBonusT,raritateT,id,imagine,selectImg;
    private JTextPane descriere;
    private JButton inapoi,adaugare,reset,fileImg;
    public FereastraAdaugareCarte(Date informatii)
    {
        setWindow();
        setPanel();
        setFundal();
        setareCampuri();
        setTexte();
        setDescriere();
        setSteluta();
        butonInapoi(informatii);
        setButoane(informatii);
        setActiuneButoane(informatii);
        setButonImagine();
        x.add(fundal);
        window.add(x);
        window.setLayout(null);
        window.setVisible(true);
    }

    // pt ediatarea cartii
    public FereastraAdaugareCarte(Date info,int n)
    {
        setWindow();
        setPanel();
        setFundal();
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\editareCarteModificare.png").getImage().getScaledInstance(785, 570, Image.SCALE_DEFAULT)));
        setareCampuri();
        setTexte();
        setDescriere();
        setSteluta();
        butonInapoi(info);
        setButoane(info);
        setButoane2();
        setTextEditare(info,n);
        setActiuneButoane2(info,n);
        setButonImagine();
        x.add(fundal);
        window.add(x);
        window.setLayout(null);
        window.setVisible(true);
    }
    private void setWindow()
    {
        window=new JFrame("Administrator");
        JFrame.setDefaultLookAndFeelDecorated(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // optiune de inchidere la x
        window.setSize(800,600);                       // dimensiunea
        window.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        window.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        window.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2); // afisarea in centru ecranului
    }
    private void setPanel()
    {
        x=new JPanel();
        x.setBounds(0,0,800,600);
        x.setBackground(Color.white);
        x.setLayout(null);
    }
    private void setFundal()
    {
        fundal = new JLabel();
        fundal.setBounds(0, 0, 785, 570);
        fundal.setBackground(Color.white);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adaugareC2.png").getImage().getScaledInstance(785, 570, Image.SCALE_DEFAULT)));
        fundal.setOpaque(true);
    }

    private void setButoane2()
    {
        adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\salvareCarte.png").getImage().getScaledInstance(40, 40, 4)));
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                adaugare.setBounds(657,497,50,50);

                adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\salvareCarte2.png").getImage().getScaledInstance(50, 50, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                adaugare.setBounds(660,500,40,40);
                adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\salvareCarte.png").getImage().getScaledInstance(40, 40, 4)));
            }
        };
        adaugare.addMouseListener(flup);
    }
    private void setButoane(Date a)
    {
        adaugare=new JButton();
        adaugare.setBounds(660,500,40,40);
        adaugare.setBackground(Color.white);
        adaugare.setBorderPainted(false);
        adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adaugareC.png").getImage().getScaledInstance(40, 40, 4)));
        adaugare.setOpaque(false);
        adaugare.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                adaugare.setBounds(657,497,50,50);

                adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adaugareC_v1.png").getImage().getScaledInstance(50, 50, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                adaugare.setBounds(660,500,40,40);
                adaugare.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\adaugareC.png").getImage().getScaledInstance(40, 40, 4)));
            }
        };
        adaugare.addMouseListener(flup);
        x.add(adaugare);
        reset=new JButton();
        reset.setBounds(600,500,40,40);
        reset.setBackground(Color.white);
        reset.setBorderPainted(false);
        reset.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\deleteC.png").getImage().getScaledInstance(40, 40, 4)));
        reset.setOpaque(false);
        reset.setContentAreaFilled(false);
        MouseAdapter flup2 = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                reset.setBounds(597,497,50,50);

                reset.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\deleteC_v2.png").getImage().getScaledInstance(50, 50, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                reset.setBounds(600,500,40,40);
                reset.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\deleteC.png").getImage().getScaledInstance(40, 40, 4)));
            }
        };
        reset.addMouseListener(flup2);
        x.add(reset);

    }

    private void setTextEditare(Date a,int n)
    {
        Carte x=a.getListaCarti().get(n);
        nume.setText(x.getNumeCarte());
        autor.setText(x.getAutorCarte());
        puncte.setText(x.getPuncte()+"");
        categorie.setText(x.getCategorie());
        tema.setText(x.getTema());
        cuvinteCheie.setText(x.getCuvinteCheie());
        nrPagini.setText(x.getNrPagini()+"");
        raft.setText(x.getRaft()+"");
        sertar.setText(x.getSertar()+"");
        descriere.setText(x.getDescriere());
        id.setText("Id "+n);
        if(a.getListaCarti().get(n) instanceof CarteSpeciala)
        {
            puncteBonus.setText(((CarteSpeciala)a.getListaCarti().get(n)).getPuncteBonus()+"");
            raritate.setText(((CarteSpeciala)a.getListaCarti().get(n)).getRaritate()+"");
            selectImg.setText("ImaginiCarti/"+n+".jpg");
        }

    }

    private void setActiuneButoane(Date a)
    {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               resetareText();
            }
        });
        adaugare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugareCarteBiblioteca(a);
            }
        });
    }
    private void setActiuneButoane2(Date a,int n)
    {
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetareText();
            }
        });
        adaugare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editareCarteBiblioteca(a,n);
            }
        });
    }
    private void adaugareCarteBiblioteca(Date a)
    {
        if(nume.getText().length()>0 && autor.getText().length()>0 && puncte.getText().length()>0 && categorie.getText().length()>0 && tema.getText().length()>0 && cuvinteCheie.getText().length()>0 && nrPagini.getText().length()>0 && raft.getText().length()>0 && sertar.getText().length()>0) {
            String[] lista={"Toate","Roman","Poezii","Nuvela","Matematica","Fizica","Romana","Geografie","Chimie","Informatica","Programare","Povestiri","Stiinta","Filozofie"}; // taote categoriile de carti
            int ok1=0;
            for(int i=0;i<lista.length;i++)
                if(categorie.getText().equals(lista[i]))
                {
                    ok1=1;
                    break;
                }
            if(ok1!=0) {
                if(!isAlpha(puncte.getText())) {
                    if(!isAlpha(nrPagini.getText())) {
                        if(!isAlpha(sertar.getText()) && !isAlpha(raft.getText())) {
                            if((raritate.getText().length()==0 && puncteBonus.getText().length()==0) || (!isAlpha(raritate.getText()) && !isAlpha(puncteBonus.getText()))) {
                                if(raritate.getText().length()>0 && puncteBonus.getText().length()>0)
                                {
                                    if(selectImg.getText().length()>0)
                                    {
                                        try {
                                            Path temp = Files.copy
                                                    (Paths.get(selectImg.getText()),
                                                            Paths.get("ImaginiCarti\\\\"+(genereareMaxim()+1)+".jpg"));
                                            fereastraAux3(a);
                                        } catch (IOException e) {
                                            fereastraAux2("Alegeti imaginea valida pentru cartea speciala!");
                                        }
                                    }
                                    else
                                        fereastraAux2("Alegeti imaginea pentru cartea speciala!");
                                }
                                else
                                    fereastraAux3(a);
                            }
                            else
                            {
                                fereastraAux2("Introduceti numere valide la raritate si la puncte bonus!");
                            }
                        }
                        else
                        {
                            fereastraAux2("Introduceti numere valide la sertar si raft!");
                        }
                    }
                    else
                    {
                        fereastraAux2("Numar pagini incorect");
                    }
                }
                else
                {
                    fereastraAux2("Introduceti un numarr vaid la puncte!");
                }
            }
            else
            {
                fereastraAux2("Nu ati introdus o categorie valida!");
            }
        }
        else
        {
            fereastraAux2("Nu ati introdus toate datele, campurile cu * sunt obligatorii!");
        }
    }

    private void editareCarteBiblioteca(Date a,int n)
    {
        if(nume.getText().length()>0 && autor.getText().length()>0 && puncte.getText().length()>0 && categorie.getText().length()>0 && tema.getText().length()>0 && cuvinteCheie.getText().length()>0 && nrPagini.getText().length()>0 && raft.getText().length()>0 && sertar.getText().length()>0) {
            String[] lista={"Toate","Roman","Poezii","Nuvela","Matematica","Fizica","Romana","Geografie","Chimie","Informatica","Programare","Povestiri","Stiinta","Filozofie"}; // taote categoriile de carti
            int ok1=0;
            for(int i=0;i<lista.length;i++)
                if(categorie.getText().equals(lista[i]))
                {
                    ok1=1;
                    break;
                }
            if(ok1!=0) {
                if(!isAlpha(puncte.getText())) {
                    if(!isAlpha(nrPagini.getText())) {
                        if(!isAlpha(sertar.getText()) && !isAlpha(raft.getText())) {
                            if((raritate.getText().length()==0 && puncteBonus.getText().length()==0) || (!isAlpha(raritate.getText()) && !isAlpha(puncteBonus.getText()))) {
                                if(raritate.getText().length()>0 && puncteBonus.getText().length()>0)
                                {
                                    if(selectImg.getText().length()>0)
                                    {
                                        try {
                                            Path temp = Files.copy
                                                    (Paths.get(selectImg.getText()),
                                                            Paths.get("ImaginiCarti\\\\"+n+".jpg"));
                                            fereastraAux4(a,n);
                                        } catch (IOException e) {
                                            fereastraAux2("Alegeti imaginea valida pentru cartea speciala!");
                                        }
                                    }
                                    else
                                        fereastraAux2("Alegeti imaginea pentru cartea speciala!");
                                }
                                else
                                    fereastraAux4(a,n);
                            }
                            else
                            {
                                fereastraAux2("Introduceti numere valide la raritate si la puncte bonus!");
                            }
                        }
                        else
                        {
                            fereastraAux2("Introduceti numere valide la sertar si raft!");
                        }
                    }
                    else
                    {
                        fereastraAux2("Numar pagini incorect");
                    }
                }
                else
                {
                    fereastraAux2("Introduceti un numarr vaid la puncte!");
                }
            }
            else
            {
                fereastraAux2("Nu ati introdus o categorie valida!");
            }
        }
        else
        {
            fereastraAux2("Nu ati introdus toate datele, campurile cu * sunt obligatorii!");
        }
    }
    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private void fereastraAux2(String a)
    {
        JFrame f=new JFrame();
        f.setSize(400,250);                       // dimensiunea
        f.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        f.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        f.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,230);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\avertismentAddC.png").getImage().getScaledInstance(385, 230, 4)));
        fundal.setOpaque(true);
        JButton ok=new JButton();
        ok.setBounds(165,150,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,146,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,150,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }
        });
        JTextPane text=new JTextPane();
        text.setBounds(100,40,250,100);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        text.setText(a);
        text.setFont(new Font("Arial",Font.BOLD,20));
        ok.addMouseListener(flup);
        f.add(text);
        f.add(ok);
        f.add(fundal);
        f.setLayout(null);
        f.setVisible(true);
    }


    private int  genereareMaxim()
    {
        int d=0;
        ResultSet resultSet = null;
        String url="jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
        String username="ivan";
        String password="12345";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();){
            String selectSql = "select max(CarteId) from Carti";
            resultSet = statement.executeQuery(selectSql);
            if (resultSet.next()) {
                d=resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.print("Oopss");
            e.printStackTrace();
        }
        return d;
    }
    private void resetareText()
    {
        nume.setText("");autor.setText("");puncte.setText("");
        categorie.setText("");tema.setText("");cuvinteCheie.setText("");
        nrPagini.setText("");raft.setText("");sertar.setText("");
        puncte.setText("");puncteBonus.setText("");raritate.setText("");
        descriere.setText("");selectImg.setText("");
    }
    private JTextField setTextFiled(int x,int y)
    {
        JTextField d=new JTextField();
        d.setBounds(x,y,200,30);
        d.setFont(new Font("f",Font.BOLD,17));
        d.setOpaque(false);
        d.setForeground(Color.white);
        d.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));
        return d;
    }
    private void setareCampuri()
    {
        nume=setTextFiled(230,100);
        x.add(nume);
        autor=setTextFiled(230,140);
        x.add(autor);
        puncte=setTextFiled(230,180);
        x.add(puncte);
        categorie=setTextFiled(230,220);
        x.add(categorie);
        tema=setTextFiled(230,260);
        x.add(tema);
        cuvinteCheie=setTextFiled(230,300);
        x.add(cuvinteCheie);
        nrPagini=setTextFiled(230,340);
        x.add(nrPagini);
        raft=setTextFiled(230,380);
        x.add(raft);
        sertar=setTextFiled(230,420);
        x.add(sertar);
        puncteBonus=setTextFiled(230,460);
        x.add(puncteBonus);
        raritate=setTextFiled(230,500);
        x.add(raritate);
    }
    private JTextPane setTextPane(int x,int y,String a,int z,int c)
    {
        JTextPane d=new JTextPane();
        d.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, c));
        d.setBounds(x,y,z,30);
        d.setText(a);
        d.setForeground(Color.white);
        d.setOpaque(false);
        d.setEditable(false);
        return d;
    }
    private void setTexte()
    {
        id=setTextPane(500,100,"Id "+(genereareMaxim()+1),200,20);
        x.add(id);
        imagine=setTextPane(500,420,"Imagine",100,20);
        x.add(imagine);
        selectImg=setTextPane(520,450,"",200,10);
        x.add(selectImg);
        numeT=setTextPane(50,100,"Nume",200,20);
        x.add(numeT);
        autorT=setTextPane(50,140,"Autor",200,20);
        x.add(autorT);
        puncteT=setTextPane(50,180,"Puncte",200,20);
        x.add(puncteT);
        categorieT=setTextPane(50,220,"Categorie",200,20);
        x.add(categorieT);
        temaT=setTextPane(50,260,"Tema",200,20);
        x.add(temaT);
        cuvinteCheieT=setTextPane(50,300,"Cuvinte cheie",200,20);
        x.add(cuvinteCheieT);
        nrPaginiT=setTextPane(50,340,"Numar Pagini",200,20);
        x.add(nrPaginiT);
        raftT=setTextPane(50,380,"Raft",200,20);
        x.add(raftT);
        sertarT=setTextPane(50,420,"Sertar",200,20);
        x.add(sertarT);
        puncteBonusT=setTextPane(50,460,"Puncte bonus",200,20);
        x.add(puncteBonusT);
        raritateT=setTextPane(50,500,"Raritate",200,20);
        x.add(raritateT);
        desriereT=setTextPane(500,160,"Descriere",200,20);
        x.add(desriereT);
    }
    private void setDescriere()
    {
        descriere=new JTextPane();
        descriere.setOpaque(false);
        descriere.setForeground(Color.white);
        descriere.setFont(new Font("Arial",Font.BOLD,15));
        JScrollPane f=new JScrollPane(descriere);
        f.setOpaque(false);
        f.getViewport().setOpaque(false);
        f.setBounds(500,200,200,200);
        f.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
        x.add(f);
    }
    private JTextPane steluta(int y)
    {
        JTextPane d=new JTextPane();
        d.setFont(new Font("Verdana", Font.BOLD|Font.ITALIC, 15));
        d.setBounds(35,y,20,30);
        d.setText("*");
        d.setForeground(Color.white);
        d.setOpaque(false);
        d.setEditable(false);
        return d;
    }
    private void setSteluta()
    {
        JTextPane x1=steluta(100);
        x.add(x1);
        JTextPane x2=steluta(140);
        x.add(x2);
        JTextPane x3=steluta(180);
        x.add(x3);
        JTextPane x4=steluta(220);
        x.add(x4);
        JTextPane x5=steluta(260);
        x.add(x5);
        JTextPane x6=steluta(300);
        x.add(x6);
        JTextPane x7=steluta(340);
        x.add(x7);JTextPane x8=steluta(380);
        x.add(x8);JTextPane x9=steluta(420);
        x.add(x9);
    }
    private void setButonImagine()
    {
        fileImg=new JButton();
        fileImg.setBounds(620,420,40,40);
        fileImg.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\fileChose1.png").getImage().getScaledInstance(40, 40,4)));
        fileImg.setOpaque(false);
        fileImg.setFocusable(false);
        fileImg.setBorder(new LineBorder(Color.white, 0));
        fileImg.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                fileImg.setBounds(   617,417,50,50);
                fileImg.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\fileChose2.png").getImage().getScaledInstance(50, 50,4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                fileImg.setBounds(   620,420,40,40);
                fileImg.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\fileChose1.png").getImage().getScaledInstance(40, 40,4)));
            }
        };
        fileImg.addMouseListener(flup);
        fileImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(puncteBonus.getText().length()>0 && raritate.getText().length()>0) {
                    if (!isAlpha(puncteBonus.getText()) && !isAlpha(raritate.getText())) {
                       selectareImagineJChose();
                    } else
                        fereastraAux2("Introduceti numere valide la puncte bonus si raritate!");
                }
                else
                    fereastraAux2("Introduceti mai intai datele la puncte bonus si raritate!");
            }
        });
        x.add(fileImg);
    }
    private void selectareImagineJChose()
    {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Select an image");
        jfc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg", "jpg");
        jfc.addChoosableFileFilter(filter);

        int returnValue = jfc.showDialog(null, "Save");
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectImg.setText(jfc.getSelectedFile().getPath());
        }

    }
    private void butonInapoi(Date a)
    {
        inapoi=new JButton();
        inapoi.setBounds(   8,525,30,30);
        inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin.png").getImage().getScaledInstance(30, 30,4)));
        inapoi.setOpaque(false);
        inapoi.setFocusable(false);                          // sa aiba contur cand se afla sageata de mouse pe el
        inapoi.setBorder(new LineBorder(Color.white, 0));
        inapoi.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                inapoi.setBounds(   5,522,40,40);
                inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin2.png").getImage().getScaledInstance(40, 40,4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                inapoi.setBounds(   8,525,30,30);
                inapoi.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\backAdmin.png").getImage().getScaledInstance(30, 30,4)));
            }
        };
        inapoi.addMouseListener(flup);
        x.add(inapoi);
        inapoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdministrator u=new FereastraAdministrator(a);
                window.dispose();
            }
        });
    }

    /**
     * Functia pentru hover
     * @param x
     */
    protected void setareCuloareActiune(JButton x)
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
    private void fereastraAux3(Date a)
    {
        JFrame f=new JFrame();
        f.setSize(400,250);                       // dimensiunea
        f.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        f.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        f.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,230);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\confirmAddC.png").getImage().getScaledInstance(385, 230, 4)));
        fundal.setOpaque(true);
        JButton ok=new JButton();
        ok.setBounds(165,150,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,146,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,150,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ok = 0;
                String[] s = new String[14];
                s[0] = nume.getText();
                s[1] = autor.getText();
                s[2] = "Disponibil";
                s[3] = puncte.getText();
                s[4] = categorie.getText();
                s[5] = tema.getText();
                s[6] = descriere.getText();
                s[7] = cuvinteCheie.getText();
                s[8] = nrPagini.getText();
                s[9] = raft.getText();
                s[10] = sertar.getText();
                s[11] = "0";
                if (puncteBonus.getText().length() > 0 && raritate.getText().length() > 0) {
                    ok = 1;
                    s[12] = puncteBonus.getText();
                    s[13] = raritate.getText();
                }
                String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
                String username = "ivan";
                String password = "12345";
                String[][] d = null;
                try (
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();) {
                    String insertSql = "";
                    if (ok == 0) {
                        insertSql += "INSERT INTO Carti (Nume,Autor,Disponibilitate,CartePuncte,Categorie,Tema,Descriere,CuvinteCheie,NumarPagini,Raft,Sertar,NumarImprumutari)" +
                                " VALUES" + "(" + "'" + s[0] + "'" + "," + "'" + s[1] + "'" + "," + "'" + s[2] + "'" + "," + "'" + s[3] + "'" + "," + "'" + s[4] + "'" + "," + "'" + s[5] + "'" + "," + "'" + s[6] + "'" + "," + "'" + s[7] + "'" + "," + "'" + s[8] + "'" + "," + "'" + s[9] + "'" + "," + "'" + s[10] + "'" + "," + 0 + ")";
                        a.adaugareCarteDate(new Carte((genereareMaxim() + 1), s[0], s[1], "Disponibil", Integer.parseInt(s[3]), s[4], s[5], s[6], s[7], Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]), "", 0), (genereareMaxim() + 1));
                    } else {
                        insertSql += "INSERT INTO Carti (Nume,Autor,Disponibilitate,CartePuncte,Categorie,Tema,Descriere,CuvinteCheie,NumarPagini,Raft,Sertar,NumarImprumutari,PuncteBonus,Raritate)" +
                                " VALUES" + "(" + "'" + s[0] + "'" + "," + "'" + s[1] + "'" + "," + "'" + s[2] + "'" + "," + "'" + s[3] + "'" + "," + "'" + s[4] + "'" + "," + "'" + s[5] + "'" + "," + "'" + s[6] + "'" + "," + "'" + s[7] + "'" + "," + "'" + s[8] + "'" + "," + "'" + s[9] + "'" + "," + "'" + s[10] + "'" + "," + 0 + "," + "'" + s[12] + "'" + "," + "'" + s[13] + "'" + ")";
                        a.adaugareCarteDate(new CarteSpeciala((genereareMaxim() + 1), s[0], s[1], "Disponibil", Integer.parseInt(s[3]), s[4], s[5], s[6], s[7], Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]), 0, Integer.parseInt(s[12]), Integer.parseInt(s[13]), ""), (genereareMaxim() + 1));
                    }
                    resetareText();
                    statement.executeUpdate(insertSql);
                    fereastraAux2("Cartea a fos adaugata   cu    succes!");
                    id.setText("Id "+(genereareMaxim()+1));
                } catch (SQLException q) {
                    System.out.print("Oopss");
                    q.printStackTrace();
                }
                f.dispose();
            }
        });
        JTextPane text=new JTextPane();
        text.setBounds(100,40,250,100);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        text.setText("Doriti sa adaugati ?");
        text.setFont(new Font("Arial",Font.BOLD,20));
        ok.addMouseListener(flup);
        f.add(text);
        f.add(ok);
        f.add(fundal);
        f.setLayout(null);
        f.setVisible(true);
    }
    private void fereastraAux4(Date a,int n)
    {
        JFrame f=new JFrame();
        f.setSize(400,250);                       // dimensiunea
        f.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        f.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        f.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,230);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\confirmAddC.png").getImage().getScaledInstance(385, 230, 4)));
        fundal.setOpaque(true);
        JButton ok=new JButton();
        ok.setBounds(165,150,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,146,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,150,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ok = 0;
                String[] s = new String[14];
                s[0] = nume.getText();
                s[1] = autor.getText();
                s[2] = "Disponibil";
                s[3] = puncte.getText();
                s[4] = categorie.getText();
                s[5] = tema.getText();
                s[6] = descriere.getText();
                s[7] = cuvinteCheie.getText();
                s[8] = nrPagini.getText();
                s[9] = raft.getText();
                s[10] = sertar.getText();
                s[11] = "0";
                if (puncteBonus.getText().length() > 0 && raritate.getText().length() > 0) {
                    ok = 1;
                    s[12] = puncteBonus.getText();
                    s[13] = raritate.getText();
                }
                String disp="";
                if(a.getListaCarti().get(n).isDisponibilitate())
                    disp+="Disponibil";
                else
                    disp+="Indisponibil";
                String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca";
                String username = "ivan";
                String password = "12345";
                String[][] d = null;
                try (
                        Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement();) {
                    String insertSql = "";
                    if (ok == 0) {
                        insertSql += "UPDATE Carti "+
                                "SET " + "Nume=" + "'" + s[0] + "'" + "," +"Autor="+"'" + s[1] + "'" +"," +"CartePuncte="+ "'" + s[3] + "'" + ","+"Categorie=" + "'" + s[4] + "'" + ","+"Tema=" + "'" + s[5] + "'" + ","+"Descriere=" + "'" + s[6] + "'" + ","+"CuvinteCheie=" + "'" + s[7] + "'" + ","+"NumarPagini="+"'" + s[8] + "'" + ","+"Raft=" + "'" + s[9] + "'" + ","+"Sertar=" + "'" + s[10] + "'"+" WHERE CarteId="+n;
                        a.adaugareCarteDate(new Carte(n, s[0], s[1],disp, Integer.parseInt(s[3]), s[4], s[5], s[6], s[7], Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]), a.getListaCarti().get(n).getData(), a.getListaCarti().get(n).getNumarImprumuturi()), n);
                    } else {
                        insertSql += "UPDATE Carti "+
                                "SET " + "Nume=" + "'" + s[0] + "'" + "," +"Autor="+"'" + s[1] + "'" +"," +"CartePuncte="+ "'" + s[3] + "'" + ","+"Categorie=" + "'" + s[4] + "'" + ","+"Tema=" + "'" + s[5] + "'" + ","+"Descriere=" + "'" + s[6] + "'" + ","+"CuvinteCheie=" + "'" + s[7] + "'" + ","+"NumarPagini="+"'" + s[8] + "'" + ","+"Raft=" + "'" + s[9] + "'" + ","+"Sertar=" + "'" + s[10] + "'"+ ","+"PuncteBonus="+ "'" + s[12] + "'" + ","+"Raritate=" + "'" + s[13] + "'"+" WHERE CarteId="+n;
                        a.adaugareCarteDate(new CarteSpeciala(n, s[0], s[1], disp, Integer.parseInt(s[3]), s[4], s[5], s[6], s[7], Integer.parseInt(s[8]), Integer.parseInt(s[9]), Integer.parseInt(s[10]), a.getListaCarti().get(n).getNumarImprumuturi(), Integer.parseInt(s[12]), Integer.parseInt(s[13]), a.getListaCarti().get(n).getData()),n);
                    }
                    statement.executeUpdate(insertSql);
                    fereastraConfirmareEditareSucces(a,"Cartea a fos editata   cu    succes!");
                } catch (SQLException q) {
                    System.out.print("Oopss");
                    q.printStackTrace();
                }
                f.dispose();
            }
        });
        JTextPane text=new JTextPane();
        text.setBounds(100,40,250,100);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        text.setText("Doriti sa salvati modificarile ?");
        text.setFont(new Font("Arial",Font.BOLD,20));
        ok.addMouseListener(flup);
        f.add(text);
        f.add(ok);
        f.add(fundal);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void fereastraConfirmareEditareSucces(Date info,String a)
    {
        JFrame f=new JFrame();
        f.setSize(400,250);                       // dimensiunea
        f.getContentPane().setBackground(Color.WHITE);        // culoarea de background
        f.setResizable(false);                             // sa nu fie marit
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT); // logoul afisat sus in colt mic
        f.setIconImage(icon);    // setarea logo ca icoana a aplicatiei
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // pentru a fi afisat in centrul ecranului
        f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2); // afisarea in centru ecranului
        JLabel fundal=new JLabel();
        fundal.setBounds(0,0,385,230);
        fundal.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\avertismentAddC.png").getImage().getScaledInstance(385, 230, 4)));
        fundal.setOpaque(true);
        JButton ok=new JButton();
        ok.setBounds(165,150,50,50);
        ok.setBorderPainted(false);
        ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
        ok.setOpaque(false);
        ok.setContentAreaFilled(false);
        MouseAdapter flup = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent m) {
                ok.setBounds(161,146,60,60);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok2.png").getImage().getScaledInstance(60, 60, 4)));
            }
            @Override
            public void mouseExited(MouseEvent m) {
                ok.setBounds(165,150,50,50);
                ok.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\ok1.png").getImage().getScaledInstance(50, 50, 4)));
            }
        };
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraAdministrator x=new FereastraAdministrator(info);
                f.dispose();
                window.dispose();
            }
        });
        JTextPane text=new JTextPane();
        text.setBounds(100,40,250,100);
        text.setForeground(Color.white);
        text.setOpaque(false);
        text.setEditable(false);
        text.setText(a);
        text.setFont(new Font("Arial",Font.BOLD,20));
        ok.addMouseListener(flup);
        f.add(text);
        f.add(ok);
        f.add(fundal);
        f.setLayout(null);
        f.setVisible(true);
    }
}
