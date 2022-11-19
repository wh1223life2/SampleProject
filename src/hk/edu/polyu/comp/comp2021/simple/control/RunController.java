package hk.edu.polyu.comp.comp2021.simple.control;

import hk.edu.polyu.comp.comp2021.simple.model.SampleModel;
import hk.edu.polyu.comp.comp2021.simple.model.Statement;

import java.util.HashSet;
import java.util.StringTokenizer;

public class RunController {
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
    public static void execute(String programname) throws InterpreterException {//REQ11

        if(!SampleController.Program.containsKey(programname))
            ExceptionController.handleErr(programname,ExceptionController.NOPRONAME);
        else {
            Statement currentS = SampleController.findStatementTotal(SampleController.Program.get(programname));
            SampleModel.currentblock.add(currentS);
            SampleModel.ExecuteStatement(SampleController.Program.get(programname));
        }
    }
}
