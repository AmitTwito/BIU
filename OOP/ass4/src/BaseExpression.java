import java.util.List;
import java.util.Map;

/**
 * The BaseExpression Class represents an abstract expression of math function and arithmetic operators.
 *
 * @author Amit Twito
 * @since 6.4.19
 */
public abstract class BaseExpression {

	public static final String OPEN_BRACKETS = "(";
	public static final String CLOSE_BRACKETS = ")";
	public static final String SPACE = " ";
	public static final String COMMA_WITH_SPACE = ", ";

	private Expression firstArgumentExpression;
	private Expression secondArgumentExpression;

	/**
	 * A constructor for the BaseExpression class.
	 * For a binary expression.
	 *
	 * @param argument Expression for the BaseExpression to be built with.
	 */
	public BaseExpression(Expression argument) {
		this.firstArgumentExpression = argument;
	}

	/**
	 * A constructor for the BaseExpression class.
	 * For a unary expression.
	 *
	 * @param firstArgumentExpression First
	 * @param secondArgumentExpression
	 */
	public BaseExpression(Expression firstArgumentExpression, Expression secondArgumentExpression) {
		this.firstArgumentExpression = firstArgumentExpression;
		this.secondArgumentExpression = secondArgumentExpression;
	}

	/**
	 * Returns the first argument of the expression.
	 *
	 * @return First argument expression.
	 */
	protected Expression getFirstArgumentExpression() {
		return this.firstArgumentExpression;
	}

	/**
	 * Returns the second argument of the expression.
	 *
	 * @return Second argument expression.
	 */
	protected Expression getSecondArgumentExpression() {
		return this.secondArgumentExpression;
	}


	public abstract Expression differentiate(String var);

	public abstract double evaluate() throws Exception;

	public abstract List<String> getVariables();

	public abstract double evaluate(Map<String, Double> assignment) throws Exception;

	public abstract String toString();

	public abstract Expression assign(String var, Expression expression);

	public abstract Expression simplify();

	public abstract Expression advancedSimplify();


	protected boolean canParseDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			if (str.equals(Num.PI_CONST_STRING)) {
				return true;
			}
			return false;
		}
	}

	protected double parseDouble(String str) {
		if (str.equals(Num.PI_CONST_STRING)) {
			return Math.PI;
		}
		return Double.parseDouble(str);
	}
}
