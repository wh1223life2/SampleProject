package hk.edu.polyu.comp.comp2021.simple.model;

public class InterpreterException extends Exception {
    String err;

    public InterpreterException(String str) {
        err = str;
    }

    public String toString() {
        return err;
    }

}
