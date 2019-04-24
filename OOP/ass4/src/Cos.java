import java.util.List;
import java.util.Map;

public class Cos extends UnaryExpression implements Expression {

	public static final String EXPRESSION_STRING = "Cos";

	public Cos(Expression expression) {
		super(expression, EXPRESSION_STRING);
	}

	public Cos(String var) {
		super(new Var(var), EXPRESSION_STRING);
	}

	public Cos(double num) {
		super(new Num(num), EXPRESSION_STRING);
	}

	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp = this.expression1;
		List<String> vars = getVariables();
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			Expression expression = new Num(entry.getValue());
			if (!vars.contains(entry.getKey())) {
				throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
			}
			exp = exp.assign(entry.getKey(), expression);
		}
		return (new Cos(exp)).evaluate();

	}

	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	public double evaluate() throws Exception{
		return super.evaluate();
	}

	// Returns a list of the variables in the expression.

	public List<String> getVariables() {
		return super.getVariables();
	}


	// Returns a nice string representation of the expression.

	public String toString() {
		return super.toString();
	}


	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	public Expression assign(String var, Expression expression) {

		Expression exp = this.expression1.assign(var, expression);
		return new Cos(exp);
	}
}
