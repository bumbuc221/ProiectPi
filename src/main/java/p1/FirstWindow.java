package p1;
import p2.*;
import p3.*;
import p4.FereastraConectareAdmin;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FirstWindow {
     private JFrame primu;
     private JButton butonUser,butonAdmin;
     private JLabel imagine;
     private UserWindow s;
    public FirstWindow(Date a) {
        primu=new JFrame("Biblioteca");
        caracteristiciFereastra();
        butonUser=new JButton();
        apasareButonUser(a);
        butonAdmin=new JButton();
        caracteristiciButoane();
        primu.add(butonAdmin);
        primu.add(butonUser);
        caracteristiciLabel();
        butonAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FereastraConectareAdmin x=new FereastraConectareAdmin(a,primu);
            }
        });
        primu.setLayout(null);
        primu.setVisible(true);
    }
    private void caracteristiciFereastra() {
        primu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        primu.setSize(600,500);
        primu.getContentPane().setBackground(Color.WHITE);
        primu.setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT);
        primu.setIconImage(icon);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        primu.setLocation(dim.width/2-primu.getSize().width/2, dim.height/2-primu.getSize().height/2);
    }
    private void caracteristiciLabel() {
        imagine=new JLabel();
        imagine.setBounds(0,0,585,470);
       // imagine.setIcon(new ImageIcon("Imagini\\primaFereatra.jpg"));
        imagine.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\primaFereatra.jpg").getImage().getScaledInstance(585, 470, Image.SCALE_DEFAULT)));
        imagine.setBackground(Color.white);
        imagine.setOpaque(true);
        primu.add(imagine);
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
    private void caracteristiciButoane() {
        butonUser.setText("Utilizator");
        butonAdmin.setText("Admin");
        butonUser.setBounds(200,280,200,35);
        butonAdmin.setBounds(480,430,100,25);
        butonAdmin.setBackground(new Color(255,240,0));
        setareCuloareActiune(butonAdmin);
        setareCuloareActiune(butonUser);
        butonAdmin.setForeground(Color.black);
        butonUser.setForeground(Color.black);
        butonAdmin.setFocusable(false);
        butonUser.setFocusable(false);
        butonUser.setBorder(new LineBorder(Color.black, 2));
        butonAdmin.setBorder(new LineBorder(Color.black, 2));
       // butonUser.setBorderPainted(true);
        butonAdmin.setFont(new Font(Font.DIALOG,  Font.BOLD, 18));
        butonUser.setFont(new Font(Font.DIALOG,  Font.BOLD, 25));
        butonUser.setBackground(new Color(255,240,0));
       // butonAdmin.setBorderPainted(false);
    }
    private void apasareButonUser(Date a) {
        butonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s=new UserWindow(a);
                primu.dispose();
            }
        });
    }


}
