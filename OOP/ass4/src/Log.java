import java.util.List;
import java.util.Map;

public class Log extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Log";

	public Log(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Log(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Log(String var, double num) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);

		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	public Log(double num, String var) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
	}

	public Log(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
		if (num1 <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
		if (num2 <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	public Log(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base or zero.");
		}
	}

	public Log(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
		if (num <= 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number or a zero.");
		}
	}

	public Log(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Log(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}

	@Override
	public String toString() {
		return EXPRESSION_STRING + OPEN_BRACKETS + getFirstArgumentExpression()
				+ COMMA_WITH_SPACE + getSecondArgumentExpression() + CLOSE_BRACKETS;
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
		return new Log(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getFirstArgumentExpression().evaluate();
		double value2 = getSecondArgumentExpression().evaluate();
		if (Math.log(value1) == Double.NaN
				|| Math.log(value1) == Double.NaN
				|| Double.isInfinite(Math.log(value1))
				|| Double.isInfinite(Math.log(value2))) {
			throw new ArithmeticException("An error occurred on log calculation: "
					+ "one or both arguments caused the result to be infinity or NaN (not a number).");
		}
		return Math.log(value2) / Math.log(value1);
	}

	public Expression differentiate(String var) {
		Expression topLogExp = new Log(new Num("e"), getSecondArgumentExpression());
		Expression bottomLogExp = new Log(new Num("e"), getFirstArgumentExpression());
		return new Div(topLogExp, bottomLogExp).differentiate(var);
	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getFirstArgumentExpression().assign(var, expression);
		Expression exp2 = getSecondArgumentExpression().assign(var, expression);
		return new Log(exp1, exp2);

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
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(1);
			}
		}
		try {
			return new Num(Math.log(simpleExp2.evaluate()) / Math.log(simpleExp1.evaluate()));
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Log(simpleExp1, simpleExp2);
		}
	}

	/**
	 * @return
	 */
	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgumentExpression().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgumentExpression().advancedSimplify();

		if (getSecondArgumentExpression() instanceof Pow) {
			Pow pow = (Pow) getSecondArgumentExpression();
			return new Mult(pow.getSecondArgumentExpression(), new Log(getFirstArgumentExpression(), pow.getFirstArgumentExpression()));
		}

		return new Log(advSimpleEx1, advSimpleEx2);
	}
}
