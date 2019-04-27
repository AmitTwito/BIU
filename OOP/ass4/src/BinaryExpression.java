import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class BinaryExpression extends BaseExpression {

	private String expressionString;

	public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
		super(expression1, expression2);

		if (expressionString.equals(Div.EXPRESSION_STRING)) {
			try {
				if (getExpression2().evaluate() == 0) {
					throw new IllegalArgumentException("Cannot divide by zero.");
				}
			} catch (Exception e) {

			}
		}
		if (expressionString.equals(Log.EXPRESSION_STRING)) {
			try {
				if (getExpression1().evaluate() < 0) {
					throw new IllegalArgumentException("Cannot calculate log with a negative base.");
				}
			} catch (Exception e) {

			}

			try {
				if (getExpression2().evaluate() < 0) {
					throw new IllegalArgumentException("Cannot calculate log of a negative number.");
				}
			} catch (Exception e) {

			}
		}
		this.expressionString = expressionString;
	}

	@Override
	public String toString() {
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)
				|| this.expressionString.equals(Minus.EXPRESSION_STRING)
				|| this.expressionString.equals(Div.EXPRESSION_STRING)) {
			return OPEN_BRACKETS + getExpression1() + SPACE
					+ this.expressionString + SPACE + getExpression2() + CLOSE_BRACKETS;
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			if (tryParseDouble(getExpression1().toString())
					&& !tryParseDouble(getExpression2().toString())) {
				if (this.getVariables().contains(getExpression2().toString())) {
					return getExpression1() + "" + getExpression2();
				} else {
					return OPEN_BRACKETS + getExpression1() + SPACE
							+ this.expressionString + SPACE + getExpression2() + CLOSE_BRACKETS;
				}
			}
			if (tryParseDouble(getExpression2().toString())
					&& !tryParseDouble(getExpression1().toString())) {
				if (this.getVariables().contains(getExpression1().toString())) {
					return getExpression2() + "" + getExpression1();
				} else {
					return OPEN_BRACKETS + getExpression2() + SPACE
							+ this.expressionString + SPACE + getExpression1() + CLOSE_BRACKETS;
				}
			}

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
				throw new Exception("Can't assign " + entry.getValue()
						+ " to var " + entry.getKey()
						+ " because it does not exist in this expression.");
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
		if (!this.getVariables().contains(var)) {
			return new Num(0);
		}
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
		try {
			return new Num(this.evaluate());
		} catch (Exception e) {
			Expression simpleExp1 = getExpression1().simplify();
			Expression simpleExp2 = getExpression2().simplify();
			if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
				if (tryParseDouble(simpleExp1.toString())) {
					if (parseDouble(simpleExp1.toString()) == 0) {
						return simpleExp2;
					}
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 0) {
						return simpleExp1;
					}
				}
				return new Plus(simpleExp1, simpleExp2);

			} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {

				if (getExpression1().toString().equals(getExpression2().toString())) {
					return new Num(0);
				}
				if (tryParseDouble(simpleExp1.toString())) {
					if (parseDouble(simpleExp1.toString()) == 0) {
						return new Neg(simpleExp2);
					}
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 0) {
						return simpleExp1;
					}
				}

				return new Minus(simpleExp1, simpleExp2);

			} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
				if (tryParseDouble(simpleExp1.toString())) {
					if (parseDouble(simpleExp1.toString()) == 1) {
						return simpleExp2;
					}
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 1) {
						return simpleExp1;
					}
				}
				if (tryParseDouble(simpleExp1.toString())) {
					if (Double.parseDouble(simpleExp1.toString()) == 0) {
						return new Num(0);
					}
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 0) {
						return new Num(0);
					}
				}
				return new Mult(simpleExp1, simpleExp2);
			} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
				if (getExpression1().toString().equals(getExpression2().toString())) {
					return new Num(1);
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 1) {
						return simpleExp1;
					}
				}
				if (tryParseDouble(simpleExp1.toString())) {
					if (parseDouble(simpleExp1.toString()) == 0) {
						return new Num(0);
					}
				}
				return new Div(simpleExp1, simpleExp2);
			} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 0) {
						return new Num(1);
					}
					if (Double.parseDouble(simpleExp2.toString()) == 1) {
						return simpleExp1;
					}
				}
				return new Pow(simpleExp1, simpleExp2);
			} else {
				if (getExpression1().toString().equals(getExpression2().toString())) {
					return new Num(1);
				}
				if (tryParseDouble(simpleExp2.toString())) {
					if (parseDouble(simpleExp2.toString()) == 0) {
						return new Num(1);
					}
				}
				return new Log(simpleExp1, simpleExp2);
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
		if (this.expressionString.equals(Plus.EXPRESSION_STRING)) {
			return new Plus(getExpression1().differentiate(var), getExpression2().differentiate(var));
		} else if (this.expressionString.equals(Minus.EXPRESSION_STRING)) {
			return new Minus(getExpression1().differentiate(var), getExpression2().differentiate(var));
		} else if (this.expressionString.equals(Mult.EXPRESSION_STRING)) {
			Expression multExp1 = new Mult(getExpression1().differentiate(var), getExpression2());
			Expression multExp2 = new Mult(getExpression1(), getExpression2().differentiate(var));
			return new Plus(multExp1, multExp2);
		} else if (this.expressionString.equals(Div.EXPRESSION_STRING)) {
			Expression multExp1 = new Mult(getExpression1().differentiate(var), getExpression2());
			Expression multExp2 = new Mult(getExpression1(), getExpression2().differentiate(var));
			Expression minusExp = new Minus(multExp1, multExp2);
			Expression powExp = new Pow(getExpression2(), 2);
			Expression divExp = new Div(minusExp, powExp);
			return divExp;
		} else if (this.expressionString.equals(Pow.EXPRESSION_STRING)) {

			Expression originalExp = new Pow(getExpression1(), getExpression2());
			Expression divExp = new Div(getExpression2(), getExpression1());
			Expression multExp1 = new Mult(getExpression1().differentiate(var), divExp);
			Expression multExp2 = new Mult(getExpression2().differentiate(var),
					new Log(new Num("e"), getExpression1()));
			Expression plusExp = new Plus(multExp1, multExp2);
			return new Mult(originalExp, plusExp);
		} else {

			Expression topLogExp = new Log(new Num("e"), getExpression2());
			Expression bottomLogExp = new Log(new Num("e"), getExpression1());
			return new Div(topLogExp, bottomLogExp).differentiate(var);
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
			if (str.equals(Num.E_CONST_STRING) || str.equals(Num.PI_CONST_STRING)) {
				return true;
			}
			return false;
		}
	}

	private double parseDouble(String str) {
		if (str.equals(Num.E_CONST_STRING)) {
			return Math.E;
		}
		if (str.equals(Num.PI_CONST_STRING)) {
			return Math.PI;
		}
		return Double.parseDouble(str);
	}
}
