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

        String part1_type = list[0];
        String part2_name = list[1];
        String part3 = list[2];

        Integer part3_int = Integer.parseInt(part3);
        if(part3_int % 1 != 0){
            ExceptionController.handleErr(ExceptionController.SYNTAX);
        }
        if(part2_name != "int"){
            ExceptionController.handleErr(ExceptionController.NOTVAR);
        }
        intvar.put(part2_name,part3_int);


       /* Statment = "float x 100"
                string1 ="float" string2 ="x" string3/int = "100";
                Hashmap<String,int> INT
                        INT.put("x",100);
                if(string1 != && string!= )
                    ExceptionHandle.handleErr(ExceptionHandle.NOVARTP);*/

    }

    public static void binExpr(Statement s) throws InterpreterException{
        if(s==null){
            System.out.println("Nothing expected");
        }
        int b=0;
        for(int i=0;i<s.getExpression().length();i++){
            if(s.getExpression().charAt(i)<80){
                Character a = s.getExpression().charAt(i);
                b = i;
            }
        }

        //int a=s.getExpression().indexOf(s.getLabel());
        System.out.println(s.getExpression().substring(0,a)+s.getLabel()+s.getExpression().substring(a));

       /* if(Varname.type == int)
            handle-》Varcontent
                else ...
        return Varname,Varcontent; (pair)*/
    }

    public static void unExpr(Statement s) throws InterpreterException {
        int n = s.getExpression().indexOf("+");
        int k = s.getExpression().indexOf("-");
        if(k>0){
            Integer a = Integer.parseInt(s.getExpression().substring(0,k))-Integer.parseInt(s.getExpression().substring(k+1));
            intvar.put("-",a);
        }
        if(n>0){
            Integer a = Integer.parseInt(s.getExpression().substring(0,k))+Integer.parseInt(s.getExpression().substring(k+1));
            intvar.put("+",a);
        }

        /*if(Varname.type == int)
        handle-》Varcontent
                else ...
        return Varname,Varcontent; (pair)*/
    }

    public static void assign(Statement value) throws InterpreterException{
        String s = value.getExpression();
        String[] s_list = s.split(" ");

        String[] list = {};
        int index = 0;
        for(String name: s_list){
            list[index++] = name;
        }

        String part_1 = list[0];
        String part_2 = list[1];

        Integer number = Integer.parseInt(part_2);
        if(number % 1 != 0){
            ExceptionController.handleErr(ExceptionController.SYNTAX);
        }
        intvar.put(part_1,number);
    }

    public static void print(Statement s){
        System.out.println(s.getExpression()+s.getLabel()+s.getOperationType());
    }

}


