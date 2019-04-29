import java.util.List;
import java.util.Map;

public class Plus extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "+";

	public Plus(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Plus(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Plus(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Plus(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Plus(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Plus(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Plus(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Plus(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Plus(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}


	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp1 = getExpression1();
		Expression exp2 = getExpression2();
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
		return new Plus(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {

		return getExpression1().evaluate() + getExpression2().evaluate();
	}

	public Expression differentiate(String var) {
		return new Plus(getExpression1().differentiate(var), getExpression2().differentiate(var));

	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Plus(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getExpression1().simplify();
		Expression simpleExp2 = getExpression2().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 0) {
				return simpleExp2;
			}
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 0) {
				return simpleExp1;
			}
		}
		try {
			return new Num(simpleExp1.evaluate() + simpleExp2.evaluate());
		} catch (Exception e) {
			return new Plus(simpleExp1, simpleExp2);

		}
	}

	public Expression advancedSimplify() {
		Expression simpleEx = simplify();
		if (simpleEx instanceof Plus) {
			Plus plus = (Plus) simpleEx;
			if (plus.getExpression2() instanceof Mult && plus.getExpression1() instanceof Num) {
				simpleEx = new Plus(plus.getExpression1(), plus.getExpression2());
			}
		}


		return simpleEx;
	}
}
