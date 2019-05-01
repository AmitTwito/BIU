import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class UnaryExpression extends BaseExpression {

	private String expressionString;

	/**
	 * A constructor for the UnaryExpression class.
	 *
	 * @param argument
	 * @param expressionString
	 */
	public UnaryExpression(Expression argument, String expressionString) {
		super(argument);
		this.expressionString = expressionString;
	}

	/**
	 * Throws runtime
	 *
	 * @return
	 */
	@Override
	public Expression getSecondArgument() {
		throw new RuntimeException("Unsuccessful try to access a second argument of a unary expression, "
				+ "unary expressions do not have one.");
	}

	/**
	 * Returns a nice string representation of the expression.
	 *
	 * @return String representation of the expression.
	 */
	@Override
	public String toString() {
		return this.expressionString + OPEN_BRACKETS + getFirstArgument() + CLOSE_BRACKETS;
	}

	/**
	 * Evaluate the expression using the variable values provided
	 * in the assignment, and return the result.  If the expression
	 * contains a variable which is not in the assignment, an exception
	 * is thrown.
	 *
	 * @param assignment Map of variables and values for assigning in the expression.
	 * @return Evaluated result of the Expression after an assignment.
	 * @throws Exception if the expression contains a variable which is not in the assignment.
	 */
	@Override
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp = getFirstArgument();
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


	/**
	 * Returns a list of the variables in the expression.
	 *
	 * @return A list of the variables in the expression.
	 */
	@Override
	public List<String> getVariables() {
		return new ArrayList<>(new HashSet<>(getFirstArgument().getVariables()));
	}

}