

public class Sin extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Sin";

	public Sin(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Sin(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Sin(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}
}
