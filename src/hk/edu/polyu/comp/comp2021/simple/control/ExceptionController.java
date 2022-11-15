package hk.edu.polyu.comp.comp2021.simple.control;

import static hk.edu.polyu.comp.comp2021.simple.control.SampleController.total;

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
    public static final int NOOPETP = 9;

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
                "Operate type dose not exist"

        };
        throw new InterpreterException("<" + label + "> " + err[error]);
    }

    /*
    只检查操作类型 不检查run
    1.类型是否符合？
    2.label是否重复（define）/label是否不存在？（run）[raw string内的]
    3.expression的表达是否属于该类型（合规？）-》只判断字符串数量 不判断分割后字符串内容是否合规
     */
    public static void checkLegal(String type,String rawstring) throws InterpreterException {
        String[] single = rawstring.split(" ");
        if(!SampleController.checkUnique(single[0]))
            handleErr(single[0],DUPLABEL);
        else{
            if(type.equals("vardef") || type.equals("binexpr") || type.equals("if")){
                if(single.length != 4)
                    handleErr(single[0],SYNTAX);
            }
            else if(type.equals("unexpr") || type.equals("assign") || type.equals("while")){
                if(single.length != 3)
                    handleErr(single[0],SYNTAX);
            }
            else if(type.equals("print") || type.equals("program")){
                if(single.length != 2)
                    handleErr(single[0],SYNTAX);
            }
            else if(type.equals("skip")){
                if(single.length != 1)
                    handleErr(single[0],SYNTAX);
            }
            else{
                if(single.length < 2)
                    handleErr(single[0],SYNTAX);
            }
        }
    }
}
