import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class BinaryExpression extends BaseExpression {

	private String expressionFunctionString;

	/**
	 * A constructor for the BinaryExpression class.
	 *
	 * @param expression1      The first argument expression.
	 * @param expression2      The second argument expression.
	 * @param expressionString The string (operator or function) that represents the main expression of this binary
	 *                         expression.
	 */
	public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
		super(expression1, expression2);
		this.expressionFunctionString = expressionString;
	}

	@Override
	public String toString() {
		return OPEN_BRACKETS + getFirstArgumentExpression() + SPACE
				+ this.expressionFunctionString + SPACE + getSecondArgumentExpression() + CLOSE_BRACKETS;
	}

	@Override
	public List<String> getVariables() {
		List<String> varList1 = getFirstArgumentExpression().getVariables();
		List<String> varList2 = getSecondArgumentExpression().getVariables();
		varList1.addAll(varList2);
		return new ArrayList<>(new HashSet<>(varList1));
	}

}
