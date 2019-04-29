import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class UnaryExpression extends BaseExpression {

	private String expressionString;

	public UnaryExpression(Expression expression, String expressionString) {
		super(expression);
		this.expressionString = expressionString;
	}

	@Override
	protected Expression getExpression2() {
		throw new NullPointerException("Unary expressions do not have a second argument.");
	}

	@Override
	public String toString() {
		return this.expressionString + OPEN_BRACKETS + getExpression1() + CLOSE_BRACKETS;
	}

	@Override
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp = getExpression1();
		List<String> vars = getVariables();
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			Expression expression = new Num(entry.getValue());
			if (!vars.contains(entry.getKey())) {
				throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
			}
			exp = exp.assign(entry.getKey(), expression);
		}
		return exp.evaluate();
	}


	@Override
	public abstract Expression differentiate(String var);


	@Override
	public List<String> getVariables() {
		return new ArrayList<>(new HashSet<>(getExpression1().getVariables()));
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override
	public abstract Expression assign(String var, Expression expression);

	@Override
	public abstract Expression simplify();

}