package hk.edu.polyu.comp.comp2021.simple.view;

import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;

import java.util.Scanner;

/**
 * The type Simple.
 */
public class Simple{

    private boolean EOF = false;

    /**
     * Instantiates a new Simple.
     *
     * @throws InterpreterException the interpreter exception
     */
    public Simple() throws InterpreterException {
        do{
            System.out.print(">");
            Scanner sc = new Scanner(System.in);
            String statement = sc.nextLine();
            if(statement.equals("quit")){//req15
                EOF = true;
                sc.close();
            }
            if(!EOF)
                SampleController.Initial(statement);
        }while(!EOF);
        //SampleController.Testing();
    }


}
