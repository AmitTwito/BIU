import java.util.List;
import java.util.Map;

public class Log extends BinaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Log";

	public Log(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Log(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Log(String var, double num) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
	}

	public Log(double num, String var) {
		super(new Num(num), new Var(var), EXPRESSION_STRING);
	}

	public Log(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}

	public Log(double num, Expression expression) {
		super(new Num(num), expression, EXPRESSION_STRING);
	}

	public Log(Expression expression, double num) {
		super(expression, new Num(num), EXPRESSION_STRING);
	}

	public Log(String var, Expression expression) {
		super(new Var(var), expression, EXPRESSION_STRING);
	}

	public Log(Expression expression, String var) {
		super(expression, new Var(var), EXPRESSION_STRING);
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public Expression assign(String var, Expression expression) {

		Expression exp1 = getExpression1().assign(var, expression);
		Expression exp2 = getExpression2().assign(var, expression);
		return new Log(exp1, exp2);
	}
}
