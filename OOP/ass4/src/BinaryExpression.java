import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BinaryExpression extends BaseExpression {

	private String expressionString;

    public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
        super(expression1, expression2);
        this.expressionString = expressionString;
    }

    @Override
    public String toString() {
        if (this.expressionString.equals(Plus.EXPRESSION_STRING)
                || this.expressionString.equals(Minus.EXPRESSION_STRING)
                || this.expressionString.equals(Mult.EXPRESSION_STRING)
                || this.expressionString.equals(Div.EXPRESSION_STRING)
                || this.expressionString.equals(Pow.EXPRESSION_STRING)) {
            return OPEN_BRACKETS + getExpression1() + SPACE
                    + this.expressionString + SPACE + getExpression2() + CLOSE_BRACKETS;

        } else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
            return getExpression1() + this.expressionString + getExpression2();
        } else {
            return this.expressionString + OPEN_BRACKETS + getExpression1()
                    + COMMA + getExpression2() + CLOSE_BRACKETS;
        }
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        Expression exp1 = getExpression1();
        Expression exp2 = getExpression2();
        List<String> vars = getVariables();
        for (Map.Entry<String, Double> entry : assignment.entrySet()) {
            Expression expression = new Num(entry.getValue());
            if (!vars.contains(entry.getKey())) {
                throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
            }
            exp1 = exp1.assign(entry.getKey(), expression);
            exp2 = exp2.assign(entry.getKey(), expression);
        }
        return (new BinaryExpression(exp1, exp2, this.expressionString)).evaluate();
    }

    @Override
    public double evaluate() throws Exception {
        return calculateByMathFunction();
    }

    @Override
    public Expression differentiate(String var) {
        return differentiateByMathFunction(var);
    }

    @Override
    public List<String> getVariables() {
        List<String> varList1 = getExpression1().getVariables();
        List<String> varList2 = getExpression2().getVariables();
        varList1.addAll(varList2);
        return new ArrayList<>(new HashSet<>(varList1));
    }

    private double calculateByMathFunction() throws Exception {
        double value1 = getExpression1().evaluate();
        double value2 = getExpression2().evaluate();
        if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
            return value1 + value2;
        } else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
            return value1 - value2;
        } else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
            return value1 * value2;
        } else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
            return value1 / value2;
        } else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
            return Math.pow(value1, value2);
        } else {
            return Math.log(value2) / Math.log(value1);
        }
    }

    private Expression differentiateByMathFunction(String var) {
        Expression difExp1 = getExpression1().differentiate(var);
        Expression difExp2 = getExpression2().differentiate(var);
        if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
            return new Plus(difExp1, difExp2);
        } else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
            return new Minus(difExp1, difExp2);
        } else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
            Expression multExp1 = new Mult(difExp1, getExpression2());
            Expression multExp2 = new Mult(getExpression1(), difExp2);
            return new Plus(multExp1, multExp2);
        } else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
            Expression multExp1 = new Mult(difExp1, getExpression2());
            Expression multExp2 = new Mult(getExpression1(), difExp2);
            Expression minusExp = new Minus(multExp1, multExp2);
            Expression powExp = new Pow(getExpression2(), 2);
            Expression divExp = new Div(minusExp, powExp);
            return divExp;
        } else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
            try {
                double powForDif = getExpression2().evaluate();
                if (powForDif == 1) {
                    return difExp1;
                }
                Expression powExp = new Pow(getExpression1(), powForDif - 1);
                Expression multExp = new Mult(powForDif, powExp);
                return new Pow(multExp, difExp1);
            } catch (Exception e) {
                System.out.println("At differentiate of Pow function: " + e.getMessage());
            }
        } else {
            try {
                double base = getExpression1().evaluate();
                Expression divExp = new Div(1, new Mult(getExpression2(), new Log(Math.E, base)));
                Expression multExp = new Mult(divExp, difExp2);
                return multExp;
            } catch (Exception e) {
                System.out.println("At differentiate of Log function: " + e.getMessage());
            }
        }
        return null;
    }
}
