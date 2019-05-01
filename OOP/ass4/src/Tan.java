import java.util.List;
import java.util.Map;

public class Tan extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Tan";

	/**
	 * A constructor for the Tan class.
	 *
	 * @param expression The argument Expression of the Tan.
	 */
	public Tan(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Tan class.
	 *
	 * @param var The var as an argument of the Tan.
	 */
	public Tan(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Tan class.
	 *
	 * @param num The var as an argument of the Tan.
	 */
	public Tan(double num) {
		super(new Num(num), EXPRESSION_STRING);
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
		return Math.tan(value);
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
		return new Tan(newExpression);
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

		Expression innerDiff = getFirstArgument().differentiate(var);
		return new Div(1, new Pow(new Cos(getFirstArgument()), 2));
	}
	/**
	 * Returned a simplified version of the current expression.
	 *
	 * @return New simplified version of the current expression.
	 */
	@Override
	public Expression simplify() {
		try {
			double arg = getFirstArgument().evaluate();
			return new Num(arg);
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Tan(getFirstArgument().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		return new Tan(getFirstArgument().simplify());
	}
}
