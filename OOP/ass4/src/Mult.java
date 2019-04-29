import java.util.List;
import java.util.Map;

public class Mult extends BinaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "*";

	public Mult(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Mult(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Mult(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Mult(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Mult(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Mult(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Mult(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Mult(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Mult(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}


	public String toString() {
		if (canParseDouble(getExpression1().toString())
				&& !canParseDouble(getExpression2().toString())) {
			if (this.getVariables().contains(getExpression2().toString())) {
				if (parseDouble(getExpression1().toString()) -
						(int) parseDouble(getExpression1().toString()) == 0) {
					return (int) parseDouble(getExpression1().toString()) + "" + getExpression2();
				}
				return getExpression1() + "" + getExpression2();
			}
		}
		if (canParseDouble(getExpression2().toString())
				&& !canParseDouble(getExpression1().toString())) {
			if (this.getVariables().contains(getExpression1().toString())) {
				if (parseDouble(getExpression2().toString()) -
						(int) parseDouble(getExpression2().toString()) == 0) {
					return (int) parseDouble(getExpression2().toString()) + "" + getExpression1();
				}
				return getExpression2() + "" + getExpression1();
			} else {
				return OPEN_BRACKETS + getExpression2() + SPACE
						+ EXPRESSION_STRING + SPACE + getExpression1() + CLOSE_BRACKETS;
			}
		}

		return super.toString();

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
		return new Mult(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getExpression1().evaluate();
		double value2 = getExpression2().evaluate();
		return value1 * value2;
	}

	public Expression differentiate(String var) {
		Expression multExp1 = new Mult(getExpression1().differentiate(var), getExpression2());
		Expression multExp2 = new Mult(getExpression1(), getExpression2().differentiate(var));
		return new Plus(multExp1, multExp2);
	}

	public Expression assign(String var, Expression expression) {
		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Mult(exp1, exp2);
	}

	public Expression simplify() {
		Expression simpleExp1 = getExpression1().simplify();
		Expression simpleExp2 = getExpression2().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 1) {
				return simpleExp2;
			}
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Num(0);
			}
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 1) {
				return simpleExp1;
			}
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(0);
			}
		}

		try {
			return new Num(simpleExp1.evaluate() * simpleExp2.evaluate());
		} catch (Exception e) {
			return new Mult(simpleExp1, simpleExp2);
		}
	}

}
