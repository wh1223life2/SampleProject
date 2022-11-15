package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import jdk.vm.ci.meta.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SampleModel {

    public static HashMap<String, String> boolvar = new HashMap<>();
    public static HashMap<String, Integer> intvar = new HashMap<String, Integer>();

    public static void varDefine(Statement statement) throws InterpreterException {

        String expression = statement.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String type = st.nextToken();
        String name = st.nextToken();
        String variable = st.nextToken();

        Integer var = Integer.parseInt(variable);
        if(name != "int"){ExceptionController.handleErr(expression,ExceptionController.NOVARTP);}
        if(var %1 != 0){ExceptionController.handleErr(expression,ExceptionController.NOEXPTP);}

        intvar.put(name,var);


       /* Statment = "float x 100"
                string1 ="float" string2 ="x" string3/int = "100";
                Hashmap<String,int> INT
                        INT.put("x",100);
                if(string1 != && string!= )
                    ExceptionHandle.handleErr(ExceptionHandle.NOVARTP);

         */

    }

    public static void binExpr(Statement s,ArrayList<String> d) throws InterpreterException{
        d.add("==");
        d.add(">=");
        d.add("<=");
        d.add(">");
        d.add("<");
        d.add("!=");
        d.add("&&");
        d.add("||");

        if(s==null){
            System.out.println("Nothing expected");
        }

        int b=0;
        Character a=null;
        for(int i=0;i<s.getExpression().length();i++){
            if(s.getExpression().charAt(i)<63){
                a = s.getExpression().charAt(i);
                b = i;
            }
        }
        String result = s.getExpression().substring(b+1);
        String another_result = "";
        for(String h:d){
            if(h==a.toString()){
                another_result=h;
            }
        }
        boolvar.put(another_result,result);

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
        String expression = value.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String LHS = st.nextToken();
        String RHS = st.nextToken();


    }

    public static void print(Statement s){
        System.out.println(s.getExpression()+s.getLabel()+s.getOperationType());
    }




}


