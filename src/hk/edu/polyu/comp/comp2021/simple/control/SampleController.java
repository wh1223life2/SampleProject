package hk.edu.polyu.comp.comp2021.simple.control;

import java.util.*;

public class SampleController {

    public static HashMap<String,String> Statments = new HashMap<>();
    public static HashMap<String,String> ExecuteType = new HashMap<>();
    public static List<String> DeExecuteType = Arrays.asList("vardef","binexpr","print","skip","if","assign","while","block");
    public static void Initial(String str) throws InterpreterException {
        StringTokenizer currentSub = new StringTokenizer(str," ");
        String temp1 = currentSub.nextToken();
        if(!DeExecuteType.contains(temp1)|| !currentSub.hasMoreTokens())
            ExceptionController.handleErr(ExceptionController.NOEXPTP);
        else {
            String temp2 = currentSub.nextToken();
            // execute/list not exists no need to judge
            if(Statments.containsKey(temp2))
                ExceptionController.handleErr(ExceptionController.DUPLABEL);

            if(temp1.equals("skip")){
                Statments.put(temp2,"");
                ExecuteType.put(temp2,"skip");
            }
            else {
                Statments.put(temp2,str.substring(temp1.length() + temp2.length() + 2));
                ExecuteType.put(temp2,temp1);
            }
        }
    }

    public static void exceute(){
        if 2 type == "block"
                Samplemodel
    }

    /*public static void Testing(){
        for(Map.Entry<String, String> entry: Statments.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }*/
}
