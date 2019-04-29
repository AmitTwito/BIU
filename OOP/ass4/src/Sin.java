
public class Sin extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Sin";

	public Sin(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Sin(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Sin(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}


	public double evaluate() throws Exception {
		double value = getExpression1().evaluate();
		return Math.sin(value);
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getExpression1().assign(var, expression);
		return new Sin(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getExpression1().differentiate(var);
		Expression cosExp = new Cos(getExpression1());
		return new Mult(cosExp, difExp);
	}

	public Expression simplify() {
		try {
			double arg = getExpression1().evaluate();
			return new Num(arg);
		} catch (Exception e) {
			return new Neg(getExpression1().simplify());
		}
	}


}
