package hk.edu.polyu.comp.comp2021.simple.control;

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
}
