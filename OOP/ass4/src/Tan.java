public class Tan extends UnaryExpression implements Expression{

	public static final String EXPRESSION_STRING = "Cos";

	public Tan(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Tan(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Tan(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}


	public double evaluate() throws Exception {
		double value = getExpression1().evaluate();
		return Math.tan(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getExpression1().assign(var, expression);
		return new Tan(newExpression);
	}
	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}

		Expression innerDiff = getExpression1().differentiate(var);
		return new Div(1, new Pow(new Cos(getExpression1()),2));
	}

	public Expression simplify() {
		try {
			double arg = getExpression1().evaluate();
			return new Num(arg);
		} catch (Exception e) {
			return new Tan(getExpression1().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		return null;
	}
}
