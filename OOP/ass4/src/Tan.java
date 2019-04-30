

public class Tan extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Cos";

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


	public double evaluate() throws Exception {
		double value = getFirstArgumentExpression().evaluate();
		return Math.tan(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getFirstArgumentExpression().assign(var, expression);
		return new Tan(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}

		Expression innerDiff = getFirstArgumentExpression().differentiate(var);
		return new Div(1, new Pow(new Cos(getFirstArgumentExpression()), 2));
	}

	public Expression simplify() {
		try {
			double arg = getFirstArgumentExpression().evaluate();
			return new Num(arg);
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Tan(getFirstArgumentExpression().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx = advancedSimplify();

		return advSimpleEx;
	}
}
