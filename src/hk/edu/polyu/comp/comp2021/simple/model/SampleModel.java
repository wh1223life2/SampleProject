package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;
import jdk.dynalink.Operation;
import jdk.vm.ci.meta.ExceptionHandler;
import sun.security.mscapi.CPublicKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class SampleModel {

    public static HashMap<String, String> boolvar = new HashMap<>();
    public static HashMap<String, Integer> intvar = new HashMap<String, Integer>();

    /*
    检查expRef类型
    0，1，2，3对应int bool Variable expression 其余抛出
    未判断int上限问题（round to -99999 ~ 99999）
     */
    public static int checkRefType(String expRef) throws InterpreterException {
        try {
            Integer.parseInt(expRef);
            return 0;
        } catch (NumberFormatException e) {
            if("true".equalsIgnoreCase(expRef) || "false".equalsIgnoreCase(expRef))
                return 1;
            else if(boolvar.containsKey(expRef) || intvar.containsKey(expRef))
                return 2;
            else if(!SampleController.checkUnique(expRef))
                return 3;
            else ExceptionController.handleErr(expRef,ExceptionController.NOEXPTP);
        }
        return -1;
    }

    public static void varDefine(Statement statement) throws InterpreterException { //REQ1

        String expression = statement.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String type = st.nextToken();
        String name = st.nextToken();
        String variable = st.nextToken();

        Integer var = Integer.parseInt(variable);
        if(name != "int"){ExceptionController.handleErr(statement.getLabel(),ExceptionController.NOVARTP);}
        if(var %1 != 0){ExceptionController.handleErr(statement.getLabel(),ExceptionController.NOEXPTP);}

        intvar.put(name,var);

    }

    public static void binExpr(Statement s  /*,ArrayList<String> d*/) throws InterpreterException{ //REQ2

        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String exp1 = st.nextToken();
        String bop = st.nextToken();
        String exp2 = st.nextToken();
        String[] list = {"%","+","-","*","/",">",">=","<","<=","==","!="};
        for(int i = 0; i < list.length; i++){
            if(!bop.equals(list[i])){ExceptionController.handleErr(s.getLabel(),ExceptionController.SYNTAX);}
        }

        if(intvar.get(exp1) == null || intvar.get(exp2) == null){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTEXIST);}
        int value_exp1 = intvar.get(exp1);
        int value_exp2 = intvar.get(exp2);
        int value = 0;

        if("%".equals(bop)){value = value_exp1 % value_exp2;}
        if("+".equals(bop)){value = value_exp1 + value_exp2;}
        if("-".equals(bop)){value = value_exp1 - value_exp2;}
        if("*".equals(bop)){value = value_exp1 * value_exp2;}
        if("/".equals(bop)){value = value_exp1 / value_exp2;}
        intvar.put(s.getExpression(),value);

        /*
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

    public static void unExpr(Statement s) throws InterpreterException { //REQ3
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

    public static void assign(Statement s) throws InterpreterException{ //REQ4
        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String LHS = st.nextToken();
        String RHS = st.nextToken();

        if(intvar.get(RHS) == null){ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);}
        int RHS_value = intvar.get(RHS);

        intvar.put(LHS,RHS_value);

    }

    public static void print(Statement s){
        System.out.println(s.getExpression()+s.getLabel()+s.getOperationType());
    }

    public static void skip(Statement s){

    }

    public static void block(Statement s){ //REQ7

    }

    public static boolean block(Statement s){
        if(If(s)) return true;

    }

    public static void While(Statement s){
        while(block(s)){
            assign(s);
        }
    }



}


