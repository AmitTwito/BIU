public class BinaryExpression extends BaseExpression {


	public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
		super(expression1, expression2, expressionString);
	}

	public String toString() {
		if (this.expressionString.contains(" ")) {
			return this.expression1 + this.expressionString + expression2;
		} else {
			return this.expression1 + SPACE + this.expressionString + SPACE + expression2;
		}
	}

	public double evaluate() throws Exception {
		if (this.expression1 instanceof Num && this.expression2 instanceof Num) {
			calculateByMathFunction();
		} else {
			throw new RuntimeException("Cannot use the method evaluate()"
					+ "if both expressions are not Num");
		}
	}



}
