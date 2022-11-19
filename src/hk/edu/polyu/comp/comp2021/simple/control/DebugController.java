package hk.edu.polyu.comp.comp2021.simple.control;

import hk.edu.polyu.comp.comp2021.simple.model.Instrument;

import java.util.HashSet;

public class DebugController {

    public static HashSet<Instrument> totalInstr = new HashSet<>();
    public static void instrument(String rawstring) throws InterpreterException {

        String[] labs = rawstring.split(" ");
        if(labs.length != 4)
            ExceptionController.handleErr("instrument "+rawstring,ExceptionController.SYNTAX);
        totalInstr.add(new Instrument(labs[0],labs[1],labs[2],labs[3]));
    }
}
