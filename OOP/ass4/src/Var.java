import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Var Class represents an expression of variables.
 *
 * @author Amit Twito
 */
public class Var implements Expression {

    private String var;
    /**
     * A constructor for the Var class.
     *
     * @param var The wanted var.
     */
    public Var(String var) {
        this.var = var;
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

        for (Map.Entry<String, Double> entry : assignment.entrySet()) {
            if (this.var.equals(entry.getKey())) {
                return entry.getValue().doubleValue();
            }
        }
        throw new Exception("The var that you tried to assign a number to, "
                + "does not exist in the expression.");
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
        throw new Exception("The var " + this.var + " can't be evaluated without an assignment");
    }


    /**
     * Returns a list of the variables in the expression.
     *
     * @return A list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        List<String> varList = new ArrayList<>();
        varList.add(this.var);
        return varList;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return String representation of the expression.
     */
    @Override
    public String toString() {
        return "" + this.var;
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param variable        The variable to assign an expression to.
     * @param expression The expression to assign into a variable.
     * @return New expression with the assigned variable.
     */
    @Override
    public Expression assign(String variable, Expression expression) {
        //assigning a var means replacing it with the expression.
        //if the given var isn't the current var, return this var itself.
        return this.var.equals(variable) ? expression : new Var(this.var);
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param variable The var to differentiate by.
     * @return Expression, the differentiation of the expression.
     */
    @Override
    public Expression differentiate(String variable) {
        return new Num(this.var.equals(variable) ? 1 : 0);
    }


    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return new Var(this.var);
    }


    /**
     * @return Advanced simplified expression.
     */
    @Override
    public Expression advancedSimplify() {
        return simplify();
    }
}
