package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;

import java.util.HashMap;

public class SampleModel {

    public static HashMap<String, String> boolvar = new HashMap<>();
    public static HashMap<String, Integer> intvar = new HashMap<String, Integer>();
    public static void varDefine(Statement ?) throws InterpreterException {


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


