import java.util.List;
import java.util.Map;

public class Sin extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Sin";

	public Sin(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Sin(String var) {
		super(var, EXPRESSION_STRING);
	}

	public Sin(double num) {
		super(num, EXPRESSION_STRING);
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public Expression assign(String var, Expression expression) {

		Expression exp = getExpression1().assign(var, expression);
		return new Sin(exp);
	}
}
