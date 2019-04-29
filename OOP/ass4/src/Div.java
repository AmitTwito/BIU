import java.util.List;
import java.util.Map;

public class Div extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "/";

	public Div(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Div(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Div(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
		if (num == 0) {
			throw new IllegalArgumentException("Cannot divide by zero.");
		}
	}

	public Div(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Div(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Div(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Div(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Div(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Div(Expression expression, String var) {
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
		return new Div(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		if (getExpression2().evaluate() == 0) {
			throw new ArithmeticException("An error occurred on dividing calculation: "
					+ "can't divide by zero.");
		}
		return getExpression1().evaluate() / getExpression2().evaluate();
	}

	public Expression differentiate(String var) {
		Expression multExp1 = new Mult(getExpression1().differentiate(var), getExpression2());
		Expression multExp2 = new Mult(getExpression1(), getExpression2().differentiate(var));
		Expression minusExp = new Minus(multExp1, multExp2);
		Expression powExp = new Pow(getExpression2(), 2);
		Expression divExp = new Div(minusExp, powExp);
		return divExp;
	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Div(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getExpression1().simplify();
		Expression simpleExp2 = getExpression2().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (getExpression1().toString().equals(getExpression2().toString())) {
			return new Num(1);
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 1) {
				return simpleExp1;
			}
		}
		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Num(0);
			}
		}
		try {
			return new Num(simpleExp1.evaluate() / simpleExp2.evaluate());
		} catch (Exception e) {
			return new Div(simpleExp1, simpleExp2);
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getExpression1().advancedSimplify();
		Expression advSimpleEx2 = getExpression1().advancedSimplify();

		if (advSimpleEx1 instanceof Sin
				&& advSimpleEx2 instanceof Cos) {
			Sin sin = (Sin) advSimpleEx1;
			Cos cos = (Cos) advSimpleEx2;
			if (sin.getExpression1().toString().equals(cos.getExpression1().toString())) {
				return new Tan(sin.getExpression1());
			}
		}
		if (advSimpleEx1 instanceof Cos
				&& advSimpleEx2 instanceof Sin) {
			Cos cos = (Cos) advSimpleEx1;
			Sin sin = (Sin) advSimpleEx2;

			if (sin.getExpression1().toString().equals(cos.getExpression1().toString())) {
				return new Pow(new Tan(cos.getExpression1()), -1);
			}
		}


		return new Div(advSimpleEx1, advSimpleEx2).simplify();
	}
}
