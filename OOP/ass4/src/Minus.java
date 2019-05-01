import java.util.List;
import java.util.Map;

public class Minus extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "-";

	/**
	 * A constructor for the Minus class.
	 *
	 * @param firstArgument  The first argument..
	 * @param secondArgument The second argument..
	 */
	public Minus(Expression firstArgument, Expression secondArgument) {
		super(firstArgument, secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param var1 The first argument..
	 * @param var2 The second argument..
	 */
	public Minus(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param var The first argument..
	 * @param num The second argument..
	 */
	public Minus(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param num The first argument..
	 * @param var The second argument..
	 */
	public Minus(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param num1 The first argument..
	 * @param num2 The second argument..
	 */
	public Minus(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param num        The first argument..
	 * @param expression The second argument..
	 */
	public Minus(double num, Expression expression) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param expression The first argument..
	 * @param num        The second argument..
	 */
	public Minus(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param var        The first argument..
	 * @param expression The second argument..
	 */
	public Minus(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Minus class.
	 *
	 * @param expression The first argument..
	 * @param var        The second argument..
	 */
	public Minus(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
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
		return new Minus(exp1, exp2).evaluate();
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

		return value1 - value2;
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
		return new Minus(getFirstArgument().differentiate(var), getSecondArgument().differentiate(var));

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
		return new Minus(exp1, exp2);

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
			return new Num(0);
		}
		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Neg(simpleExp2);
			}
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 0) {
				return simpleExp1;
			}
		}
		try {
			return new Num(simpleExp1.evaluate() - simpleExp2.evaluate());
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Minus(simpleExp1, simpleExp2);
		}
	}

	@Override
	public Expression advancedSimplify() {

		Expression advSimpleEx1 = getFirstArgument().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgument().advancedSimplify();

		if (advSimpleEx2 instanceof Neg) {
			Neg neg = (Neg) advSimpleEx2;
			return new Plus(advSimpleEx1.simplify(), neg.getFirstArgument().simplify());
		}
		return new Plus(advSimpleEx1.simplify(), advSimpleEx2.simplify());
	}
}
