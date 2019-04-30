public class Cos extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Cos";

	/**
	 * A constructor for the Cos class.
	 *
	 * @param expression The argument Expression of the Cos.
	 */
	public Cos(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Cos class.
	 *
	 * @param var The var as an argument of the Cos.
	 */
	public Cos(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Cos class.
	 *
	 * @param num The num as an argument of the Cos.
	 */
	public Cos(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}


	public double evaluate() throws Exception {
		double value = getFirstArgumentExpression().evaluate();
		return Math.cos(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getFirstArgumentExpression().assign(var, expression);
		return new Cos(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getFirstArgumentExpression().differentiate(var);
		Expression negExp = new Neg(new Sin(getFirstArgumentExpression()));
		return new Mult(negExp, difExp);
	}

	public Expression simplify() {
		try {
			double arg = getFirstArgumentExpression().evaluate();
			return new Num(arg);
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Cos(getFirstArgumentExpression().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx = advancedSimplify();

		if (advSimpleEx instanceof Cos) {
			Cos cos = (Cos) advSimpleEx;
			if (cos.getFirstArgumentExpression() instanceof Neg) {

				return new Cos(((Neg) cos.getFirstArgumentExpression()).getFirstArgumentExpression());
			}
		}
		return advSimpleEx.simplify();
	}
}
