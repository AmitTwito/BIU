import java.util.List;
import java.util.Map;


/**
 *
 */
public interface Expression {

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
    double evaluate(Map<String, Double> assignment) throws Exception;


    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return Evaluated result of the Expression.
     * @throws Exception If the Expression was not assigned with values.
     */
    double evaluate() throws Exception;


    /**
     * Returns a list of the variables in the expression.
     *
     * @return A list of the variables in the expression.
     */
    List<String> getVariables();

    /**
     * Returns a nice string representation of the expression.
     *
     * @return String representation of the expression.
     */
    String toString();

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        The variable to assign an expression to.
     * @param expression The expression to assign into a variable.
     * @return New expression with the assigned variable.
     */
    Expression assign(String var, Expression expression);

    //

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var The var to differentiate by.
     * @return Expression, the differentiation of the expression.
     */
    Expression differentiate(String var);


    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    Expression simplify();

    /**
     * @return Advanced simplified expression.
     */
    Expression advancedSimplify();
}

