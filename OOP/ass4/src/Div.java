import java.util.List;
import java.util.Map;

public class Div extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "/";

	public Div(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Div(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Div(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Div(double num, String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Div(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Div(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Div(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Div(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Div(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}

	@Override
	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Div(exp1, exp2);
	}
}
