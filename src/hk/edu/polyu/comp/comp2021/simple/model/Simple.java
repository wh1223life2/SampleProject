package hk.edu.polyu.comp.comp2021.simple.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Simple{

    boolean EOF = false;
    public Simple() throws InterpreterException {
        do{
            System.out.print(">");
            Scanner sc = new Scanner(System.in);
            String statement = sc.nextLine();
            if(statement.equals("quit")){
                EOF = true;
                sc.close();
            }
            if(!EOF)
                SampleController.Initial(statement);
        }while(!EOF);
        //SampleController.Testing();
    }

}
