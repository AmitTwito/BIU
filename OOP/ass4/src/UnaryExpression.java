public class UnaryExpression extends BaseExpression {

	public UnaryExpression(Expression expression, String expressionString) {
		super(expression, expressionString);
	}

	public String toString() {
		return this.expressionString + OPEN_BRACKETS + this.expression1 + CLOSE_BRACKETS;
	}

	public double evaluate() throws Exception {
		if (this.expression1 instanceof Num) {
			return ;
		} else {
			throw new RuntimeException("Cannot use the method evaluate()"
					+ "if both expressions are not Num");
		}
	}
}
