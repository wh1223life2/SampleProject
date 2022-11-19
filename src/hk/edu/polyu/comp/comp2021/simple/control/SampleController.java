package hk.edu.polyu.comp.comp2021.simple.control;

import hk.edu.polyu.comp.comp2021.simple.model.SampleModel;
import hk.edu.polyu.comp.comp2021.simple.model.Statement;

import java.util.*;

public class SampleController {

    //public static HashMap<String,String> Labels = new HashMap<>();
    //public static HashMap<String,String> ExecuteType = new HashMap<>();
    public static HashSet<Statement> total = new HashSet<>();
    public static HashMap<String,String> Program = new HashMap<>();
    public static List<String> DeExecuteType = Arrays.asList("vardef", "binexpr", "unexpr", "assign", "print", "skip", "block", "if", "while");
    public static List<String> RunExecuteType = Arrays.asList("program","execute", "list", "store", "load");

    public static void Initial(String str) throws InterpreterException {
        StringTokenizer currentSub = new StringTokenizer(str, " ");
        String temp1 = currentSub.nextToken();
        String temp2 = "";
        if (!currentSub.hasMoreTokens())
            ExceptionController.handleErr("unknown Statement", ExceptionController.SYNTAX);
        else if (DeExecuteType.contains(temp1)) {
            ExceptionController.checkLegal(temp1,str.substring(temp1.length()+1));
            temp2 = currentSub.nextToken();
            total.add(new Statement(temp1,temp2,str.substring(temp1.length() + temp2.length() + 2)));
        }
        else if (RunExecuteType.contains(temp1)) {
            temp2 = str.substring(temp1.length()+1);
            ExceptionController.checkLegalRun(temp1,temp2);
            switch (temp1){
                case "program":
                  //  System.out.println(temp1+ ""+temp2);
                    RunController.program(temp2);
                    break;
                case"execute":
                  //  System.out.println(temp2+ " "+Program.get(temp2));
                    SampleModel.currentblock.clear();
                    SampleModel.intvar.clear();
                    SampleModel.boolvar.clear();
                    RunController.execute(temp2);
                    break;
                    //剩下12-14；
            }
        }
        else {
            temp2 = currentSub.nextToken();
            ExceptionController.handleErr(temp2, ExceptionController.NOOPETP);
        }
    }

    //keep label unique
    public static boolean checkUniqueTotal(String newlabel) {
        for (Statement p : total)
            if (p.getLabel().equals(newlabel))
                return false;
        return true;
    }
    public static boolean checkUnique(String newlabel) {
        for (Statement p : SampleModel.currentblock)
            if (p.getLabel().equals(newlabel))
                return false;
        return true;
    }
    public static Statement findStatement(String newlabel) throws InterpreterException {
        for (Statement p : SampleModel.currentblock)
            if (p.getLabel().equals(newlabel))
                return p;
        ExceptionController.handleErr(newlabel,ExceptionController.UNDEFLABEL);
        return new Statement("","","");
    }

    public static Statement findStatementTotal(String newlabel) throws InterpreterException {
        for (Statement p : total)
            if (p.getLabel().equals(newlabel))
                return p;
        ExceptionController.handleErr(newlabel,ExceptionController.UNDEFLABEL);
        return new Statement("","","");
    }
}
