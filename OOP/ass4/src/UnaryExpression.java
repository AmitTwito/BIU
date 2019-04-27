import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UnaryExpression extends BaseExpression {

	private String expressionString;

	public UnaryExpression(Expression expression, String expressionString) {
		super(expression);
		this.expressionString = expressionString;
	}

	@Override
	public String toString() {
		if (this.expressionString.equals(Sin.EXPRESSION_STRING)
				|| this.expressionString.equals(Cos.EXPRESSION_STRING)) {
			return this.expressionString + OPEN_BRACKETS + getExpression1() + CLOSE_BRACKETS;
		} else {
			return this.expressionString + getExpression1();
		}
	}

	@Override
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp = getExpression1();
		List<String> vars = getVariables();
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			Expression expression = new Num(entry.getValue());
			if (!vars.contains(entry.getKey())) {
				throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
			}
			exp = exp.assign(entry.getKey(), expression);
		}
		return (new UnaryExpression(exp, this.expressionString)).evaluate();

	}

	@Override
	public double evaluate() throws Exception {
		return calculateByMathFunction();
	}

	@Override
	public Expression differentiate(String var) {
		if(!this.getVariables().contains(var)){
			return new Num(0);
		}
		return differentiateByMathFunction(var);	}

	@Override
	public List<String> getVariables() {
		return new ArrayList<>(new HashSet<>(getExpression1().getVariables()));
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public Expression assign(String var, Expression expression) {

		Expression newExpression = getExpression1().assign(var, expression);
		if (this.expressionString.equals(Sin.EXPRESSION_STRING)) {
			return new Sin(newExpression);
		} else if (this.expressionString.equals(Cos.EXPRESSION_STRING)) {
			return new Cos(newExpression);
		} else {
			return new Neg(newExpression);
		}
	}

	@Override
	public Expression simplify() {
		try {
			double arg = getExpression1().evaluate();
			return new Num(arg);
		} catch (Exception e) {
			if (this.expressionString.equals(Sin.EXPRESSION_STRING)) {
				return new Sin(getExpression1().simplify());
			} else if (this.expressionString.equals(Cos.EXPRESSION_STRING)) {
				return new Cos(getExpression1().simplify());
			} else {
				return new Neg(getExpression1().simplify());
			}
		}
	}

	private double calculateByMathFunction() throws Exception {
		double value = getExpression1().evaluate();
		if (this.expressionString.equals(Sin.EXPRESSION_STRING)) {
			return Math.sin(value);
		} else if (this.expressionString.equals(Cos.EXPRESSION_STRING)) {
			return Math.cos(value);
		} else {
			return -1 * value;
		}
	}

	private Expression differentiateByMathFunction(String var) {
		Expression difExp = getExpression1().differentiate(var);
		if (this.expressionString.equals(Cos.EXPRESSION_STRING)) {
			Expression negExp = new Neg(new Sin(getExpression1()));
			return new Mult(negExp, difExp);
		} else if (this.expressionString.equals(Sin.EXPRESSION_STRING)) {
			Expression cosExp = new Cos(getExpression1());
			return new Mult(cosExp, difExp);
		} else {
			return new Neg(difExp);
		}
	}
}