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
		return new Plus(exp1, exp2).evaluate();
	}

	public double evaluate() throws Exception {

		return getFirstArgumentExpression().evaluate() + getSecondArgumentExpression().evaluate();
	}

	public Expression differentiate(String var) {
		return new Plus(getFirstArgumentExpression().differentiate(var), getSecondArgumentExpression().differentiate(var));

	}

	public Expression assign(String var, Expression expression) {

		Expression exp1 = getFirstArgumentExpression().assign(var, expression);
		Expression exp2 = getSecondArgumentExpression().assign(var, expression);
		return new Plus(exp1, exp2);

	}

	public Expression simplify() {
		Expression simpleExp1 = getFirstArgumentExpression().simplify();
		Expression simpleExp2 = getSecondArgumentExpression().simplify();

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
		} catch (ArithmeticException ae) {
			throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
		} catch (Exception e) {
			return new Plus(simpleExp1, simpleExp2);
		}
	}

	public Expression advancedSimplify() {
		//4x +6x
		//1+ (4x + 6x)
		// ((2x) + (2 + ((4x) + 1)))
		Expression advSimpleEx1 = getFirstArgumentExpression().advancedSimplify();
		Expression advSimpleEx2 = getFirstArgumentExpression().advancedSimplify();

		//X + X = 2X
		if (advSimpleEx1.toString().equals(advSimpleEx2.toString())) {
			return new Mult(2, advSimpleEx1);
		}
		// Num + X => X + Num
		if (advSimpleEx1 instanceof Num && !(advSimpleEx2 instanceof Num)) {
			return new Plus(advSimpleEx2, advSimpleEx1);
		}
		//((4x +1) + 2) => (4x + (1+2)).
		if (advSimpleEx1 instanceof Plus && advSimpleEx2 instanceof Num) {
			Plus plusExp = (Plus) advSimpleEx1;
			if (plusExp.getSecondArgumentExpression() instanceof Num) {
				return new Plus(plusExp.getFirstArgumentExpression(), new Plus(plusExp.getSecondArgumentExpression(), advSimpleEx2));
			}
		}
		//(2x + (4x + 1))
		if (advSimpleEx1 instanceof Mult && advSimpleEx2 instanceof Plus) {
			Plus plusExp = (Plus) advSimpleEx2;
			if (plusExp.getSecondArgumentExpression() instanceof Num) {
				return new Plus(new Plus(advSimpleEx1, plusExp.getFirstArgumentExpression()), plusExp.getSecondArgumentExpression());
			}

		}
		//(4x + 6x) = ((4+6)x)
		if (advSimpleEx1 instanceof Mult
				&& advSimpleEx2 instanceof Mult) {
			Mult firstMult = (Mult) advSimpleEx1;
			Mult secondMult = (Mult) advSimpleEx2;
			if (firstMult.getSecondArgumentExpression().toString().equals(secondMult.getSecondArgumentExpression().toString())) {
				return new Mult(new Plus(firstMult.getFirstArgumentExpression(),
						secondMult.getFirstArgumentExpression()), firstMult.getSecondArgumentExpression());
			}
		}



		return new Plus(advSimpleEx1, advSimpleEx2).simplify();
	}
}
