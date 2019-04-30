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
		Expression exp1 = getFirstArgumentExpression();
		Expression exp2 = getSecondArgumentExpression();
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
		if (getSecondArgumentExpression().evaluate() == 0) {
			throw new ArithmeticException("An error occurred on dividing calculation: "
					+ "can't divide by zero.");
		}
		return getFirstArgumentExpression().evaluate() / getSecondArgumentExpression().evaluate();
	}

	public Expression differentiate(String var) {
		Expression multExp1 = new Mult(getFirstArgumentExpression().differentiate(var), getSecondArgumentExpression());
		Expression multExp2 = new Mult(getFirstArgumentExpression(), getSecondArgumentExpression().differentiate(var));
		Expression minusExp = new Minus(multExp1, multExp2);
		Expression powExp = new Pow(getSecondArgumentExpression(), 2);
		Expression divExp = new Div(minusExp, powExp);
		return divExp;
	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getFirstArgumentExpression().assign(var, expression);
		Expression exp2 = getSecondArgumentExpression().assign(var, expression);
		return new Div(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getFirstArgumentExpression().simplify();
		Expression simpleExp2 = getSecondArgumentExpression().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (getFirstArgumentExpression().toString().equals(getSecondArgumentExpression().toString())) {
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
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Div(simpleExp1, simpleExp2);
		}
	}

	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgumentExpression().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgumentExpression().advancedSimplify();

		if (advSimpleEx1 instanceof Sin
				&& advSimpleEx2 instanceof Cos) {
			Sin sin = (Sin) advSimpleEx1;
			Cos cos = (Cos) advSimpleEx2;
			if (sin.getFirstArgumentExpression().toString().equals(cos.getFirstArgumentExpression().toString())) {
				return new Tan(sin.getFirstArgumentExpression());
			}
		}
		if (advSimpleEx1 instanceof Cos
				&& advSimpleEx2 instanceof Sin) {
			Cos cos = (Cos) advSimpleEx1;
			Sin sin = (Sin) advSimpleEx2;

			if (sin.getFirstArgumentExpression().toString().equals(cos.getFirstArgumentExpression().toString())) {
				return new Pow(new Tan(cos.getFirstArgumentExpression()), -1);
			}
		}


		return new Div(advSimpleEx1, advSimpleEx2).simplify();
	}
}
