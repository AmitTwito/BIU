import java.util.List;
import java.util.Map;

public class Cos extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Cos";

	public Cos(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Cos(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Cos(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}
}
