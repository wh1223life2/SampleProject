package hk.edu.polyu.comp.comp2021.simple.control;

public class ExceptionController {
    public static final int SYNTAX = 0;
    public static final int NOVARTP = 1;
    public static final int NOEXPTP = 2;
    public static final int DIVBYZERO = 3;
    public static final int EQUALEXPECTED = 4;
    public static final int NOTVAR = 5;
    public static final int DUPLABEL = 6;
    public static final int UNDEFLABEL = 7;
    public static final int UNDEFINEDVAR = 8;

    public static void handleErr(String label, int error) throws InterpreterException
    {
        String[] err = {
                "Syntax Error",
                "Variable Type does not exist",
                "Expression Type does not exist",
                "Division by Zero",
                "Equal sign expected",
                "Not a variable",
                "Duplicate label",
                "Undefined label",
                "Variable cannot be defined"
        };
        throw new InterpreterException("<" + label + ">" + err[error]);
    }

    /*
    1.类型是否符合？
    2.label是否重复（define）/label是否不存在？（run）
    3.expression的表达是否属于该类型（合规？）-》只判断字符串数量 不判断分割后字符串内容是否合规
     */
    public static boolean checkLegal(String type, String rawstring){
        if(SampleController.RunExecuteType.contains())
    }
}
