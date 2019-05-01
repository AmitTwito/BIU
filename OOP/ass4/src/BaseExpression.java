import java.util.List;
import java.util.Map;

/**
 * The BaseExpression Class represents an abstract expression of math function and arithmetic operators.
 *
 * @author Amit Twito
 * @since 6.4.19
 */
public abstract class BaseExpression {

	public static final String OPEN_BRACKETS = "(";
	public static final String CLOSE_BRACKETS = ")";
	public static final String SPACE = " ";
	public static final String COMMA_WITH_SPACE = ", ";

	private Expression firstArgument;
	private Expression secondArgument;

	/**
	 * A constructor for the BaseExpression class.
	 * For a binary expression.
	 *
	 * @param argument Expression for the BaseExpression to be built with.
	 */
	public BaseExpression(Expression argument) {
		this.firstArgument = argument;
	}

	/**
	 * A constructor for the BaseExpression class.
	 * For a unary expression.
	 *
	 * @param firstArgument  First
	 * @param secondArgument
	 */
	public BaseExpression(Expression firstArgument, Expression secondArgument) {
		this.firstArgument = firstArgument;
		this.secondArgument = secondArgument;
	}

	/**
	 * Returns the first argument of the expression.
	 *
	 * @return First argument expression.
	 */
	protected Expression getFirstArgument() {
		return this.firstArgument;
	}

	/**
	 * Returns the second argument of the expression.
	 *
	 * @return Second argument expression.
	 */
	protected Expression getSecondArgument() {
		return this.secondArgument;
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
	public abstract double evaluate(Map<String, Double> assignment) throws Exception;

	/**
	 * A convenience method. Like the `evaluate(assignment)` method above,
	 * but uses an empty assignment.
	 *
	 * @return Evaluated result of the Expression.
	 * @throws Exception If the Expression was not assigned with values.
	 */
	public abstract double evaluate() throws Exception;

	/**
	 * Returns a list of the variables in the expression.
	 *
	 * @return A list of the variables in the expression.
	 */
	public abstract List<String> getVariables();

	/**
	 * Returns a new expression in which all occurrences of the variable
	 * var are replaced with the provided expression (Does not modify the
	 * current expression).
	 *
	 * @param var        The variable to assign an expression to.
	 * @param expression The expression to assign into a variable.
	 * @return New expression with the assigned variable.
	 */
	public abstract Expression assign(String var, Expression expression);

	/**
	 * Returns the expression tree resulting from differentiating
	 * the current expression relative to variable `var`.
	 *
	 * @param var The var to differentiate by.
	 * @return Expression, the differentiation of the expression.
	 */
	public abstract Expression differentiate(String var);

	/**
	 * Returns a nice string representation of the expression.
	 *
	 * @return String representation of the expression.
	 */
	public abstract String toString();

	/**
	 * Returned a simplified version of the current expression.
	 *
	 * @return New simplified version of the current expression.
	 */
	public abstract Expression simplify();

	/**
	 * @return
	 */
	public abstract Expression advancedSimplify();


	protected boolean canParseDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			if (str.equals(Num.PI_CONST_STRING)) {
				return true;
			}
			return false;
		}
	}

	protected double parseDouble(String str) {
		if (str.equals(Num.PI_CONST_STRING)) {
			return Math.PI;
		}
		return Double.parseDouble(str);
	}
}
