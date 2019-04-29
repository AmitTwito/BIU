import javax.swing.text.EditorKit;
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

		if (num < 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number.");
		}
	}

	public Log(double num, String var) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
		if (num < 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base.");
		}
	}

	public Log(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
		if (num1 < 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base.");
		}
		if (num2 < 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number.");
		}
	}

	public Log(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
		if (num < 0) {
			throw new IllegalArgumentException("Cannot calculate log with a negative base.");
		}
	}

	public Log(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
		if (num < 0) {
			throw new IllegalArgumentException("Cannot calculate log of a negative number.");
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
		return EXPRESSION_STRING + OPEN_BRACKETS + getExpression1()
				+ COMMA_WITH_SPACE + getExpression2() + CLOSE_BRACKETS;
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
		return new Log(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getExpression1().evaluate();
		double value2 = getExpression2().evaluate();
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
		Expression topLogExp = new Log(new Num("e"), getExpression2());
		Expression bottomLogExp = new Log(new Num("e"), getExpression1());
		return new Div(topLogExp, bottomLogExp).differentiate(var);
	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Log(exp1, exp2);

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
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(1);
			}
		}
		try {
			return new Num(Math.log(simpleExp2.evaluate()) / Math.log(simpleExp1.evaluate()));
		} catch (Exception e) {
			return new Log(simpleExp1, simpleExp2);
		}
	}
}
