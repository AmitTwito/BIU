

public class Neg extends UnaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "-";

	public Neg(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Neg(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Neg(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}

	@Override
	public String toString() {
		return OPEN_BRACKETS + EXPRESSION_STRING + getExpression1() + CLOSE_BRACKETS;
	}


	public double evaluate() throws Exception {
		double value = getExpression1().evaluate();
		return -1 * value;
	}

	public Expression assign(String var, Expression expression) {

		Expression newExpression = getExpression1().assign(var, expression);
		return new Neg(newExpression);
	}

	public Expression differentiate(String var) {
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
		Expression difExp = getExpression1().differentiate(var);
		return new Neg(difExp);
	}

	public Expression simplify() {
		try {
			double arg = getExpression1().evaluate();
			return new Num(arg);
		} catch (Exception e) {
			return new Neg(getExpression1().simplify());
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx = advancedSimplify();


		return new Neg(advSimpleEx);
	}
}
