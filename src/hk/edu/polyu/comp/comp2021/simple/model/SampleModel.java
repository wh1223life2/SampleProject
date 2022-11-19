package hk.edu.polyu.comp.comp2021.simple.model;


import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;

import java.util.*;

/**
 * The type Sample model.
 */
public class SampleModel {

    /**
     * The Boolvar.
     */
    public static HashMap<String, Boolean> boolvar = new HashMap<>();
    /**
     * The Intvar.
     */
    public static HashMap<String, Integer> intvar = new HashMap<>();

    /**
     * The Currentblock.
     */
    public static HashSet<Statement> currentblock = new HashSet<>();


    /**
     * Check ref type int.
     *
     * @param expRef the exp ref
     * @return the int
     * @throws InterpreterException the interpreter exception
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

    /**
     * Execute statement.
     *
     * @param newlabel the newlabel
     * @throws InterpreterException the interpreter exception
     */
    public static void ExecuteStatement(String newlabel) throws InterpreterException {
        if(!SampleController.checkUnique(newlabel)){
            Statement S1 = SampleController.findStatement(newlabel);
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
                    ifOperation(S1);
                    break;
                case "while":
                    whileOperation(S1);
                    break;
                case "binexpr":
                case "unexpr":
                    break;
                default:
                    ExceptionController.handleErr(newlabel,ExceptionController.UNDEFLABEL);
            }
        }
        else ExceptionController.handleErr(newlabel,ExceptionController.UNDEFLABEL);
    }
    /*
    define注意区分int 与 var
    定义时如果当前为bool 也要查一下int里面有没有同名的  ****未完成
    不允许同名！即使是不同类型
     */

    /**
     * Var define.
     *
     * @param statement the statement
     * @throws InterpreterException the interpreter exception
     */
    public static void varDefine(Statement statement) throws InterpreterException { //REQ1 名字限制未达成

        String expression = statement.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String type = st.nextToken();
        String varname = st.nextToken();
        String value = st.nextToken();
        //System.out.println(type);
        if(intvar.containsKey(varname) || boolvar.containsKey(varname))
            ExceptionController.handleErr(statement.getLabel(),ExceptionController.DUPVARNAME);
        if(type.equals("int")){
            if(checkRefType(value) == 0)
                intvar.put(varname,Integer.parseInt(value));
            else if(checkRefType(value) == 1)
                ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
            else if(checkRefType(value) == 2){
                if(intvar.containsKey(value))
                    intvar.put(varname,intvar.get(value));
                else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
            }
            else {
                Statement expStatement = SampleController.findStatement(value);
                String expType = expStatement.getOperationType();
                if(expType.equals("binexpr")){
                    if(checkRefType(binExpr(expStatement)) == 0)
                        intvar.put(varname,Integer.parseInt(binExpr(expStatement)));
                    else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
                }
                else if(expType.equals("unexpr")){
                    if(checkRefType(unExpr(expStatement)) == 0)
                        intvar.put(varname,Integer.parseInt(unExpr(expStatement)));
                    else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
                }
                else ExceptionController.handleErr(statement.getLabel(), ExceptionController.EXPTPWRONG);
            }
        }
        else if(type.equals("bool")){
            if(checkRefType(value) == 1)
                boolvar.put(varname,Boolean.valueOf(value));
            else if(checkRefType(value) == 0)
                ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
            else if(checkRefType(value) == 2){
                if(boolvar.containsKey(value))
                    boolvar.put(varname,boolvar.get(value));
                else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
            }
            else {
                Statement expStatement = SampleController.findStatement(value);
                String expType = expStatement.getOperationType();
                if(expType.equals("binexpr")){
                    if(checkRefType(binExpr(expStatement)) == 1)
                        boolvar.put(varname,Boolean.valueOf(binExpr(expStatement)));
                    else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
                }
                else if(expType.equals("unexpr")){
                    if(checkRefType(unExpr(expStatement)) == 1)
                        boolvar.put(varname,Boolean.valueOf(unExpr(expStatement)));
                    else ExceptionController.handleErr(statement.getLabel(),ExceptionController.EXPTPWRONG);
                }
                else ExceptionController.handleErr(statement.getLabel(), ExceptionController.EXPTPWRONG);
            }
        }
        else ExceptionController.handleErr(statement.getLabel(),ExceptionController.NOVARTP);
    }

    /**
     * Calculator int.
     *
     * @param s          the s
     * @param bop        the bop
     * @param value_exp1 the value exp 1
     * @param value_exp2 the value exp 2
     * @return the int
     * @throws InterpreterException the interpreter exception
     */
    public static int calculator(Statement s,String bop, int value_exp1, int value_exp2) throws InterpreterException{
        if(bop.equals("%")){return value_exp1 % value_exp2;}
        else if(bop.equals("+")){return value_exp1 + value_exp2;}
        else if(bop.equals("-")){return value_exp1 - value_exp2;}
        else if(bop.equals("*")){return value_exp1 * value_exp2;}
        else if(bop.equals("/")){
            if(value_exp2 == 0)ExceptionController.handleErr(s.getLabel(),ExceptionController.DIVBYZERO);
            return value_exp1 / value_exp2;}
        return 0;
    }

    /**
     * Comparison boolean.
     *
     * @param bop        the bop
     * @param value_exp1 the value exp 1
     * @param value_exp2 the value exp 2
     * @return the boolean
     */
    public static boolean comparison(String bop, int value_exp1, int value_exp2){
        if(bop.equals(">")){return value_exp1 > value_exp2;}
        else if(bop.equals(">=")){return value_exp1 >= value_exp2;}
        else if(bop.equals("<")){return value_exp1 < value_exp2;}
        else if(bop.equals("<=")){return value_exp1 <= value_exp2;}
        else if(bop.equals("==")){return value_exp1 == value_exp2;}
        else if(bop.equals("!=")){return value_exp1 != value_exp2;}
        return false;
    }

    /**
     * Bin expr string.
     *
     * @param s the s
     * @return the string
     * @throws InterpreterException the interpreter exception
     */
    public static String binExpr(Statement s) throws InterpreterException{ //REQ2

        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String exp1 = st.nextToken();
        String bop = st.nextToken();
        String exp2 = st.nextToken();
        List<String> bops = Arrays.asList("%","+","-","*","/",">",">=","<","<=","==","!=","&&","||");
        if(!bops.contains(bop)){ExceptionController.handleErr(s.getLabel(),ExceptionController.NOTBINOP);}


        // calculation
        // ------------------------------------------------------------------------------------------------------------------------------------------------------
        // condition 1 : exp1 = int , exp2 = int
        if( bop.equals("%") || bop.equals("+") || bop.equals("-") || bop.equals("*") || bop.equals("/")){
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                Integer value_exp1 = Integer.parseInt(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
            }

            // condition 2 : exp1 = variable , exp2 = int
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 0){
                if(intvar.get(exp1) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                Integer value_exp2 = Integer.parseInt(exp2);
                return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
            }

            // condition 3 : exp1 = int, exp2 = variable
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 0){
                if(intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp2 = intvar.get(exp2);
                Integer value_exp1 = Integer.parseInt(exp1);
                return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
            }

            // condition 4 : exp1 = variable , exp2 = variable
            if(checkRefType(exp1) == 2 && checkRefType(exp2) == 2){
                if(intvar.get(exp1) == null || intvar.get(exp2) == null)ExceptionController.handleErr(s.getLabel(),ExceptionController.UNDEFINEDVAR);
                int value_exp1 = intvar.get(exp1);
                int value_exp2 = intvar.get(exp2);
                return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
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

                        return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
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

                        return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
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

                        return String.valueOf(calculator(s,bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }
        }

        // ------------------------------------------------------------------------------------------------------------------------------------------------------


        // comparison
        // ------------------------------------------------------------------------------------------------------------------------------------------------------
        if( bop.equals(">") || bop.equals(">=") || bop.equals("<") || bop.equals("<=") || bop.equals("==") || bop.equals("!=") || bop.equals("&&") || bop.equals("||")){
            // condition 0 : bop is && or ||, in this condition exp1 and exp2 must be bool type
            // bop is &&
            if(bop.equals("&&")) {
                // 1. exp1 = bool, exp2 = bool/expression
                if (checkRefType(exp1) == 1 && (checkRefType(exp2) == 1 || checkRefType(exp2) == 3)) {
                    if (checkRefType(exp2) == 1) { // exp2 = bool
                        if (exp1.equals("true") && exp2.equals("true")) {
                            return String.valueOf(true);
                        } else return String.valueOf(false);
                    }
                    if (checkRefType(exp2) == 3) { // exp2 = expression
                        if (SampleController.findStatement(exp2).getOperationType().equals("binexpr")) {
                            if (checkRefType(binExpr(SampleController.findStatement(exp2))) == 1) {
                                if (exp1.equals("true") && binExpr(SampleController.findStatement(exp2)).equals("true")) {
                                    return String.valueOf(true);
                                } else return String.valueOf(false);
                            }
                        }
                    }
                }
                // 2. exp1 = expression, exp2 = bool/expression
                if (checkRefType(exp1) == 3 && (checkRefType(exp2) == 1 || checkRefType(exp2) == 3)) {
                    if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                        if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 1){
                            if (checkRefType(exp2) == 1) { // exp2 = bool
                                if (binExpr(SampleController.findStatement(exp1)).equals("true") && exp2.equals("true")) {
                                    return String.valueOf(true);
                                } else return String.valueOf(false);
                            }
                            if (checkRefType(exp2) == 3) { // exp2 = expression
                                if (SampleController.findStatement(exp2).getOperationType().equals("binexpr")) {
                                    if (checkRefType(binExpr(SampleController.findStatement(exp2))) == 1) {
                                        if (binExpr(SampleController.findStatement(exp1)).equals("true") && binExpr(SampleController.findStatement(exp2)).equals("true")) {
                                            return String.valueOf(true);
                                        } else return String.valueOf(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }



            // bop is ||
            if(bop.equals("||")){
                // 1. exp1 = bool, exp2 = bool/expression
                if (checkRefType(exp1) == 1 && (checkRefType(exp2) == 1 || checkRefType(exp2) == 3)) {
                    if (checkRefType(exp2) == 1) { // exp2 = bool
                        if ((exp1.equals("false") && exp2.equals("true")) || (exp1.equals("true") && exp2.equals("false"))) {
                            return String.valueOf(true);
                        } else return String.valueOf(false);
                    }
                    if (checkRefType(exp2) == 3) { // exp2 = expression
                        if (SampleController.findStatement(exp2).getOperationType().equals("binexpr")) {
                            if (checkRefType(binExpr(SampleController.findStatement(exp2))) == 1) {

                                String value_exp2 = binExpr(SampleController.findStatement(exp2));

                                if ((exp1.equals("true") && value_exp2.equals("false")) || (exp1.equals("false") && value_exp2.equals("true"))) {
                                    return String.valueOf(true);
                                } else return String.valueOf(false);
                            }
                        }
                    }
                }
                // 2. exp1 = expression, exp2 = bool/expression
                if (checkRefType(exp1) == 3 && (checkRefType(exp2) == 1 || checkRefType(exp2) == 3)) {
                    if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                        if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 1){

                            String value_exp1 = binExpr(SampleController.findStatement(exp1));

                            if (checkRefType(exp2) == 1) { // exp2 = bool
                                if ((value_exp1.equals("false") && exp2.equals("true")) || ((value_exp1).equals("true") && (exp2).equals("false"))) {
                                    return String.valueOf(true);
                                } else return String.valueOf(false);
                            }
                            if (checkRefType(exp2) == 3) { // exp2 = expression
                                if (SampleController.findStatement(exp2).getOperationType().equals("binexpr")) {
                                    if (checkRefType(binExpr(SampleController.findStatement(exp2))) == 1) {

                                        String value_exp2 = binExpr(SampleController.findStatement(exp2));

                                        if ((value_exp1.equals("true") && value_exp2.equals("false")) || (value_exp1.equals("false") && value_exp2.equals("true"))) {
                                            return String.valueOf(true);
                                        } else return String.valueOf(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
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

            // condition 5: exp1 = bool , exp2 = expression
            if(checkRefType(exp1) == 1 && checkRefType(exp2) == 3) {ExceptionController.handleErr(s.getLabel(),ExceptionController.SYNTAX);}

            // condition 6 : exp1 = expression, exp2 = bool
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 1){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 1){

                        String value_exp1 = binExpr(SampleController.findStatement(exp1));
                        String value_exp2 = exp2;
                        if(bop.equals("==")){
                            return String.valueOf(value_exp1.equals(value_exp2));
                        }
                        if(bop.equals("!=")){
                            return String.valueOf(!value_exp1.equals(value_exp2));
                        }
                    }
                }else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }


            // condition 7 : exp1 = expression, exp2 = int
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 0){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 0){
                        String value = binExpr(SampleController.findStatement(exp1));

                        Integer value_exp1 = Integer.parseInt(value);
                        Integer value_exp2 = Integer.parseInt(exp2);

                        return String.valueOf(comparison(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 8 : exp1 = int, exp2 = expression
            if(checkRefType(exp1) == 0 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp2))) == 0){
                        String value = binExpr(SampleController.findStatement(exp2));

                        Integer value_exp1 = Integer.parseInt(exp1);
                        Integer value_exp2 = Integer.parseInt(value);

                        return String.valueOf(comparison(bop,value_exp1,value_exp2));
                    }
                }
                else ExceptionController.handleErr(s.getLabel(),ExceptionController.NOOPETP);
            }

            // condition 9 : exp1 = expression, exp2 = expression
            if(checkRefType(exp1) == 3 && checkRefType(exp2) == 3){
                if(SampleController.findStatement(exp1).getOperationType().equals("binexpr") && SampleController.findStatement(exp2).getOperationType().equals("binexpr")){
                    if(checkRefType(binExpr(SampleController.findStatement(exp1))) == 0 && checkRefType(binExpr(SampleController.findStatement(exp2))) == 0){
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

    /**
     * Un expr string.
     *
     * @param s the s
     * @return the string
     * @throws InterpreterException the interpreter exception
     */
    public static String unExpr(Statement s) throws InterpreterException { //REQ3
        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression," ");

        String a = st.nextToken();
        String b = st.nextToken();

        // condition 1 : pre_increment and pre_decrease
        if(a.equals("~") || a.equals("#")){
            if(a.equals("#")){
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
            if(a.equals("~")){
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

        }
        return "";
    }

    /**
     * Assign.
     *
     * @param s the s
     * @throws InterpreterException the interpreter exception
     */
    public static void assign(Statement s) throws InterpreterException { //REQ4
        String expression = s.getExpression();
        StringTokenizer st = new StringTokenizer(expression, " ");

        String LHS = st.nextToken();
        String RHS = st.nextToken();


        //condition 1 : LHS is int (int = int / int = variable / int = bool / int = expression) which are all wrong
        if (checkRefType(LHS) == 0) {
            ExceptionController.handleErr(s.getLabel(), ExceptionController.NOTVAR);
        }

        //condition 2 : LHS is bool ( bool = int / bool = variable / bool = bool / bool = expression) which are all wrong
        if (checkRefType(LHS) == 1) {
            ExceptionController.handleErr(s.getLabel(), ExceptionController.NOTVAR);
        }

        //condition 3 : LHS is variable (variable = int / variable = expression / variable = bool / variable = variable)
        if (checkRefType(LHS) == 2) {
            // 1 : variable = int
            if (intvar.containsKey(LHS)) {
                if (checkRefType(RHS) == 0) {
                    Integer RHS_value = Integer.parseInt(RHS);
                    intvar.put(LHS, RHS_value);
                }

                // 2 : variable = bool
                if (checkRefType(RHS) == 1) {
                    ExceptionController.handleErr(s.getLabel(), ExceptionController.SYNTAX);
                }

                // 3 : variable = variable
                if (checkRefType(RHS) == 2) {
                    if (intvar.get(RHS) == null) {
                        ExceptionController.handleErr(s.getLabel(), ExceptionController.UNDEFINEDVAR);
                    }
                    int RHS_value = intvar.get(RHS);
                    intvar.put(LHS, RHS_value);
                }

                // 4 : variable = expression
                if (checkRefType(RHS) == 3) {
                    if (SampleController.findStatement(RHS).getOperationType().equals("binexpr")) {
                        if (checkRefType(binExpr(SampleController.findStatement(RHS))) == 0) {
                            String value = binExpr(SampleController.findStatement(RHS));
                            Integer RHS_value = Integer.parseInt(value);
                            intvar.put(LHS, RHS_value);
                        } else
                            ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                    } else if (SampleController.findStatement(RHS).getOperationType().equals("unexpr")) {
                        if (checkRefType(unExpr(SampleController.findStatement(RHS))) == 0) {
                            String value = unExpr(SampleController.findStatement(RHS));
                            Integer RHS_value = Integer.parseInt(value);
                            intvar.put(LHS, RHS_value);
                        } else
                            ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                    } else
                        ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                }
            } else {
                // 1 : variable = bool
                if (checkRefType(RHS) == 1) {
                    boolvar.put(LHS, Boolean.valueOf(RHS));
                }

                // 2 : variable = bool
                if (checkRefType(RHS) == 0) {
                    ExceptionController.handleErr(s.getLabel(), ExceptionController.SYNTAX);
                }

                // 3 : variable = variable
                if (checkRefType(RHS) == 2) {
                    if (boolvar.get(RHS) == null) {
                        ExceptionController.handleErr(s.getLabel(), ExceptionController.UNDEFINEDVAR);
                    }
                    boolvar.put(LHS, Boolean.valueOf(RHS));
                }

                // 4 : variable = expression
                if (checkRefType(RHS) == 3) {
                    if (SampleController.findStatement(RHS).getOperationType().equals("binexpr")) {
                        if (checkRefType(binExpr(SampleController.findStatement(RHS))) == 1) {
                            String value = binExpr(SampleController.findStatement(RHS));
                            boolvar.put(LHS, Boolean.valueOf(RHS));
                        } else
                            ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                    } else if (SampleController.findStatement(RHS).getOperationType().equals("unexpr")) {
                        if (checkRefType(unExpr(SampleController.findStatement(RHS))) == 1) {
                            String value = unExpr(SampleController.findStatement(RHS));
                            boolvar.put(LHS, Boolean.valueOf(RHS));
                        } else
                            ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                    } else
                        ExceptionController.handleErr(SampleController.findStatement(RHS).getLabel(), ExceptionController.EXPTPWRONG);
                }
            }

            // condition 4 : LHS is expression ( expression = int / expression = expression / expression = bool / expression = variable )
            if (checkRefType(LHS) == 3) {
                ExceptionController.handleErr(s.getLabel(), ExceptionController.NOTVAR);
            }

        }
    }

    /**
     * Print.
     *
     * @param s the s
     * @throws InterpreterException the interpreter exception
     */
    public static void print(Statement s) throws InterpreterException {//REQ5
        String expression = s.getExpression();
        if(checkRefType(expression) == 0 || checkRefType(expression) == 1)
            System.out.println("["+expression+"] ");
        else if(checkRefType(expression) == 2)
        System.out.println("[" + (intvar.containsKey(expression) ? intvar.get(expression) : boolvar.get(expression)) + "] ");
        else {
            Statement S1 = SampleController.findStatement(expression);
            String type = S1.getOperationType();
            if(type.equals("binexpr")) System.out.println("[" + binExpr(S1) + "] ");
            else if(type.equals("unexpr")) System.out.println("[" + unExpr(S1) + "]");
            else ExceptionController.handleErr(expression,ExceptionController.EXPTPWRONG);
        }
    }

    /**
     * Skip.
     *
     * @param s the s
     */
    public static void skip(Statement s){//REQ6
        return;
    }

    /**
     * Block.
     *
     * @param s the s
     * @throws InterpreterException the interpreter exception
     */
    public static void block(Statement s) throws InterpreterException {//REQ7
        String expression = s.getExpression();
        String[] labs = expression.split(" ");
        for(int i = 0;i < labs.length; i++)
        {
            Statement currentS = SampleController.findStatementTotal(labs[i]);
            currentblock.add(currentS);
            ExecuteStatement(labs[i]);
        }
    }

    /**
     * If operation.
     *
     * @param s the s
     * @throws InterpreterException the interpreter exception
     */
    public static void ifOperation(Statement s) throws InterpreterException {//REQ8
        String expression = s.getExpression();
        StringTokenizer str = new StringTokenizer(expression," ");

        String condition = str.nextToken();
        String trueExecute = str.nextToken();
        String falseExecute = str.nextToken();

        boolean Result = true;
        if(checkRefType(condition) == 1)
            Result = Boolean.valueOf(condition);
        else if(checkRefType(condition) == 3){
            Statement expStatement = SampleController.findStatement(condition);
            String judgetype = expStatement.getOperationType();
            if(judgetype.equals("binexpr"))
                Result = (checkRefType(binExpr(expStatement))==1) ? Boolean.valueOf(binExpr(expStatement)): false;
            else if(judgetype.equals("unexpr"))
                Result = (checkRefType(unExpr(expStatement)) == 1) ? Boolean.valueOf(unExpr(expStatement)) : false;
            else ExceptionController.handleErr(condition, ExceptionController.EXPTPWRONG);
        }
        else Result = false;
        if(Result == true)
            ExecuteStatement(trueExecute);
        else ExecuteStatement(falseExecute);
    }

    /**
     * While operation.
     *
     * @param s the s
     * @throws InterpreterException the interpreter exception
     */
    public static void whileOperation(Statement s) throws InterpreterException {//REQ9
        String expression = s.getExpression();
        StringTokenizer str = new StringTokenizer(expression," ");

        String condition = str.nextToken();
        String trueExecute = str.nextToken();

        boolean Result = true;
        if(checkRefType(condition) == 1)
            Result = Boolean.valueOf(condition);
        else if(checkRefType(condition) == 3){
            Statement expStatement = SampleController.findStatement(condition);
            String judgetype = expStatement.getOperationType();
            if(judgetype.equals("binexpr"))
                Result = (checkRefType(binExpr(expStatement))==1) ? Boolean.valueOf(binExpr(expStatement)): false;
            else if(judgetype.equals("unexpr"))
                Result = (checkRefType(unExpr(expStatement)) == 1) ? Boolean.valueOf(unExpr(expStatement)) : false;
            else ExceptionController.handleErr(condition, ExceptionController.EXPTPWRONG);
        }
        else Result = false;
        while(Result){
            ExecuteStatement(trueExecute);

            if(checkRefType(condition) == 1)
                Result = Boolean.valueOf(condition);
            else if(checkRefType(condition) == 3){
                Statement expStatement = SampleController.findStatement(condition);
                String judgetype = expStatement.getOperationType();
                if(judgetype.equals("binexpr"))
                    Result = (checkRefType(binExpr(expStatement))==1) ? Boolean.valueOf(binExpr(expStatement)): false;
                else if(judgetype.equals("unexpr"))
                    Result = (checkRefType(unExpr(expStatement)) == 1) ? Boolean.valueOf(unExpr(expStatement)) : false;
                else ExceptionController.handleErr(condition, ExceptionController.EXPTPWRONG);
            }
            else Result = false;

        }
    }
}


