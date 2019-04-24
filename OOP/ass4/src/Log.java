import java.util.List;
import java.util.Map;

public class Log extends BinaryExpression implements Expression  {

	public static final String EXPRESSION_STRING = "Log";

	public Log(Expression expression1, Expression expression2) {
		super(expression1, expression2, EXPRESSION_STRING);
	}

	public Log(String var1, String var2) {
		super(new Var(var1), new Var(var2), EXPRESSION_STRING);
	}

	public Log(String var, double num) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}
	public Log(double num,String var) {
		super(new Var(var), new Num(num), EXPRESSION_STRING);
	}

	public Log(double num1, double num2) {
		super(new Num(num1), new Num(num2), EXPRESSION_STRING);
	}
	// Evaluate the expression using the variable values provided
	// in the assignment, and return the result.  If the expression
	// contains a variable which is not in the assignment, an exception
	// is thrown.
	public double evaluate(Map<String, Double> assignment) throws Exception {
		Expression exp1 = this.expression1;
		Expression exp2 = this.expression2;
		List<String> vars = getVariables();
		for (Map.Entry<String, Double> entry : assignment.entrySet()) {
			Expression expression = new Num(entry.getValue());
			if(!vars.contains(entry.getKey())) {
				throw new Exception("The var " + entry.getKey() + " does not exists in this expression.");
			}
			exp1 = exp1.assign(entry.getKey(), expression);
			exp2 = exp2.assign(entry.getKey(), expression);
		}
		//return super.evaluate(assignment);
		return (new Log(exp1, exp2)).evaluate();

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
	@Override
	public String toString() {
		return super.toString();
	}

	// Returns a new expression in which all occurrences of the variable
	// var are replaced with the provided expression (Does not modify the
	// current expression).
	public Expression assign(String var, Expression expression) {

		Expression exp1 = this.expression1.assign(var, expression);
		Expression exp2 = this.expression2.assign(var,expression);
		return new Log(exp1, exp2);
	}}
