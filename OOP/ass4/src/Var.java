import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Var implements Expression  {

	private String var;

	public Var(String var) {
		this.var = var;
	}


	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	public double evaluate(Map<String, Double> assignment) throws Exception {

		return
	}

	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	double evaluate() throws Exception;

	// Returns a list of the variables in the expression.
	public List<String> getVariables() {
		List<String> varList = new ArrayList<>();
		varList.add(this.var);
		return varList;
	}

	// Returns a nice string representation of the expression.
	public String toString() {
		return "" + this.var;
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	Expression assign(String var, Expression expression)
}
