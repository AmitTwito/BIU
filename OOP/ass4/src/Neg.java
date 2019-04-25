import java.util.List;
import java.util.Map;

public class Neg extends UnaryExpression implements Expression {


	public static final String EXPRESSION_STRING = "-";

	public Neg(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Neg(String var) {
		super(var, EXPRESSION_STRING);
	}

	public Neg(double num) {
		super(num, EXPRESSION_STRING);
	}


	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public Expression assign(String var, Expression expression) {

		Expression exp = getExpression1().assign(var, expression);
		return new Neg(exp);
	}
}
