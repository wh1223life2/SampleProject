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
        String expression = value.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String LHS = st.nextToken();
        String RHS = st.nextToken();
        int RHS_value = binExpr(RHS);
        intvar.put(LHS,RHS_value);

    }

    public static void print(Statement s){
        System.out.println(s.getExpression()+s.getLabel()+s.getOperationType());
    }

    public static void skip(Statement s){

    }

    public static boolean If(Statement s){
        return true;
    }

    public static boolean block(Statement s){
        if(If(s)) return true;

        return false;
    }

    public static void While(Statement s){
        while(block(s)){
            assign(s);
        }
    }



}


