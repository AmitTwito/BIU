import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The UnaryExpression Class represents an abstract expression of unary math functions.
 *
 * @author Amit Twito
 */
public abstract class UnaryExpression extends BaseExpression {

    private String expressionString;

    /**
     * A constructor for the UnaryExpression class.
     *
     * @param argument Argument for the unary expression.
     * @param expressionString Expression string of the unary function.
     */
    public UnaryExpression(Expression argument, String expressionString) {
        super(argument);
        this.expressionString = expressionString;
    }

    /**
     * When used on unary expressions, throws an exception.
     *
     * @return nothing.
     */
    @Override
    public Expression getSecondArgument() {
        throw new NullPointerException("Unsuccessful try to access a second argument of a unary expression, "
                + "unary expressions do not have one.");
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return String representation of the expression.
     */
    @Override
    public String toString() {
        return this.expressionString + OPEN_BRACKETS + getFirstArgument() + CLOSE_BRACKETS;
    }


    /**
     * Returns a list of the variables in the expression.
     *
     * @return A list of the variables in the expression.
     */
    @Override
    public List<String> getVariables() {
        return new ArrayList<>(new HashSet<>(getFirstArgument().getVariables()));
    }

}