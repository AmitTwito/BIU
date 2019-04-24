import java.util.Map;

public abstract class BaseExpression {

	public static final String OPEN_BRACKETS = "(";
	public static final String CLOSE_BRACKETS = ")";
	public static final String SPACE = " ";

	protected Expression expression1;
	protected Expression expression2;
	protected String expressionString;

	public BaseExpression(Expression expression, String expressionString) {
		this.expression1 = expression;
		this.expressionString = expressionString;
	}

	public BaseExpression(Expression expression1, Expression expression2, String expressionString) {
		this.expression1 = expression1;
		this.expression2 = expression2;
		this.expressionString = expressionString;
	}

	public Expression getExpression1() {
		return expression1;
	}

	public Expression getExpression2() {
		return expression2;
	}

	protected abstract double evaluate() throws Exception;
	//public abstract double evaluate(Map<String, Double> assignment) throws Exception;
	public abstract String toString();

}
