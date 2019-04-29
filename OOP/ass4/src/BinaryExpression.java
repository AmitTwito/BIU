import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public abstract class BinaryExpression extends BaseExpression {

	private String expressionFunctionString;

	public BinaryExpression(Expression expression1, Expression expression2, String expressionString) {
		super(expression1, expression2);
		this.expressionFunctionString = expressionString;
	}

	@Override
	public String toString() {
		return OPEN_BRACKETS + getExpression1() + SPACE
				+ this.expressionFunctionString + SPACE + getExpression2() + CLOSE_BRACKETS;
	}

	@Override
	public List<String> getVariables() {
		List<String> varList1 = getExpression1().getVariables();
		List<String> varList2 = getExpression2().getVariables();
		varList1.addAll(varList2);
		return new ArrayList<>(new HashSet<>(varList1));
	}
}
