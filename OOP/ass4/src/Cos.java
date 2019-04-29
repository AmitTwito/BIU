public class Cos extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Cos";

	public Cos(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Cos(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Cos(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}


	public double evaluate() throws Exception {
		double value = getExpression1().evaluate();
		return Math.cos(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getExpression1().assign(var, expression);
		return new Cos(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getExpression1().differentiate(var);
		Expression negExp = new Neg(new Sin(getExpression1()));
		return new Mult(negExp, difExp);
	}

	public Expression simplify() {
		try {
			double arg = getExpression1().evaluate();
			return new Num(arg);
		} catch (Exception e) {
			return new Cos(getExpression1().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx = advancedSimplify();

		if (advSimpleEx instanceof Cos) {
			Cos cos = (Cos) advSimpleEx;
			if (cos.getExpression1() instanceof Neg) {

				return new Cos(((Neg) cos.getExpression1()).getExpression1());
			}
		}
		return advSimpleEx.simplify();
	}
}
