import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Num implements Expression {

	private double num;

	public Num(double number) {
		this.num = number;
	}

	public Num(String number) {
		this.num = Double.parseDouble(number);

	}
	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	 public double evaluate(Map<String, Double> assignment) throws Exception {

		throw new Exception("This object is not variable.");

	}

	// A convenience method. Like the `evaluate(assignment)` method above,
	// but uses an empty assignment.
	public double evaluate() throws Exception {
		return this.num;
	}

	// Returns a list of the variables in the expression.
	public List<String> getVariables() {
		return new ArrayList<>();
	}

	// Returns a nice string representation of the expression.
	public String toString() {

		if(this.num - (int)this.num == 0) {
			return "" + (int) this.num;
		} else {
			return "" + this.num;
		}
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	public Expression assign(String var, Expression expression){
		return this;
	}
}
