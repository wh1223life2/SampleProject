package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import jdk.vm.ci.meta.ExceptionHandler;

import java.util.HashMap;

public class SampleModel {

    public static HashMap<String, String> boolvar = new HashMap<>();
    public static HashMap<String, Integer> intvar = new HashMap<String, Integer>();
    public static void varDefine(Statement statement) throws InterpreterException {

        String expression = statement.getExpression();
        String[] expression_list = expression.split(" ");

        String[] list = {};
        int index = 0;
        for(String part : expression_list){
            list[index++] = part;
        }

        String part1_name = list[0];
        String part2_type = list[1];
        String part3 = list[2];

        Integer part3_int = Integer.parseInt(part3);
        if(part3_int % 1 == 0){
            ExceptionController.handleErr(ExceptionController.SYNTAX);
        }
        if(part2_type != "int"){
            ExceptionController.handleErr(ExceptionController.NOTVAR);
        }
        intvar.put(part1_name,part3_int);



       /* Statment = "float x 100"
                string1 ="float" string2 ="x" string3/int = "100";
                Hashmap<String,int> INT
                        INT.put("x",100);
                if(string1 != && string!= )
                    ExceptionHandle.handleErr(ExceptionHandle.NOVARTP);*/

    }

    public static void binExpr(Statement ?){


       /* if(Varname.type == int)
            handle-》Varcontent
                else ...
        return Varname,Varcontent; (pair)*/
    }

    public static void unExpr(Statement ?){


        /*if(Varname.type == int)
        handle-》Varcontent
                else ...
        return Varname,Varcontent; (pair)*/
    }

}


