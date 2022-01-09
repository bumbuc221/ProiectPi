package p1;
import p2.*;
import p3.*;
import p3.Date;
import p4.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Actualizare x=new Actualizare();
        Date a=new Date();
        FereastraStart b=new FereastraStart(a);
        UtilizatorNormal g=new UtilizatorNormal("23324222342","Ion Mihai","ion@gmail.com",
                "xm2342","20/232/232",0,0,false,null,new ArrayList<>());

    }
}
