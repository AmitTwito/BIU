import java.util.List;
import java.util.Map;

public class Minus extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "-";

	public Minus(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Minus(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Minus(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Minus(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Minus(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Minus(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Minus(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Minus(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Minus(Expression expression, String var) {
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
		return new Minus(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getFirstArgumentExpression().evaluate();
		double value2 = getSecondArgumentExpression().evaluate();

		return value1 - value2;
	}

	public Expression differentiate(String var) {
		return new Minus(getFirstArgumentExpression().differentiate(var), getSecondArgumentExpression().differentiate(var));

	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getFirstArgumentExpression().assign(var, expression);
		Expression exp2 = getSecondArgumentExpression().assign(var, expression);
		return new Minus(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getFirstArgumentExpression().simplify();
		Expression simpleExp2 = getSecondArgumentExpression().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (getFirstArgumentExpression().toString().equals(getSecondArgumentExpression().toString())) {
			return new Num(0);
		}
		if (canParseDouble(simpleExp1.toString())) {
			if (parseDouble(simpleExp1.toString()) == 0) {
				return new Neg(simpleExp2);
			}
		}
		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 0) {
				return simpleExp1;
			}
		}
		try {
			return new Num(simpleExp1.evaluate() - simpleExp2.evaluate());
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Minus(simpleExp1, simpleExp2);
		}
	}

	@Override
	public Expression advancedSimplify() {

		Expression advSimpleEx1 = getFirstArgumentExpression().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgumentExpression().advancedSimplify();


		return new Log(advSimpleEx1, advSimpleEx2);
	}
}
