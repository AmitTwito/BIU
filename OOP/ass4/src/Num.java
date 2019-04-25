import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Num implements Expression {

    public static final String E_CONST_STRING = "e";
    public static final String PI_CONST_NUMBER = "Pi";

    private double num;

    public Num(double number) {
        this.num = number;
    }

    public Num(String number) {
        if (number.equals(E_CONST_STRING)) {
            this.num = Math.E;
        } else if (number.equals(PI_CONST_NUMBER)) {
            this.num = Math.PI;
        } else {
            this.num = Double.parseDouble(number);
        }
    }

    // Evaluate the expression using the variable values provided
    // in the assignment, and return the result.  If the expression
    // contains a variable which is not in the assignment, an exception
    // is thrown.
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        throw new Exception("Can't evaluate with an assignment to a Num without variables.");
    }

    // A convenience method. Like the `evaluate(assignment)` method above,
    // but uses an empty assignment.
    @Override
    public double evaluate() throws Exception {
        return this.num;
    }

    // Returns a list of the variables in the expression.
    @Override
    public List<String> getVariables() {
        return new ArrayList<>();
    }

    // Returns a nice string representation of the expression.
    @Override
    public String toString() {

        if (this.num - (int) this.num == 0) {
            return "" + (int) this.num;
        } else if (this.num == Math.E) {
            return E_CONST_STRING;
        } else if (this.num == Math.PI) {
            return PI_CONST_NUMBER;
        } else {
            return "" + this.num;
        }
    }

    // Returns a new expression in which all occurrences of the variable
    // var are replaced with the provided expression (Does not modify the
    // current expression).
    @Override
    public Expression assign(String var, Expression expression) {
        return new Num(this.num);
    }

    // Returns the expression tree resulting from differentiating
    // the current expression relative to variable `var`.
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    // Returned a simplified version of the current expression.
    public Expression simplify() {
        return new Num(this.num);
    }
}
