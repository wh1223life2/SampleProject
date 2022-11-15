package hk.edu.polyu.comp.comp2021.simple.control;

import hk.edu.polyu.comp.comp2021.simple.model.Statement;

import java.util.*;

public class SampleController {

    //public static HashMap<String,String> Labels = new HashMap<>();
    //public static HashMap<String,String> ExecuteType = new HashMap<>();
    public static HashSet<Statement> total = new HashSet<>();
    public static List<String> DeExecuteType = Arrays.asList("vardef", "binexpr", "unexpr", "assign", "print", "skip", "block", "if", "while", "program");
    public static List<String> RunExecuteType = Arrays.asList("execute", "list", "store", "load");

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
        /*else if (RunExecuteType.contains(temp2)) {

        }*/
        else {
            temp2 = currentSub.nextToken();
            ExceptionController.handleErr(temp2, ExceptionController.NOOPETP);
        }
    }

    //keep label unique
    public static boolean checkUnique(String newlabel) {
        for (Statement p : total)
            if (p.getLabel().equals(newlabel))
                return false;
        return true;
    }

    public static void Testing(){
        for (Statement p : total)
            System.out.println(p.getOperationType() + " " + p.getLabel() + " " +p.getExpression());
    }
}
