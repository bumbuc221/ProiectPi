package p1;

import p3.Date;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class FereastraStart{
    private JFrame window;
    private JLabel img;
    public FereastraStart(Date info)
    {
        setImg();
        setWindow();
        window.add(img);
        window.setLayout(null);
        window.setVisible(true);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.dispose();
        FirstWindow x=new FirstWindow(info);
    }
    private void setWindow()
    {
        window=new JFrame();
        window.setSize(500,300);
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(true);
        window.getContentPane().setBackground(Color.WHITE);
        window.setResizable(false);
        Image icon = Toolkit.getDefaultToolkit().getImage("Imagini\\logo2.2.png").getScaledInstance(6000,6000,Image.SCALE_DEFAULT);
        window.setIconImage(icon);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
    }
    private void setImg()
    {
        img=new JLabel();
        img.setBounds(0,20,485,270);
        // imagine.setIcon(new ImageIcon("Imagini\\primaFereatra.jpg"));
        img.setIcon(new ImageIcon(new ImageIcon("Imagini\\\\primulLogo.jpg").getImage().getScaledInstance(485, 270, Image.SCALE_DEFAULT)));
        img.setBackground(Color.white);
        img.setOpaque(true);
    }
}
