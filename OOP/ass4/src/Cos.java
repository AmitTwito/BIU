import java.util.List;
import java.util.Map;

public class Cos extends UnaryExpression implements Expression  {

	public static final String EXPRESSION_STRING = "Cos";

	public Cos(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	public double evaluate(Map<String, Double> assignment) throws Exception {

	}

	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	public double evaluate() throws Exception{
		if (this.expression1 instanceof Num && this.expression2 instanceof Num) {
			return Math.cos(this.expression1);
		} else {
			throw new RuntimeException("Cannot use the method evaluate()"
					+ "if both expressions are not Num");
		}
	}

	// Returns a list of the variables in the expression.
	List<String> getVariables();

	// Returns a nice string representation of the expression.
	public String toString() {
		return super.toString();
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	Expression assign(String var, Expression expression);

}
