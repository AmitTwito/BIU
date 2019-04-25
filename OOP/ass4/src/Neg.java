
public class Neg extends UnaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "-";

	public Neg(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Neg(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Neg(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}
}
