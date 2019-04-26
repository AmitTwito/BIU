import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BinaryExpression extends BaseExpression {

	private String expressionString;

	public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
		super(expression1, expression2);
		this.expressionString = expressionString;
	}

	@Override
	public String toString() {
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)
				|| this.expressionString.equals(Minus.EXPRESSION_STRING)
				|| this.expressionString.equals(Mult.EXPRESSION_STRING)
				|| this.expressionString.equals(Div.EXPRESSION_STRING)) {
			return OPEN_BRACKETS + getExpression1() + SPACE
					+ this.expressionString + SPACE + getExpression2() + CLOSE_BRACKETS;
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
			return getExpression1() + this.expressionString + getExpression2();
		} else {
			return this.expressionString + OPEN_BRACKETS + getExpression1()
					+ COMMA_WITH_SPACE + getExpression2() + CLOSE_BRACKETS;
		}
	}

	@Override
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp1 = getExpression1();
		Expression exp2 = getExpression2();
		List<String> vars = getVariables();
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			Expression expression = new Num(entry.getValue());
			if (!vars.contains(entry.getKey())) {
				throw new Exception("The var " + entry.getKey() + " does not exist in this expression.");
			}
			exp1 = exp1.assign(entry.getKey(), expression);
			exp2 = exp2.assign(entry.getKey(), expression);
		}
		return (new BinaryExpression(exp1, exp2, this.expressionString)).evaluate();
	}

	@Override
	public double evaluate() throws Exception {
		return calculateByMathFunction();
	}

	@Override
	public Expression differentiate(String var) {
		return differentiateByMathFunction(var);
	}

	@Override
	public List<String> getVariables() {
		List<String> varList1 = getExpression1().getVariables();
		List<String> varList2 = getExpression2().getVariables();
		varList1.addAll(varList2);
		return new ArrayList<>(new HashSet<>(varList1));
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public Expression assign(String var, Expression expression) {
		return assignByMathFunction(var, expression);
	}


	@Override
	public Expression simplify() {
		return simplifyByMathFunction();
	}

	private Expression simplifyByMathFunction() {
		if (tryParseDouble(this.toString())) {
			return new Num(Double.parseDouble(this.toString()));
		}
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
			try {
				if (tryParseDouble(getExpression1().toString())) {
					if (Double.parseDouble(getExpression1().toString()) == 0) {
						return getExpression2().simplify();
					}
				}
				if (tryParseDouble(getExpression2().toString())) {
					if (Double.parseDouble(getExpression2().toString()) == 0) {
						return getExpression1().simplify();
					}
				}
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Plus(getExpression1().simplify(), getExpression2().simplify());
			}
		} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {

			if (getExpression1().toString().equals(getExpression2().toString())) {
				return new Num(0);
			}
			try {
				if (tryParseDouble(getExpression1().toString())) {
					if (Double.parseDouble(getExpression1().toString()) == 0) {
						return new Neg(getExpression2().simplify());
					}
				}
				if (tryParseDouble(getExpression2().toString())) {
					if (Double.parseDouble(getExpression2().toString()) == 0) {
						return getExpression1().simplify();
					}
				}
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Minus(getExpression1().simplify(), getExpression2().simplify());
			}
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			try {
				if (tryParseDouble(getExpression1().toString())) {
					if (Double.parseDouble(getExpression1().toString()) == 1) {
						return getExpression2().simplify();
					}
				}
				if (tryParseDouble(getExpression2().toString())) {
					if (Double.parseDouble(getExpression2().toString()) == 1) {
						return getExpression1().simplify();
					}
				}
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Mult(getExpression1().simplify(), getExpression2().simplify());
			}
		} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
			if (getExpression1().toString().equals(getExpression2().toString())) {
				return new Num(1);
			}
			try {
				if (tryParseDouble(getExpression2().toString())) {
					if (Double.parseDouble(getExpression2().toString()) == 1) {
						return getExpression1().simplify();
					}
				}
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Div(getExpression1().simplify(), getExpression2().simplify());
			}
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
			try {
				if (tryParseDouble(getExpression2().toString())) {
					if (Double.parseDouble(getExpression2().toString()) == 0) {
						return new Num(1);
					}
					if (Double.parseDouble(getExpression2().toString()) == 1) {
						return getExpression1().simplify();
					}
				}
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Pow(getExpression1().simplify(), getExpression2().simplify());
			}
		} else {
			if (getExpression1().toString().equals(getExpression2().toString())) {
				return new Num(1);
			}
			try {
				return new Num(this.evaluate());
			} catch (Exception e) {
				return new Log(getExpression1().simplify(), getExpression2().simplify());
			}
		}
	}

	private double calculateByMathFunction() throws Exception {
		double value1 = getExpression1().evaluate();
		double value2 = getExpression2().evaluate();
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
			return value1 + value2;
		} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
			return value1 - value2;
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			return value1 * value2;
		} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
			return value1 / value2;
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
			return Math.pow(value1, value2);
		} else {
			return Math.log(value2) / Math.log(value1);
		}
	}

	private Expression differentiateByMathFunction(String var) {
		Expression difExp1 = getExpression1().differentiate(var);
		Expression difExp2 = getExpression2().differentiate(var);
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
			return new Plus(difExp1, difExp2);
		} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
			return new Minus(difExp1, difExp2);
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			Expression multExp1 = new Mult(difExp1, getExpression2());
			Expression multExp2 = new Mult(getExpression1(), difExp2);
			return new Plus(multExp1, multExp2);
		} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
			Expression multExp1 = new Mult(difExp1, getExpression2());
			Expression multExp2 = new Mult(getExpression1(), difExp2);
			Expression minusExp = new Minus(multExp1, multExp2);
			Expression powExp = new Pow(getExpression2(), 2);
			Expression divExp = new Div(minusExp, powExp);
			return divExp;
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
			try {
				double powForDif = getExpression2().evaluate();
				if (powForDif == 1) {
					return difExp1;
				}
				if (powForDif == 0) {
					return new Num(0);
				}
				Expression powExp = new Pow(getExpression1(), powForDif - 1);
				Expression multExp = new Mult(powForDif, powExp);
				return new Pow(multExp, difExp1);
			} catch (Exception e) {
				//e^x ' = e^x * (x') * log(e,e)
				Expression powExp = new Pow(getExpression1(), getExpression2());
				Expression multExp = new Mult(powExp, difExp2);
				return new Mult(multExp, new Log("e", getExpression1()));
				//System.out.println("At differentiate of Pow function: " + e.getMessage());
			}
		} else {
			try {
				double base = getExpression1().evaluate();
				Expression divExp = new Div(1, new Mult(getExpression2(), new Log(new Num("e"), base)));
				Expression multExp = new Mult(divExp, difExp2);
				return multExp;
			} catch (Exception e) {
				System.out.println("At differentiate of Log function: " + e.getMessage());
				return null;
			}
		}
	}

	private Expression assignByMathFunction(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
			return new Plus(exp1, exp2);
		} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
			return new Minus(exp1, exp2);
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			return new Mult(exp1, exp2);
		} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
			return new Div(exp1, exp2);
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
			return new Pow(exp1, exp2);
		} else {
			return new Log(exp1, exp2);
		}
	}

	private boolean tryParseDouble(String str) {

		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
