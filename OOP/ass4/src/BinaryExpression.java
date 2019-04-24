import java.util.List;
import java.util.Map;

public class BinaryExpression extends BaseExpression {


    public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
        super(expression1, expression2, expressionString);
    }

    public BinaryExpression(String var1, String var2, String expressionString) {
        super(new Var(var1), new Var(var2), expressionString);
    }

    public BinaryExpression(String var1, double num, String expressionString) {
        super(new Var(var1), new Num(num), expressionString);
    }
    public BinaryExpression( double num, String var1,String expressionString) {
        super(new Var(var1), new Num(num), expressionString);
    }

    public BinaryExpression(double num1, double num2, String expressionString) {
        super(new Num(num1), new Num(num2), expressionString);
    }

    public String toString() {
        return OPEN_BRACKETS + this.expression1 + SPACE
                + this.expressionString + SPACE + this.expression2 + CLOSE_BRACKETS;
    }


    protected double evaluate() throws Exception {
        return ExpressionController.CalculateByMathFunction(this);
    }

    public List<String> getVariables() {
        List<String> varList1 = expression1.getVariables();
        List<String> varList2 = expression2.getVariables();
        varList1.addAll(varList2);
        return varList1;
    }



}
