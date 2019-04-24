import java.util.List;

public class UnaryExpression extends BaseExpression {

    public UnaryExpression(Expression expression, String expressionString) {
        super(expression, expressionString);
    }

    public UnaryExpression(String var, String expressionString) {
        super(new Var(var), expressionString);
    }

    public UnaryExpression(double num, String expressionString) {
        super(new Num(num), expressionString);
    }

    public String toString() {
        return this.expressionString + OPEN_BRACKETS + this.expression1 + CLOSE_BRACKETS;
    }

    protected double evaluate() throws Exception {
        return ExpressionController.CalculateByMathFunction(this);
    }

    public List<String> getVariables() {
        return this.expression1.getVariables();
    }
}
