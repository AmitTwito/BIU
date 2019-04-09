import java.util.List;
import java.util.Map;

public class Log extends BinaryExpression implements Expression  {

	public static final String EXPRESSION_STRING = "Log";

	public Log(Expression expression1, Expression expression2) {
		super(expression1, expression2,EXPRESSION_STRING);
	}


	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	double evaluate(Map<String, Double> assignment) throws Exception;

	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	public double evaluate() throws Exception {
		if (this.expression1 instanceof Num && this.expression2 instanceof Num) {
			return this.expression1.evaluate() - this.expression2.evaluate();
		} else {
			throw new RuntimeException("Cannot use the method evaluate()"
					+ "if both expressions are not Num");
		}
	}

	// Returns a list of the variables in the expression.
	List<String> getVariables();

	// Returns a nice string representation of the expression.
	@Override
	public String toString() {
		return super.toString();
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	Expression assign(String var, Expression expression)
}
