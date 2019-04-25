import java.util.List;
import java.util.Map;

public abstract class BaseExpression {

	public static final String OPEN_BRACKETS = "(";
	public static final String CLOSE_BRACKETS = ")";
	public static final String SPACE = " ";
	public static final String COMMA = ", ";

	private Expression expression1;
	private Expression expression2;


	public BaseExpression(Expression expression) {
		this.expression1 = expression;
	}

	public BaseExpression(Expression expression1, Expression expression2) {
		this.expression1 = expression1;
		this.expression2 = expression2;
	}

	protected Expression getExpression1() {
		return this.expression1;
	}
	protected Expression getExpression2() {
		return this.expression2;
	}

	public abstract double evaluate() throws Exception;

	public abstract Expression differentiate(String var);

	public abstract List<String> getVariables();

	public abstract double evaluate(Map<String, Double> assignment) throws Exception;

	public abstract String toString();

	public abstract Expression assign(String var, Expression expression);

	public abstract Expression simplify();
}
