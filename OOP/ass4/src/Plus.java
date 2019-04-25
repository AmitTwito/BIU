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

}
