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

	private Expression expression1;
	private Expression expression2;

	/**
	 * A constructor for the BaseExpression class.
	 * Built from
	 *
	 * @param expression
	 */
	public BaseExpression(Expression expression) {
		this.expression1 = expression;
	}

	/**
	 * A constructor for the BaseExpression class.
	 * Built from
	 *
	 * @param expression1
	 * @param expression2
	 */
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
			if (str.equals(Num.E_CONST_STRING) || str.equals(Num.PI_CONST_STRING)) {
				return true;
			}
			return false;
		}
	}

	protected double parseDouble(String str) {
		if (str.equals(Num.E_CONST_STRING)) {
			return Math.E;
		}
		if (str.equals(Num.PI_CONST_STRING)) {
			return Math.PI;
		}
		return Double.parseDouble(str);
	}


}
