

public class Neg extends UnaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "-";

	/**
	 * A constructor for the Neg class.
	 *
	 * @param expression The argument Expression of the Neg.
	 */
	public Neg(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Neg class.
	 *
	 * @param var The var as an argument of the Neg.
	 */
	public Neg(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Neg class.
	 *
	 * @param num The var as an argument of the Neg.
	 */
	public Neg(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}

	@Override
	public String toString() {
		return OPEN_BRACKETS + EXPRESSION_STRING + getFirstArgumentExpression() + CLOSE_BRACKETS;
	}


	public double evaluate() throws Exception {
		double value = getFirstArgumentExpression().evaluate();
		return -1 * value;
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getFirstArgumentExpression().assign(var, expression);
		return new Neg(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getFirstArgumentExpression().differentiate(var);
		return new Neg(difExp);
	}

	public Expression simplify() {
		try {
			double arg = getFirstArgumentExpression().evaluate();
			return new Num(arg);
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Neg(getFirstArgumentExpression().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx = advancedSimplify();

		return new Neg(advSimpleEx);
	}
}
