package hk.edu.polyu.comp.comp2021.simple.control;

import hk.edu.polyu.comp.comp2021.simple.model.Statement;

import java.util.*;

public class SampleController {

    //public static HashMap<String,String> Labels = new HashMap<>();
    //public static HashMap<String,String> ExecuteType = new HashMap<>();
    public static HashSet<Statement> total = new HashSet<>();
    public static List<String> DeExecuteType = Arrays.asList("vardef","binexpr","unexpr","assign","print","skip","block","if","while","program");
    public static List<String> RunExecuteType = Arrays.asList("execute","list","store","load");
    public static void Initial(String str) throws InterpreterException {
        StringTokenizer currentSub = new StringTokenizer(str," ");
        String temp1 = currentSub.nextToken();
        if(!(DeExecuteType.contains(temp1) || RunExecuteType.contains(temp1)))
            ExceptionController.handleErr(ExceptionController.NOEXPTP);
        else if(!currentSub.hasMoreTokens())
            ExceptionController.handleErr(ExceptionController.SYNTAX);
        else {
            String temp2 = currentSub.nextToken();
            if (DeExecuteType.contains(temp1)) {
                if(temp1.equals("skip"))
                    total.add(new Statement("skip",temp2,""));
                else total.add(new Statement(temp1,temp2,str.substring(temp1.length() + temp2.length() + 2)));
            }
            else {
                /*wait to be completed  temp2 belong to Run */
            }
            /*if(Statments.containsKey(temp2))
                ExceptionController.handleErr(ExceptionController.DUPLABEL);*/
        }
    }

    public static boolean checkUnique(String newlabel){//keep label unique
        for(Statement p :total)
            if(p.getLabel().equals(newlabel))
                return false;
        return true;
}
