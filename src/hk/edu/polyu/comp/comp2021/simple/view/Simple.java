package hk.edu.polyu.comp.comp2021.simple.view;

import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;

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
