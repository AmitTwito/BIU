import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class BinaryExpression extends BaseExpression {

	private String expressionFunctionString;

	/**
	 * A constructor for the BinaryExpression class.
	 *
	 * @param firstArgument    The first argument expression.
	 * @param secondArgument   The second argument expression.
	 * @param expressionString The string (operator or function) that represents the main expression of this binary
	 *                         expression.
	 */
	public BinaryExpression(Expression firstArgument, Expression secondArgument, String expressionString) {
		super(firstArgument, secondArgument);
		this.expressionFunctionString = expressionString;
	}

	/**
	 * Returns a nice string representation of the expression.
	 *
	 * @return String representation of the expression.
	 */
	@Override
	public String toString() {
		return OPEN_BRACKETS + getFirstArgument() + SPACE
				+ this.expressionFunctionString + SPACE + getSecondArgument() + CLOSE_BRACKETS;
	}

	/**
	 * Returns a list of the variables in the expression.
	 *
	 * @return A list of the variables in the expression.
	 */
	@Override
	public List<String> getVariables() {
		List<String> varList1 = getFirstArgument().getVariables();
		List<String> varList2 = getSecondArgument().getVariables();
		varList1.addAll(varList2);
		return new ArrayList<>(new HashSet<>(varList1));
	}

}
