package hk.edu.polyu.comp.comp2021.simple.model;

/**
 * The type Statement.
 */
public class Statement {
    private String OperationType;
    private String label;
    private String Expression;

    /**
     * Instantiates a new Statement.
     *
     * @param OperationType the operation type
     * @param label         the label
     * @param Expression    the expression
     */
    public Statement(String OperationType, String label, String Expression){
        this.OperationType = OperationType;
        this.label = label;
        this.Expression = Expression;
    }

    /**
     * Gets expression.
     *
     * @return the expression
     */
    public String getExpression() {
        return Expression;
    }

    /**
     * Gets label.
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Gets operation type.
     *
     * @return the operation type
     */
    public String getOperationType() {
        return OperationType;
    }

}
