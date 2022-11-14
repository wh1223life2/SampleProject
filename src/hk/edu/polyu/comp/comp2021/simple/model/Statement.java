package hk.edu.polyu.comp.comp2021.simple.model;

public class Statement {
    private String OperationType;
    private String label;
    private String Expression;
    public Statement(String OperationType, String label, String Expression){
        this.OperationType = OperationType;
        this.label = label;
        this.Expression = Expression;
    }

    public String getExpression() {
        return Expression;
    }

    public String getLabel() {
        return label;
    }

    public String getOperationType() {
        return OperationType;
    }

}
