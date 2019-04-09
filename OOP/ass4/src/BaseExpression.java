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


	// abstract double evaluate();
	public abstract String toString();
	public abstract double calculate();


}
