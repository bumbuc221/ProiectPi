package p3;

import p2.Administrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Actualizare {

    public Actualizare() {
        if (!exist()) {
            write(java.time.LocalDate.now() + "");
            actualizare();
        } else {
            if (!cit().equals(java.time.LocalDate.now() + "")) {
                write(java.time.LocalDate.now() + "");
                actualizare();
            }
        }
    }

    private String cit() {
        String data = "";
        try {
            File myObj = new File("dataCurenta.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }

    private void write(String s) {
        try {
            FileWriter myWriter = new FileWriter("dataCurenta.txt");
            myWriter.write(s);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private boolean exist() {
        File obj = new File("dataCurenta.txt");
        if (obj.exists())
            return true;
        return false;
    }

    private void actualizare() {
        ResultSet r = null;
        String url = "jdbc:sqlserver://koki\\sqlexpress;databaseName=Biblioteca"; //url
        String username = "ivan"; // nume
        String password = "12345"; // parola
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement();) { // realizarea conexiuni
            String updateSql = "UPDATE Utilizatori SET Suspendat=NULL , DataDisponibilaLaImprumut=NULL where Suspendat is not null and DATEDIFF(day, DataDisponibilaLaImprumut, GETDATE())>=0";   // selectarea cartilor
            String updateSuspenadari = "DELETE FROM Suspendari WHERE DATEDIFF(day, DataAcces, GETDATE())>=0";
            statement.executeUpdate(updateSql);
            statement.executeUpdate(updateSuspenadari);
        } catch (SQLException e) {
            System.out.print("Conexiune esuata la baza de date!");
            e.printStackTrace();
        }
    }

    private boolean diferenta(String a, String b) {
        long days_difference = 0;
        SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date1 = obj.parse(a);
            java.util.Date date2 = obj.parse(b);
            long time_difference = date2.getTime() - date1.getTime();
            days_difference = (time_difference / (1000 * 60 * 60 * 24)) % 365;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (days_difference >= 0)
            return true;
        return false;
    }
}
