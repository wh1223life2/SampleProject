package hk.edu.polyu.comp.comp2021.simple.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.StringTokenizer;

/**
 * The type Run controller.
 */
public class RunController {
    /**
     * Program.
     *
     * @param rawstring the rawstring
     * @throws InterpreterException the interpreter exception
     */
    public static void program(String rawstring) throws InterpreterException {//REQ10
        StringTokenizer st = new StringTokenizer(rawstring," ");

        String programname = st.nextToken();
        String label = st.nextToken();
        if(SampleController.Program.containsKey(programname))
            ExceptionController.handleErr(programname,ExceptionController.DUPNAME);
        else {
           // System.out.println(programname+ " "+label);
            SampleController.Program.put(programname,label);
        }
    }

    /**
     * Execute.
     *
     * @param programname the programname
     * @throws InterpreterException the interpreter exception
     */
    public static void execute(String programname) throws InterpreterException {//REQ11

        SampleModel.currentblock.clear();
        SampleModel.intvar.clear();
        SampleModel.boolvar.clear();

        if(!SampleController.Program.containsKey(programname))
            ExceptionController.handleErr(programname,ExceptionController.NOPRONAME);
        else {
            Statement currentS = SampleController.findStatementTotal(SampleController.Program.get(programname));
            SampleModel.currentblock.add(currentS);
            SampleModel.ExecuteStatement(SampleController.Program.get(programname));
        }
    }

    /**
     * List.
     *
     * @param programname the programname
     * @throws InterpreterException the interpreter exception
     */
    public static void list(String programname) throws InterpreterException {//REQ12

        SampleModel.currentblock.clear();
        SampleModel.intvar.clear();
        SampleModel.boolvar.clear();

        if(!SampleController.Program.containsKey(programname))
            ExceptionController.handleErr(programname,ExceptionController.NOPRONAME);
        else {
            Statement currentS = SampleController.findStatementTotal(SampleController.Program.get(programname));
            SampleModel.currentblock.add(currentS);
            if(currentS.getOperationType().equals("block")){
                String expression = currentS.getExpression();
                String[] labs = expression.split(" ");
                for(int i = 0;i < labs.length; i++)
                {
                    Statement blockstatement = SampleController.findStatementTotal(labs[i]);
                    SampleModel.currentblock.add(blockstatement);
                }
            }
            for(Statement tempstr: SampleModel.currentblock)
                System.out.println(tempstr.getOperationType()+" "+tempstr.getLabel()+" "+tempstr.getExpression());
        }
    }


    /**
     * Store.
     *
     * @param rawstring the rawstring
     */
    public static void store(String rawstring) {//REQ13

        StringTokenizer st = new StringTokenizer(rawstring," ");

        String programname = st.nextToken();
        String filepath = st.nextToken();

        try {
            File f=new File(filepath);
            FileWriter fw;
            fw=new FileWriter(f);
            if(SampleController.Program.containsKey(programname))
                fw.write(SampleController.Program.get(programname));
            else ExceptionController.handleErr(programname,ExceptionController.NOPRONAME);
            fw.close();
            } catch (IOException e) { e.printStackTrace(); } catch (InterpreterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load.
     *
     * @param rawstring the rawstring
     * @throws InterpreterException the interpreter exception
     */
    public static void load (String rawstring) throws InterpreterException {
        StringTokenizer st = new StringTokenizer(rawstring," ");

        String filepath = st.nextToken();
        String programname = st.nextToken();
        if(SampleController.Program.containsKey(programname))
            ExceptionController.handleErr(programname,ExceptionController.DUPNAME);
        else {
            try {
                String newlabel = new String(Files.readAllBytes(Paths.get(filepath)));
                SampleController.findStatementTotal(newlabel);
                SampleController.Program.put(programname,newlabel);
                //System.out.println(SampleController.Program.get(programname));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
