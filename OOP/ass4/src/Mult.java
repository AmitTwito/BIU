import java.util.List;
import java.util.Map;

public class Mult extends BinaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "*";

	/**
	 * A constructor for the Mult class.
	 *
	 * @param firstArgument  The first argument..
	 * @param secondArgument The second argument..
	 */
	public Mult(Expression firstArgument, Expression secondArgument) {
		super(firstArgument, secondArgument, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param var1 The first argument..
	 * @param var2 The second argument..
	 */
	public Mult(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param var The first argument..
	 * @param num The second argument..
	 */
	public Mult(String var, double num) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param num The first argument..
	 * @param var The second argument..
	 */
	public Mult(double num, String var) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param num1 The first argument..
	 * @param num2 The second argument..
	 */
	public Mult(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param num        The first argument..
	 * @param expression The second argument..
	 */
	public Mult(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param expression The first argument..
	 * @param num        The second argument..
	 */
	public Mult(Expression expression, double num) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param var        The first argument..
	 * @param expression The second argument..
	 */
	public Mult(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Mult class.
	 *
	 * @param expression The first argument..
	 * @param var        The second argument..
	 */
	public Mult(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}


	/*public String toString() {
		if (canParseDouble(getFirstArgument().toString())
				&& !canParseDouble(getSecondArgument().toString())) {
			if (this.getVariables().contains(getSecondArgument().toString())) {
				if (parseDouble(getFirstArgument().toString()) -
						(int) parseDouble(getFirstArgument().toString()) == 0) {
					return (int) parseDouble(getFirstArgument().toString()) + "" + getSecondArgument();
				}
				return getFirstArgument() + "" + getSecondArgument();
			}
		}
		if (canParseDouble(getSecondArgument().toString())
				&& !canParseDouble(getFirstArgument().toString())) {
			if (this.getVariables().contains(getFirstArgument().toString())) {
				if (parseDouble(getSecondArgument().toString()) -
						(int) parseDouble(getSecondArgument().toString()) == 0) {
					return (int) parseDouble(getSecondArgument().toString()) + "" + getFirstArgument();
				}
				return getSecondArgument() + "" + getFirstArgument();
			} else {
				return OPEN_BRACKETS + getSecondArgument() + SPACE
						+ EXPRESSION_STRING + SPACE + getFirstArgument() + CLOSE_BRACKETS;
			}
		}

		return super.toString();

	}
*/

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
		return new Mult(exp1, exp2).evaluate();
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
		return value1 * value2;
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
		return new Plus(multExp1, multExp2);
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
		return new Mult(exp1, exp2);
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

		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 1) {
				return simpleExp2;
			}
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Num(0);
			}
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 1) {
				return simpleExp1;
			}
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(0);
			}
		}

		try {
			return new Num(simpleExp1.evaluate() * simpleExp2.evaluate());
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression "
					+ "with the following reason: " + ae.getMessage());
		} catch (Exception e) {
			return new Mult(simpleExp1, simpleExp2);
		}
	}

	/**
	 * @return
	 */
	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgument().advancedSimplify().simplify();
		Expression advSimpleEx2 = getSecondArgument().advancedSimplify().simplify();

		//X*X = X^2
		if (advSimpleEx1.toString().equals(advSimpleEx2.toString())) {
			return new Pow(advSimpleEx1, 2);
		}
		//X * Num = Num * X
		if (advSimpleEx2 instanceof Num && !(advSimpleEx1 instanceof Num)) {
			return new Mult(advSimpleEx2, advSimpleEx1);
		}

		if (advSimpleEx1 instanceof Num) {
			//2(2x) => (2*2)x
			if (advSimpleEx2 instanceof Mult) {
				Mult mult = (Mult) advSimpleEx2;
				if (mult.getFirstArgument() instanceof Num) {
					return new Mult(new Mult(advSimpleEx1, mult.getFirstArgument().simplify())
							, mult.getSecondArgument().simplify()).advancedSimplify().advancedSimplify();
				}
				if (mult.getSecondArgument() instanceof Num) {
					return new Mult(new Mult(advSimpleEx1, mult.getSecondArgument().simplify()),
							mult.getFirstArgument().simplify()).advancedSimplify();
				}
			}
		}
		if (advSimpleEx1 instanceof Pow && advSimpleEx2 instanceof Pow) {
			Pow pow1 = (Pow) advSimpleEx1;
			Pow pow2 = (Pow) advSimpleEx2;
			if (pow1.getFirstArgument().toString().equals(pow1.getFirstArgument().toString())) {
				return new Pow(pow1.getFirstArgument().simplify(),
						new Plus(pow1.getSecondArgument().simplify(),
								pow2.getFirstArgument().simplify())).advancedSimplify();
			}
		}

		return new Mult(advSimpleEx1.simplify(), advSimpleEx2.simplify());
	}
}
