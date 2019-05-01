import java.util.List;
import java.util.Map;

public class Div extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "/";

	/**
	 * A constructor for the Div class.
	 *
	 * @param firstArgument  The first argument: the numerator.
	 * @param secondArgument The second argument: the denominator.
	 */
	public Div(Expression firstArgument, Expression secondArgument) {
		super(firstArgument, secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param var1 A variable as the numerator.
	 * @param var2 A variable as the denominator.
	 */
	public Div(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param var A variable as the numerator.
	 * @param num A number as the denominator.
	 */
	public Div(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
		if (num == 0) {
			throw new IllegalArgumentException("Can't divide by zero.");
		}
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param num A number as the numerator.
	 * @param var A variable as the denominator.
	 */
	public Div(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param num1 A number as the numerator.
	 * @param num2 A number as the denominator.
	 */
	public Div(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param num            A number as the numerator.
	 * @param secondArgument The second argument: the denominator.
	 */
	public Div(double num, Expression secondArgument) {
		super(new Num(num), secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param firstArgument The first argument: the numerator.
	 * @param num           A number as the denominator.
	 */
	public Div(Expression firstArgument, double num) {
		super(firstArgument, new Num(num), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param var            A var as the numerator.
	 * @param secondArgument The second argument: the denominator.
	 */
	public Div(String var, Expression secondArgument) {
		super(new Var(var), secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Div class.
	 *
	 * @param firstArgument The first argument: the numerator.
	 * @param var           A variable as the denominator.
	 */
	public Div(Expression firstArgument, String var) {
		super(firstArgument, new Var(var), EXPRESSION_STRING);
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
		return new Div(exp1, exp2).evaluate();
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
		if (getSecondArgument().evaluate() == 0) {
			throw new ArithmeticException("An error occurred on dividing calculation: "
					+ "can't divide by zero.");
		}
		return getFirstArgument().evaluate() / getSecondArgument().evaluate();
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
		Expression multExp1 = new Mult(getFirstArgument().differentiate(var), getSecondArgument());
		Expression multExp2 = new Mult(getFirstArgument(), getSecondArgument().differentiate(var));
		Expression minusExp = new Minus(multExp1, multExp2);
		Expression powExp = new Pow(getSecondArgument(), 2);
		Expression divExp = new Div(minusExp, powExp);
		return divExp;
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
		return new Div(exp1, exp2);

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
			if (parseDouble(simpleExp2.toString()) == 1) {
				return simpleExp1;
			}
		}
		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Num(0);
			}
		}
		try {
			return new Num(simpleExp1.evaluate() / simpleExp2.evaluate());
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Div(simpleExp1, simpleExp2);
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgument().advancedSimplify();
		Expression advSimpleEx2 = getSecondArgument().advancedSimplify();

		if (advSimpleEx1 instanceof Sin
				&& advSimpleEx2 instanceof Cos) {
			Sin sin = (Sin) advSimpleEx1;
			Cos cos = (Cos) advSimpleEx2;
			if (sin.getFirstArgument().toString().equals(cos.getFirstArgument().toString())) {
				return new Tan(sin.getFirstArgument().simplify());
			}
		}
		if (advSimpleEx1 instanceof Cos
				&& advSimpleEx2 instanceof Sin) {
			Cos cos = (Cos) advSimpleEx1;
			Sin sin = (Sin) advSimpleEx2;

			if (sin.getFirstArgument().toString().equals(cos.getFirstArgument().toString())) {
				return new Pow(new Tan(cos.getFirstArgument().simplify()), -1);
			}
		}


		return new Div(advSimpleEx1, advSimpleEx2).simplify();
	}
}
