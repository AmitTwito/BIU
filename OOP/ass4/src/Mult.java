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
		if (canParseDouble(getFirstArgumentExpression().toString())
				&& !canParseDouble(getSecondArgumentExpression().toString())) {
			if (this.getVariables().contains(getSecondArgumentExpression().toString())) {
				if (parseDouble(getFirstArgumentExpression().toString()) -
						(int) parseDouble(getFirstArgumentExpression().toString()) == 0) {
					return (int) parseDouble(getFirstArgumentExpression().toString()) + "" + getSecondArgumentExpression();
				}
				return getFirstArgumentExpression() + "" + getSecondArgumentExpression();
			}
		}
		if (canParseDouble(getSecondArgumentExpression().toString())
				&& !canParseDouble(getFirstArgumentExpression().toString())) {
			if (this.getVariables().contains(getFirstArgumentExpression().toString())) {
				if (parseDouble(getSecondArgumentExpression().toString()) -
						(int) parseDouble(getSecondArgumentExpression().toString()) == 0) {
					return (int) parseDouble(getSecondArgumentExpression().toString()) + "" + getFirstArgumentExpression();
				}
				return getSecondArgumentExpression() + "" + getFirstArgumentExpression();
			} else {
				return OPEN_BRACKETS + getSecondArgumentExpression() + SPACE
						+ EXPRESSION_STRING + SPACE + getFirstArgumentExpression() + CLOSE_BRACKETS;
			}
		}

		return super.toString();

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
		return new Mult(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getFirstArgumentExpression().evaluate();
		double value2 = getSecondArgumentExpression().evaluate();
		return value1 * value2;
	}

	public Expression differentiate(String var) {
		Expression multExp1 = new Mult(getFirstArgumentExpression().differentiate(var), getSecondArgumentExpression());
		Expression multExp2 = new Mult(getFirstArgumentExpression(), getSecondArgumentExpression().differentiate(var));
		return new Plus(multExp1, multExp2);
	}

	public Expression assign(String var, Expression expression) {
		Expression exp1 = getFirstArgumentExpression().assign(var, expression);
		Expression exp2 = getSecondArgumentExpression().assign(var, expression);
		return new Mult(exp1, exp2);
	}

	public Expression simplify() {
		Expression simpleExp1 = getFirstArgumentExpression().simplify();
		Expression simpleExp2 = getSecondArgumentExpression().simplify();

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
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression "
					+ " with the following reason: " + ae.getMessage());
		} catch (Exception e) {
			return new Mult(simpleExp1, simpleExp2);
		}
	}

	/**
	 * @return
	 */
	@Override
	public Expression advancedSimplify() {
		Expression advSimpleEx1 = getFirstArgumentExpression().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgumentExpression().advancedSimplify();

		//X*X = X^2
		if (advSimpleEx1.toString().equals(advSimpleEx2.toString())) {
			return new Pow(advSimpleEx1, 2);
		}
		//X * Num = Num * X
		if (advSimpleEx2 instanceof Num && !(advSimpleEx1 instanceof Num)) {
			return new Mult(advSimpleEx2, advSimpleEx1);
		}

		if (advSimpleEx1 instanceof Num) {
			//2(2x) => (2*2)x
			if (advSimpleEx2 instanceof Mult) {
				Mult mult = (Mult) advSimpleEx2;
				if (mult.getFirstArgumentExpression() instanceof Num) {
					return new Mult(new Mult(advSimpleEx1, mult.getFirstArgumentExpression()), mult.getSecondArgumentExpression());
				}
			}
		}
		if(advSimpleEx1 instanceof Pow && advSimpleEx2 instanceof Pow) {
			if()
		}

		return new Mult(advSimpleEx1, advSimpleEx2).simplify();
	}
}
