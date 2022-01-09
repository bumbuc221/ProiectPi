package t1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import p2.Administrator;
import p2.Istoric;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Test1 {
    @Test
    public void test() {

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        Administrator x=new Administrator("5012242323","Mihai Ioan","mihai@gmail.com","XM234","12345");
        // action
        System.out.print(x);

        // assertion
        Assertions.assertEquals("Administrator{" +
                ", CNP='" + "5012242323" + '\'' +
                ", Nume='" + "Mihai Ioan" + '\'' +
                ", Email='" + "mihai@gmail.com" + '\'' +
                ", Serie='" + "XM234" + '\'' +
                '}', bos.toString(), "Afisare");

        // undo the binding in System
        System.setOut(originalOut);

    }
    @Test
    public void test2() {

        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));
        Istoric x=new Istoric("20/12/2020","10/01/2021","Ion");
           // action
        System.out.print(x);

        // assertion
        Assertions.assertEquals("Nume Carte: "+"Ion"+", "+
                "Data Imprumutari: "+"20/12/2020"+", "+
                "Data Aduceri: "+"10/01/2021"+"\n", bos.toString(), "Afisare");

        // undo the binding in System
        System.setOut(originalOut);
    }
    @Test
    public void test3()
    {
        Istoric x=new Istoric("20/12/2020","10/01/2021","Ion");
        Assertions.assertEquals("Ion",x.getC());
    }
    @Test
    public void test4()
    {
        Administrator x=new Administrator("5012242323","Mihai Ioan","mihai@gmail.com","XM234","12345");
        Assertions.assertEquals("Mihai Ioan",x.getNumeUtilizator());
    }
}
