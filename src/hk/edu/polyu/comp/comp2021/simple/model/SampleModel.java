package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SampleModel {

    public static HashMap<String, String> boolvar = new HashMap<>();
    public static HashMap<String, Integer> intvar = new HashMap<String, Integer>();

    /*
    表达式与expression命名规范 数字不开头 最多八位    需判断  未完成**
     */

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
    /*
    define注意区分int 与 var
    定义时如果当前为bool 也要查一下int里面有没有同名的  ****未完成
    不允许同名！即使是不同类型
     */

    public static void varDefine(Statement statement) throws InterpreterException { //REQ1 (not finished)

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

    public static int calculator(String bop, int value_exp1, int value_exp2){
        if(bop.equals("%")){return value_exp1 % value_exp2;}
        else if(bop.equals("+")){return value_exp1 + value_exp2;}
        else if(bop.equals("-")){return value_exp1 - value_exp2;}
        else if(bop.equals("*")){return value_exp1 * value_exp2;}
        else if(bop.equals("/")){return value_exp1 / value_exp2;}
        return 0;
    }

    public static boolean comparison(String bop, int value_exp1, int value_exp2){
        if(bop.equals(">")){return value_exp1 > value_exp2;}
        else if(bop.equals(">=")){return value_exp1 >= value_exp2;}
        else if(bop.equals("<")){return value_exp1 < value_exp2;}
        else if(bop.equals("<=")){return value_exp1 <= value_exp2;}
        else if(bop.equals("==")){return value_exp1 == value_exp2;}
        else if(bop.equals("!=")){return value_exp1 != value_exp2;}
        return false;
    }

    public static String binExpr(Statement s) throws InterpreterException{ //REQ2

        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String exp1 = st.nextToken();
        String bop = st.nextToken();
        String exp2 = st.nextToken();
        String[] list = {"%","+","-","*","/",">",">=","<","<=","==","!="};
        for(int i = 0; i < list.length; i++){
            if(!bop.equals(list[i])){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTBINOP);}
        }


        // calculation
        // ------------------------------------------------------------------------------------------------------------------------------------------------------
        // condition 1 : exp1 = int , exp2 = int
        if( bop.equals("%") || bop.equals("+") || bop.equals("-") || bop.equals("*") || bop.equals("/")){
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                Integer value_exp1 = Integer.parseInt(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(calculator(bop,value_exp1,value_exp2));
            }

            // condition 2 : exp1 = variable , exp2 = int
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 0){
                if(intvar.get(exp1) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(calculator(bop,value_exp1,value_exp2));
            }

            // condition 3 : exp1 = int, exp2 = variable
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                if(intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp2 = intvar.get(exp2);
                Integer value_exp1 = Integer.parseInt(exp1);
                return String.valueOf(calculator(bop,value_exp1,value_exp2));
            }

            // condition 4 : exp1 = variable , exp2 = variable
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 2){
                if(intvar.get(exp1) == null || intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                int value_exp2 = intvar.get(exp2);
                return String.valueOf(calculator(bop,value_exp1,value_exp2));
            }

            // condition 5 : exp1 or exp2 = bool
            if(checkRefType(exp1) == 1){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}
            if(checkRefType(exp2) == 1){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}


            // condition 6 : exp1 = expression, exp2 = int
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 0){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 0){
                        String value = binExpr(SampleController.findStatement(exp1));

                        Integer value_exp1 = Integer.parseInt(value);
                        Integer value_exp2 = Integer.parseInt(exp2);

                        return String.valueOf(calculator(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 7 : exp1 = int, exp2 = expression
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp2))) == 0){
                        String value = binExpr(SampleController.findStatement(exp2));

                        Integer value_exp1 = Integer.parseInt(exp1);
                        Integer value_exp2 = Integer.parseInt(value);

                        return String.valueOf(calculator(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 8 : exp1 = expression, exp2 = expression
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr") && SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 0 && checkRefType(binExpr(SampleController.findStatement(exp2))) == 0){
                        String value1 = binExpr(SampleController.findStatement(exp1));
                        String value2 = binExpr(SampleController.findStatement(exp2));

                        Integer value_exp1 = Integer.parseInt(value1);
                        Integer value_exp2 = Integer.parseInt(value2);

                        return String.valueOf(calculator(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }
        }

        // ------------------------------------------------------------------------------------------------------------------------------------------------------


        // comparison
        // ------------------------------------------------------------------------------------------------------------------------------------------------------
        if( bop.equals(">") || bop.equals(">=") || bop.equals("<") || bop.equals("<=") || bop.equals("==") || bop.equals("!=")){
            // condition 1 : exp1 = int , exp2 = int
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                Integer value_exp1 = Integer.parseInt(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(comparison(bop,value_exp1,value_exp2));
            }

            // condition 2 : exp1 = variable , exp2 = int
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 0){
                if(intvar.get(exp1) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(comparison(bop,value_exp1,value_exp2));
            }

            // condition 3 : exp1 = int, exp2 = variable
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                if(intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp2 = intvar.get(exp2);
                Integer value_exp1 = Integer.parseInt(exp1);
                return String.valueOf(comparison(bop,value_exp1,value_exp2));
            }

            // condition 4 : exp1 = variable , exp2 = variable
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 2){
                if(intvar.get(exp1) == null || intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                int value_exp2 = intvar.get(exp2);
                return String.valueOf(comparison(bop,value_exp1,value_exp2));
            }

            // condition 5 : exp1 or exp2 = bool
            if(checkRefType(exp1) == 1){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}
            if(checkRefType(exp2) == 1){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}


            // condition 6 : exp1 = expression, exp2 = int
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 0){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 1){
                        String value = binExpr(SampleController.findStatement(exp1));

                        Integer value_exp1 = Integer.parseInt(value);
                        Integer value_exp2 = Integer.parseInt(exp2);

                        return String.valueOf(comparison(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 7 : exp1 = int, exp2 = expression
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp2))) == 1){
                        String value = binExpr(SampleController.findStatement(exp2));

                        Integer value_exp1 = Integer.parseInt(exp1);
                        Integer value_exp2 = Integer.parseInt(value);

                        return String.valueOf(comparison(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 8 : exp1 = expression, exp2 = expression
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr") && SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 1 && checkRefType(binExpr(SampleController.findStatement(exp2))) == 1){
                        String value1 = binExpr(SampleController.findStatement(exp1));
                        String value2 = binExpr(SampleController.findStatement(exp2));

                        Integer value_exp1 = Integer.parseInt(value1);
                        Integer value_exp2 = Integer.parseInt(value2);

                        return String.valueOf(comparison(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }
        }
        // ------------------------------------------------------------------------------------------------------------------------------------------------------
        return "";

    }

    public static String unExpr(Statement s) throws InterpreterException { //REQ3
        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String a = st.nextToken();
        String b = st.nextToken();

        // condition 1 : pre_increment and pre_decrease
        if(a.equals("~") || a.equals("#")){
            // 1 : b is int
            if(checkRefType(b) == 0){
                Integer b_value = Integer.parseInt(b);
                return String.valueOf(b_value + 1);
            }
            // 2 : b is bool
            if(checkRefType(b) == 1){
                ExceptionController.handleErr(s.getLabel(),ExceptionController.SYNTAX);
            }
            // 3 : b is variable
            if(checkRefType(b) == 2){
                if(intvar.get(b) == null){ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);}
                int b_value = intvar.get(b);
                return String.valueOf(b_value + 1);
            }
            // 4 : b is expression
            if(checkRefType(b) == 3){
                if(SampleController.findStatement(b).equals("binexpr") ){
                    if(checkRefType(binExpr(SampleController.findStatement(b))) == 0){
                        String value = binExpr(SampleController.findStatement(b));
                        Integer b_value = Integer.parseInt(value);
                        return String.valueOf(b_value + 1);
                    }
                }
            }
        }

        // condition 2 : post_increment and post_decrease
        if(b.equals("~") || b.equals("#")){
            // 1 : b is int
            if(checkRefType(b) == 0){
                Integer b_value = Integer.parseInt(b);
                return String.valueOf(b_value - 1);
            }
            // 2 : b is bool
            if(checkRefType(b) == 1){
                ExceptionController.handleErr(s.getLabel(),ExceptionController.SYNTAX);
            }
            // 3 : b is variable
            if(checkRefType(b) == 2){
                if(intvar.get(b) == null){ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);}
                int b_value = intvar.get(b);
                return String.valueOf(b_value - 1);
            }
            // 4 : b is expression
            if(checkRefType(b) == 3){
                if(SampleController.findStatement(b).equals("binexpr") ){
                    if(checkRefType(binExpr(SampleController.findStatement(b))) == 0){
                        String value = binExpr(SampleController.findStatement(b));
                        Integer b_value = Integer.parseInt(value);
                        return String.valueOf(b_value - 1);
                    }
                }
            }
        }
        return "";
    }

    public static void assign(Statement s) throws InterpreterException{ //REQ4
        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String LHS = st.nextToken();
        String RHS = st.nextToken();


        //condition 1 : LHS is int (int = int / int = variable / int = bool / int = expression) which are all wrong
        if(checkRefType(LHS) == 0){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}

        //condition 2 : LHS is bool ( bool = int / bool = variable / bool = bool / bool = expression) which are all wrong
        if(checkRefType(LHS) == 1){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}

        //condition 3 : LHS is variable (variable = int / variable = expression / variable = bool / variable = variable)
        if(checkRefType(LHS) == 2){
            // 1 : variable = int
            if(checkRefType(RHS) == 0){
                Integer RHS_value = Integer.parseInt(RHS);
                intvar.put(LHS,RHS_value);
            }

            // 2 : variable = bool
            if(checkRefType(RHS) == 1 ){
                ExceptionController.handleErr(s.getLabel(),ExceptionController.SYNTAX);
            }

            // 3 : variable = variable
            if(checkRefType(RHS) == 2){
                if(intvar.get(RHS) == null){ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);}
                int RHS_value = intvar.get(RHS);
                intvar.put(LHS,RHS_value);
            }

            // 4 : variable = expression
            if(checkRefType(RHS) == 3){
                if(SampleController.findStatement(RHS).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(RHS))) == 0){
                        String value = binExpr(SampleController.findStatement(RHS));
                        Integer RHS_value = Integer.parseInt(value);
                        intvar.put(LHS,RHS_value);
                    }
                }
            }
        }

        // condition 4 : LHS is expression ( expression = int / expression = expression / expression = bool / expression = variable )
        if(checkRefType(LHS) == 3){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTVAR);}

    }

    public static void print(Statement s) throws InterpreterException {//REQ5
        String expression = s.getExpression();
        if(checkRefType(expression) == 0 || checkRefType(expression) == 1)
            System.out.println("["+expression+"] ");
        else if(checkRefType(expression) == 2)
        System.out.println("[" + (intvar.containsKey(expression) ? intvar.get(expression) : boolvar.get(expression)) + "] ");
        else {
            Statement S1 = SampleController.findStatement(expression);
            String type = S1.getOperationType();
            if(type == "binexpr") System.out.println("[" + binExpr(S1) + "] ");
            else System.out.println("[" + unExpr(S1) + "]");
        }
    }

    public static void skip(Statement s){//REQ6
        return;
    }

    public static void block(Statement s) throws InterpreterException {//REQ7
        String expression = s.getExpression();
        String[] labs = expression.split(" ");
        for(int i = 0;i < labs.length; i++){
            if(!SampleController.checkUnique(labs[i])){
                Statement S1 = SampleController.findStatement(labs[i]);
                String Type = S1.getOperationType();
                switch (Type){
                    case "vardef":
                        varDefine(S1);
                        break;
                    case "assign":
                        assign(S1);
                        break;
                    case "print":
                        print(S1);
                        break;
                    case "skip":
                        skip(S1);
                        break;
                    case "block":
                        block(S1);
                        break;
                    case "if":
                        if(S1);
                        break;
                    case "while":
                        while(S1);
                        break;
                    default:
                        ExceptionController.handleErr(labs[i],ExceptionController.UNDEFLABEL);
                }
            }
            else ExceptionController.handleErr(labs[i],ExceptionController.UNDEFLABEL);
        }
    }


}


