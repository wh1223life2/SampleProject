package hk.edu.polyu.comp.comp2021.simple.control;

/**
 * The type Interpreter exception.
 */
public class InterpreterException extends Exception {
    private String err;

    /**
     * Instantiates a new Interpreter exception.
     *
     * @param str the str
     */
    public InterpreterException(String str) {
        err = str;
    }

    public String toString() {
        return err;
    }

}
