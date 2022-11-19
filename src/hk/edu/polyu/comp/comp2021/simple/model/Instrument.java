package hk.edu.polyu.comp.comp2021.simple.model;

import hk.edu.polyu.comp.comp2021.simple.control.ExceptionController;
import hk.edu.polyu.comp.comp2021.simple.control.InterpreterException;
import hk.edu.polyu.comp.comp2021.simple.control.SampleController;

public class Instrument {
    private String programName;
    private String statementLab;
    private String pos;
    private String expRef;

    public Instrument(String name, String lab,String pos, String expRef) throws InterpreterException {
        if(SampleController.Program.containsKey(name))
            this.programName = name;
        else ExceptionController.handleErr(name,ExceptionController.NOPRONAME);
        if(SampleController.total.contains(lab))
            this.statementLab = lab;
        else ExceptionController.handleErr(lab,ExceptionController.UNDEFLABEL);
        if(pos.equals("before") || pos.equals("after"))
            this.pos = pos;
        else ExceptionController.handleErr(lab,ExceptionController.SYNTAX);
        this.expRef = expRef;
    }
    public String getProgramName(){
        return programName;
    }

    public String getExpRef() {
        return expRef;
    }

    public String getPos() {
        return pos;
    }

    public String getStatementLab() {
        return statementLab;
    }
}
