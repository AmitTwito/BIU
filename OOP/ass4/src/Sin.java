
public class Sin extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Sin";

	/**
	 * A constructor for the Sin class.
	 *
	 * @param expression The argument Expression of the Sin.
	 */
	public Sin(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Sin class.
	 *
	 * @param var The var as an argument of the Sin.
	 */
	public Sin(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	/**
	 * A constructor for the Sin class.
	 *
	 * @param num The num as an argument of the Sin.
	 */
	public Sin(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}


	public double evaluate() throws Exception {
		double value = getFirstArgumentExpression().evaluate();
		return Math.sin(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getFirstArgumentExpression().assign(var, expression);
		return new Sin(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getFirstArgumentExpression().differentiate(var);
		Expression cosExp = new Cos(getFirstArgumentExpression());
		return new Mult(cosExp, difExp);
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

		if (advSimpleEx instanceof Sin) {
			Sin sin = (Sin) advSimpleEx;
			if (sin.getFirstArgumentExpression() instanceof Neg) {

				return new Neg(new Sin(((Neg) ((Neg) sin.getFirstArgumentExpression()).getFirstArgumentExpression())));
			}

		}

		return advSimpleEx.simplify();
	}
}
