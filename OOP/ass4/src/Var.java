import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Var implements Expression {

	private String var;

	public Var(String var) {
		this.var = var;
	}

	/**
	 * @param assignment
	 * @return
	 * @throws Exception
	 */
	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	@Override

	public double evaluate(Map<String, Double> assignment) throws Exception {
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			if (this.var.equals(entry.getKey())) {
				return entry.getValue().doubleValue();
			}
		}
		throw new Exception("The var that you tried to assign a number to, " +
				"does not exist in the expression.");
	}

	/**
	 * @return
	 * @throws Exception
	 */
	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	@Override

	public double evaluate() throws Exception {
		throw new Exception("The var " + this.var + " can't be evaluated without an assignment");
	}

	/**
	 * @return
	 */
	// Returns a list of the variables in the expression.
	@Override

	public List<String> getVariables() {
		List<String> varList = new ArrayList<>();
		varList.add(this.var);
		return varList;
	}

	/**
	 * @return
	 */
	// Returns a nice string representation of the expression.
	@Override

	public String toString() {
		return "" + this.var;
	}

	/**
	 * @param var
	 * @param expression
	 * @return
	 */
	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	@Override

	public Expression assign(String var, Expression expression) {
		return this.var.equals(var) ? expression : new Var(this.var);
	}

	// Returns the expression tree resulting from differentiating
	// the current expression relative to variable `var`.

	/**
	 * @param var
	 * @return
	 */
	@Override
	public Expression differentiate(String var) {
		return new Num(this.var.equals(var) ? 1 : 0);
	}

	// Returned a simplified version of the current expression.

	/**
	 * @return
	 */
	@Override

	public Expression simplify() {
		return new Var(this.var);
	}


	/**
	 * @return
	 */
	public Expression advancedSimplify() {
		return simplify();
	}
}
