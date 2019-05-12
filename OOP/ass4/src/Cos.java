import java.util.List;
import java.util.Map;

/**
 * The Cos Class represents an expression of Cos math function.
 *
 * @author Amit Twito
 */
public class Cos extends UnaryExpression implements Expression {

    public static final String EXPRESSION_STRING = "Cos";

    /**
     * A constructor for the Cos class.
     *
     * @param expression The argument Expression of the Cos.
     */
    public Cos(Expression expression) {
        super(expression, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Cos class.
     *
     * @param var The var as an argument of the Cos.
     */
    public Cos(String var) {
        super(new Var(var), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Cos class.
     *
     * @param num The num as an argument of the Cos.
     */
    public Cos(double num) {
        super(new Num(num), EXPRESSION_STRING);
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment Map of variables and values for assigning in the expression.
     * @return Evaluated result of the Expression after an assignment.
     * @throws Exception if the expression contains a variable which is not in the assignment.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        Expression exp = getFirstArgument();
        List<String> vars = getVariables();
        for (Map.Entry<String, Double> entry : assignment.entrySet()) {
            Expression expression = new Num(entry.getValue());
            if (!vars.contains(entry.getKey())) {
                throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
            }
            exp = exp.assign(entry.getKey(), expression);
        }
        return new Cos(exp).evaluate();
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
        double value = getFirstArgument().evaluate();
        return Math.cos(value);
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

        Expression newExpression = getFirstArgument().assign(var, expression);
        return new Cos(newExpression);
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
        if (!this.getVariables().contains(var)) {
            return new Num(0);
        }
        //Cos'(X) = -Sin(X) * X';
        Expression difExp = getFirstArgument().differentiate(var);
        Expression negExp = new Neg(new Sin(getFirstArgument()));
        return new Mult(negExp, difExp);
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    @Override
    public Expression simplify() {

        //If we can evaluate the argument of the function, try to do it and then return it as a Num.
        //If not, catch the ArithmeticException and throw it, catch the Exception and return the current function,
        //with its argument simplified.
        try {
            double arg = getFirstArgument().evaluate();
            return new Num(arg);
        } catch (ArithmeticException ae) {
            throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
        } catch (Exception e) {
            return new Cos(getFirstArgument().simplify());
        }
    }

    /**
     * @return Advanced simplified expression.
     */
    @Override
    public Expression advancedSimplify() {

        if (getFirstArgument() instanceof Neg) {
            Neg neg = (Neg) getFirstArgument();
            return new Cos(neg.getFirstArgument());
        }

        return new Cos(getFirstArgument().advancedSimplify().simplify());
    }
}
