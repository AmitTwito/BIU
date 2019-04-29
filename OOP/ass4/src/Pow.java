import org.omg.PortableServer.POA;

import java.util.List;
import java.util.Map;

public class Pow extends BinaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "^";

	public Pow(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Pow(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Pow(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Pow(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Pow(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Pow(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Pow(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Pow(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Pow(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}


	public String toString(){
		return OPEN_BRACKETS + getExpression1() + EXPRESSION_STRING + getExpression2() + CLOSE_BRACKETS;
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
		return new Pow(exp1,exp2).evaluate();
	}

	public double evaluate() throws Exception {
		double value1 = getExpression1().evaluate();
		double value2 = getExpression2().evaluate();

		return Math.pow(value1, value2);
	}

	public Expression differentiate(String var) {
		Expression originalExp = new Pow(getExpression1(), getExpression2());
		Expression divExp = new Div(getExpression2(), getExpression1());
		Expression multExp1 = new Mult(getExpression1().differentiate(var), divExp);
		Expression multExp2 = new Mult(getExpression2().differentiate(var),
				new Log(new Num("e"), getExpression1()));
		Expression plusExp = new Plus(multExp1, multExp2);
		return new Mult(originalExp, plusExp);
	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Pow(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getExpression1().simplify();
		Expression simpleExp2 = getExpression2().simplify();

		if (canParseDouble(this.toString())) {
			return new Num(parseDouble(this.toString()));
		}

		if (canParseDouble(simpleExp2.toString())) {
			if (parseDouble(simpleExp2.toString()) == 0) {
				return new Num(1);
			}
			if (Double.parseDouble(simpleExp2.toString()) == 1) {
				return simpleExp1;
			}
		}
		try {
			return new Num(Math.pow(simpleExp1.evaluate(), simpleExp2.evaluate()));
		} catch (Exception e) {
			return new Pow(simpleExp1, simpleExp2);

		}
	}

	@Override
	public Expression advancedSimplify() {

		Expression advSimpleEx1 = getExpression1().advancedSimplify();
		Expression advSimpleEx2 = getExpression1().advancedSimplify();

		if(advSimpleEx1 instanceof  Pow) {
			Pow innerPow = (Pow)advSimpleEx1;
			return new Pow(innerPow.getExpression1(), new Mult(innerPow.getExpression2(), getExpression2()));
		}

		return new Pow(advSimpleEx1, advSimpleEx2).simplify();
	}
}
