import java.util.List;
import java.util.Map;

/**
 * The Pow Class represents an expression of power.
 *
 * @author Amit Twito
 */
public class Pow extends BinaryExpression implements Expression {


    public static final String EXPRESSION_STRING = "^";

    /**
     * A constructor for the Pow class.
     *
     * @param firstArgument  The first argument: the factor.
     * @param secondArgument The second argument: the power to raise the factor.
     */
    public Pow(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param var1 The first argument: the factor.
     * @param var2 The second argument: the power to raise the factor.
     */
    public Pow(String var1, String var2) {
        super(new Var(var1), new Var(var2), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param var The first argument: the factor.
     * @param num The second argument: the power to raise the factor.
     */
    public Pow(String var, double num) {
        super(new Var(var), new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param num The first argument: the factor.
     * @param var The second argument: the power to raise the factor.
     */
    public Pow(double num, String var) {
        super(new Var(var), new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param num1 The first argument: the factor.
     * @param num2 The second argument: the power to raise the factor.
     */
    public Pow(double num1, double num2) {
        super(new Num(num1), new Num(num2), EXPRESSION_STRING);

        if (num1 == 0 && num2 <= 0) {
            throw new IllegalArgumentException("Cannot calculate a power of zero with a zero or negative number");
        }
    }

    /**
     * A constructor for the Pow class.
     *
     * @param num        The first argument: the factor.
     * @param expression The second argument: the power to raise the factor.
     */
    public Pow(double num, Expression expression) {
        super(new Num(num), expression, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param expression The first argument: the factor.
     * @param num        The second argument: the power to raise the factor.
     */
    public Pow(Expression expression, double num) {
        super(expression, new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param var        The first argument: the factor.
     * @param expression The second argument: the power to raise the factor.
     */
    public Pow(String var, Expression expression) {
        super(new Var(var), expression, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Pow class.
     *
     * @param expression The first argument: the factor.
     * @param var        The second argument: the power to raise the factor.
     */
    public Pow(Expression expression, String var) {
        super(expression, new Var(var), EXPRESSION_STRING);
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return String representation of the expression.
     */
    @Override
    public String toString() {
        return OPEN_BRACKETS + getFirstArgument() + EXPRESSION_STRING + getSecondArgument() + CLOSE_BRACKETS;
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment Map of variable and values for assigning in the expression.
     * @return Evaluated result of the Expression after an assignment.
     * @throws Exception if the expression contains a variable which is not in the assignment.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (assignment == null) {
            throw new IllegalArgumentException("Can't assign Null to the expression.");
        }
        Expression exp1 = getFirstArgument();
        Expression exp2 = getSecondArgument();
        List<String> vars = getVariables();
        for (Map.Entry<String, Double> entry : assignment.entrySet()) {
            Expression expression = new Num(entry.getValue());
            if (!vars.contains(entry.getKey())) {
                throw new Exception("Can't assign " + entry.getValue()
                        + " to var " + entry.getKey()
                        + " because it does not exist in this expression.");
            }
            exp1 = exp1.assign(entry.getKey(), expression);
            exp2 = exp2.assign(entry.getKey(), expression);
        }
        return new Pow(exp1, exp2).evaluate();
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return Evaluated result of the Expression.
     * @throws Exception If the Expression was not assigned with values.
     */
    @Override
    public double evaluate() throws Exception {
        double value1 = getFirstArgument().evaluate();
        double value2 = getSecondArgument().evaluate();

        if (value1 == 0 && value2 <= 0) {
            throw new ArithmeticException("Can't evaluate 0 ^ " + value2 + " in this expression, "
                    + " undefined number");
        }
        return Math.pow(value1, value2);
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var The var to differentiate by.
     * @return Expression, the differentiation of the expression.
     */
    @Override
    public Expression differentiate(String var) {

        //(f^g)' = (f^g)*(f'(g/f) + g'Log(e,f))
        Expression originalExp = new Pow(getFirstArgument(), getSecondArgument());
        Expression divExp = new Div(getSecondArgument(), getFirstArgument());
        Expression multExp1 = new Mult(getFirstArgument().differentiate(var), divExp);
        Expression multExp2 = new Mult(getSecondArgument().differentiate(var),
                new Log(new Num("e"), getFirstArgument()));
        Expression plusExp = new Plus(multExp1, multExp2);
        return new Mult(originalExp, plusExp);
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        The variable to assign an expression to.
     * @param expression The expression to assign into a variable.
     * @return New expression with the assigned variable.
     */
    @Override
    public Expression assign(String var, Expression expression) {

        Expression exp1 = getFirstArgument().assign(var, expression);
        Expression exp2 = getSecondArgument().assign(var, expression);
        return new Pow(exp1, exp2);

    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        //First get both (for the recursive) arguments of the Expression simplified.
        //Then check if it a number - return it as a Num.
        //Try to evaluate both arguments with inserting it into the function calculation.
        //If not, catch the ArithmeticException and throw it, catch the Exception and return the current function,
        //with its both arguments simplified.
        Expression simpleExp1 = getFirstArgument().simplify();
        Expression simpleExp2 = getSecondArgument().simplify();

        if (canParseDouble(this.toString())) {
            return new Num(parseDouble(this.toString()));
        }

        if (canParseDouble(simpleExp2.toString())) {
            //If the second on is zero, return the Num 1.
            if (parseDouble(simpleExp2.toString()) == 0) {
                return new Num(1);
            }
            //If the second on is 1, return the first argument.
            if (Double.parseDouble(simpleExp2.toString()) == 1) {
                return simpleExp1;
            }
        }
        try {
            return new Num(Math.pow(simpleExp1.evaluate(), simpleExp2.evaluate()));
        } catch (ArithmeticException ae) {
            throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
        } catch (Exception e) {
            return new Pow(simpleExp1, simpleExp2);

        }
    }

    /**
     * @return Advanced simplified expression.
     */
    @Override
    public Expression advancedSimplify() {

        Expression advSimpleEx1 = getFirstArgument().advancedSimplify();
        Expression advSimpleEx2 = getSecondArgument().advancedSimplify();

        if (advSimpleEx1 instanceof Pow) {
            Pow innerPow = (Pow) advSimpleEx1;
            return new Pow(innerPow.getFirstArgument(), new Mult(innerPow.getSecondArgument(), getSecondArgument()));
        }

        return new Pow(advSimpleEx1, advSimpleEx2).simplify();
    }
}
