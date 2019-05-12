import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The Num Class represents an expression of numbers.
 *
 * @author Amit Twito
 */
public class Num implements Expression {

    public static final String E_CONST_STRING = "e";
    public static final String PI_CONST_STRING = "Pi";

    private double num;

    /**
     * A constructor for the Tan class.
     *
     * @param number The wanted number.
     */
    public Num(double number) {
        this.num = number;
    }

    /**
     * A constructor for the Num class.
     *
     * @param number Number as a string, for the number e or Pi.
     */
    public Num(String number) {
        if (number.equals(E_CONST_STRING)) {
            this.num = Math.E;
        } else if (number.equals(PI_CONST_STRING)) {
            this.num = Math.PI;
        } else {
            try {
                this.num = Double.parseDouble(number);
            } catch (Exception e) {
                throw new IllegalArgumentException("Can't insert a char to a number");
            }
        }
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.  If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     *
     * @param assignment Map of variable and values for assigning in the expression.
     * @return Evaluated result of the Expression after an assignment.
     * @throws Exception if the expression contains a variable which is not in the assignment.
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        throw new Exception("Can't evaluate with an assignment to a Num without variables.");
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     *
     * @return Evaluated result of the Expression.
     * @throws Exception If the Expression was not assigned with values.
     */
    @Override
    public double evaluate() throws Exception {
        return this.num;
    }

    /**
     * Returns a list of the variables in the expression.
     *
     * @return A list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return new ArrayList<>();
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return String representation of the expression.
     */
    @Override
    public String toString() {

        if (this.num == Math.E) {
            return E_CONST_STRING;
        } else if (this.num == Math.PI) {
            return PI_CONST_STRING;
        } else {
            return "" + this.num;
        }
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        The variable to assign an expression to.
     * @param expression The expression to assign into a variable.
     * @return New expression with the assigned variable.
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Num(this.num);
    }

    /**
     * Returns the expression tree resulting from differentiating
     * the current expression relative to variable `var`.
     *
     * @param var The var to differentiate by.
     * @return Expression, the differentiation of the expression.
     */
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        return new Num(this.num);
    }

    /**
     * @return Advanced simplified expression.
     */
    @Override
    public Expression advancedSimplify() {
        return simplify();
    }
}
