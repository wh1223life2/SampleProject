package hk.edu.polyu.comp.comp2021.simple.control;

import static hk.edu.polyu.comp.comp2021.simple.control.SampleController.total;


/**
 * The type Exception controller.
 */
public class ExceptionController {
    /**
     * The constant SYNTAX.
     */
    public static final int SYNTAX = 0;
    /**
     * The constant NOVARTP.
     */
    public static final int NOVARTP = 1;
    /**
     * The constant NOEXPTP.
     */
    public static final int NOEXPTP = 2;
    /**
     * The constant DIVBYZERO.
     */
    public static final int DIVBYZERO = 3;
    /**
     * The constant EQUALEXPECTED.
     */
    public static final int EQUALEXPECTED = 4;
    /**
     * The constant NOTVAR.
     */
    public static final int NOTVAR = 5;
    /**
     * The constant DUPLABEL.
     */
    public static final int DUPLABEL = 6;
    /**
     * The constant UNDEFLABEL.
     */
    public static final int UNDEFLABEL = 7;
    /**
     * The constant UNDEFINEDVAR.
     */
    public static final int UNDEFINEDVAR = 8;
    /**
     * The constant NOOPETP.
     */
    public static final int NOOPETP = 9;

    /**
     * The constant NOTBINOP.
     */
    public static final int NOTBINOP = 10;

    /**
     * The constant EXPTPWRONG.
     */
    public static final int EXPTPWRONG = 11;
    /**
     * The constant DUPNAME.
     */
    public static final int DUPNAME = 12;
    /**
     * The constant DUPVARNAME.
     */
    public static final int DUPVARNAME = 13;
    /**
     * The constant NOPRONAME.
     */
    public static final int NOPRONAME = 14;

    /**
     * The constant OUTRANGE.
     */

    public static final int OUTRANGE = 15;

    /**
     * Handle err.
     *
     * @param label the label
     * @param error the error
     * @throws InterpreterException the interpreter exception
     */
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
                "Variable cannot be defined",
                "Operate type dose not exist",
                "Operator is not defined",
                "Expression Type Wrong",
                "Duplicate program name",
                "Duplicate variable name",
                "program name does not exist",
                "value is out of range",
        };
        throw new InterpreterException("<" + label + "> " + err[error]);
    }

    /**
     * Check legal.
     *
     * @param type      the type
     * @param rawstring the rawstring
     * @throws InterpreterException the interpreter exception
     */
/*
    只检查操作类型 不检查run
    1.类型是否符合？
    2.label是否重复（define）/label是否不存在？（run）[raw string内的]
    3.expression的表达是否属于该类型（合规？）-》只判断字符串数量 不判断分割后字符串内容是否合规
     */
    public static void checkLegal(String type,String rawstring) throws InterpreterException {
        String[] single = rawstring.split(" ");
        if(!SampleController.checkUniqueTotal(single[0]))
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

    /**
     * Check legal run.
     *
     * @param type      the type
     * @param rawstring the rawstring
     * @throws InterpreterException the interpreter exception
     */
    public static void checkLegalRun(String type,String rawstring) throws InterpreterException {
        String[] single = rawstring.split(" ");
        if(type.equals("program") || type.equals("store") || type.equals("load")){
            if(single.length != 2)
                handleErr(single[0],SYNTAX);
        }
        else{
            if(single.length != 1)
                handleErr(single[0],SYNTAX);
        }
    }
}
