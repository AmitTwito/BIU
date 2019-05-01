import java.util.List;
import java.util.Map;

public class Log extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Log";

	/**
	 * A constructor for the Log class.
	 *
	 * @param firstArgument  The first argument: the base.
	 * @param secondArgument The second argument: the exponent.
	 */
	public Log(Expression firstArgument, Expression secondArgument) {
		super(firstArgument, secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param var1 The first argument: the base.
	 * @param var2 The second argument: the exponent.
	 */
	public Log(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param var The first argument: the base.
	 * @param num The second argument: the exponent.
	 */
	public Log(String var, double num) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);

		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param num The first argument: the base.
	 * @param var The second argument: the exponent.
	 */
	public Log(double num, String var) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param num1 The first argument: the base.
	 * @param num2 The second argument: the exponent.
	 */
	public Log(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
		if (num1 <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
		if (num2 <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param num        The first argument: the base.
	 * @param expression The second argument: the exponent.
	 */
	public Log(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param expression The first argument: the base.
	 * @param num        The second argument: the exponent.
	 */
	public Log(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param var        The first argument: the base.
	 * @param expression The second argument: the exponent.
	 */
	public Log(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Log class.
	 *
	 * @param expression The first argument: the base.
	 * @param var        The second argument: the exponent.
	 */
	public Log(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}

	/**
	 * Returns a nice string representation of the expression.
	 *
	 * @return String representation of the expression.
	 */
	@Override
	public String toString() {
		return EXPRESSION_STRING + OPEN_BRACKETS + getFirstArgument()
				+ COMMA_WITH_SPACE + getSecondArgument() + CLOSE_BRACKETS;
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
		return new Log(exp1, exp2).evaluate();
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
		if (Math.log(value1) == Double.NaN
				|| Math.log(value1) == Double.NaN
				|| Double.isInfinite(Math.log(value1))
				|| Double.isInfinite(Math.log(value2))) {
			throw new ArithmeticException("An error occurred on log calculation: "
					+ "one or both arguments caused the result to be infinity or NaN (not a number).");
		}
		return Math.log(value2) / Math.log(value1);
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
		Expression topLogExp = new Log(new Num("e"), getSecondArgument());
		Expression bottomLogExp = new Log(new Num("e"), getFirstArgument());
		return new Div(topLogExp, bottomLogExp).differentiate(var);
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
		return new Log(exp1, exp2);

	}
	/**
	 * Returned a simplified version of the current expression.
	 *
	 * @return New simplified version of the current expression.
	 */
	@Override
	public Expression simplify() {
		Expression simpleExp1 = getFirstArgument().simplify();
		Expression simpleExp2 = getSecondArgument().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (getFirstArgument().toString().equals(getSecondArgument().toString())) {
			return new Num(1);
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(1);
			}
		}
		try {
			return new Num(Math.log(simpleExp2.evaluate()) / Math.log(simpleExp1.evaluate()));
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Log(simpleExp1, simpleExp2);
		}
	}

	/**
	 * @return
	 */
	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgument().advancedSimplify();
		Expression advSimpleEx2 = getSecondArgument().advancedSimplify();

		if (getSecondArgument() instanceof Pow) {
			Pow pow = (Pow) getSecondArgument();
			return new Mult(pow.getSecondArgument(), new Log(getFirstArgument(), pow.getFirstArgument()));
		}

		return new Log(advSimpleEx1, advSimpleEx2);
	}
}
