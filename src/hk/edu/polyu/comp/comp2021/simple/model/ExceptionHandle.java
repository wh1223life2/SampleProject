package hk.edu.polyu.comp.comp2021.simple.model;

public class ExceptionHandle {
    public static final int SYNTAX = 0;
    public static final int NOVARTP = 1;
    public static final int NOEXPTP = 2;
    public static final int DIVBYZERO = 3;
    public static final int EQUALEXPECTED = 4;
    public static final int NOTVAR = 5;
    public static final int DUPLABEL = 6;
    public static final int UNDEFLABEL = 7;
    public static final int UNDEFINEDVAR = 8;

    public static void handleErr(int error) throws InterpreterException
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
        throw new InterpreterException(err[error]);
    }
}
