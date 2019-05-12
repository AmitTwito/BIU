import java.util.List;
import java.util.Map;

/**
 * The Plus Class represents an expression of plus operator.
 *
 * @author Amit Twito
 */
public class Plus extends BinaryExpression implements Expression {

    public static final String EXPRESSION_STRING = "+";

    /**
     * A constructor for the Plus class.
     *
     * @param firstArgument  The first argument..
     * @param secondArgument The second argument..
     */
    public Plus(Expression firstArgument, Expression secondArgument) {
        super(firstArgument, secondArgument, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param var1 The first argument..
     * @param var2 The second argument..
     */
    public Plus(String var1, String var2) {
        super(new Var(var1), new Var(var2), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param var The first argument..
     * @param num The second argument..
     */
    public Plus(String var, double num) {
        super(new Var(var), new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param num The first argument..
     * @param var The second argument..
     */
    public Plus(double num, String var) {
        super(new Var(var), new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param num1 The first argument..
     * @param num2 The second argument..
     */
    public Plus(double num1, double num2) {
        super(new Num(num1), new Num(num2), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param num        The first argument..
     * @param expression The second argument..
     */
    public Plus(double num, Expression expression) {
        super(expression, new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param expression The first argument..
     * @param num        The second argument..
     */
    public Plus(Expression expression, double num) {
        super(expression, new Num(num), EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param var        The first argument..
     * @param expression The second argument..
     */
    public Plus(String var, Expression expression) {
        super(new Var(var), expression, EXPRESSION_STRING);
    }

    /**
     * A constructor for the Plus class.
     *
     * @param expression The first argument..
     * @param var        The second argument..
     */
    public Plus(Expression expression, String var) {
        super(expression, new Var(var), EXPRESSION_STRING);
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
        if (assignment == null) {
            throw new IllegalArgumentException("Can't assign Null to the expression.");
        }
        Expression exp1 = getFirstArgument();
        Expression exp2 = getSecondArgument();
        List<String> vars = getVariables();
        for (Map.Entry<String, Double> entry : assignment.entrySet()) {
            Expression expression = new Num(entry.getValue());
            if (!vars.contains(entry.getKey())) {
                throw new Exception("Can't assign " + entry.getValue()
                        + " to var " + entry.getKey()
                        + " because it does not exist in this expression.");
            }
            exp1 = exp1.assign(entry.getKey(), expression);
            exp2 = exp2.assign(entry.getKey(), expression);
        }
        return new Plus(exp1, exp2).evaluate();
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

        return getFirstArgument().evaluate() + getSecondArgument().evaluate();
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
        //(f+g)' = f' - g'
        return new Plus(getFirstArgument().differentiate(var), getSecondArgument().differentiate(var));
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

        Expression exp1 = getFirstArgument().assign(var, expression);
        Expression exp2 = getSecondArgument().assign(var, expression);
        return new Plus(exp1, exp2);

    }

    /**
     * Returned a simplified version of the current expression.
     *
     * @return New simplified version of the current expression.
     */
    @Override
    public Expression simplify() {
        //First get both (for the recursive) arguments of the Expression simplified.
        //Then check if it a number - return it as a Num.
        //Try to evaluate both arguments with inserting it into the function calculation.
        //If not, catch the ArithmeticException and throw it, catch the Exception and return the current function,
        //with its both arguments simplified.
        Expression simpleExp1 = getFirstArgument().simplify();
        Expression simpleExp2 = getSecondArgument().simplify();

        if (canParseDouble(this.toString())) {
            return new Num(parseDouble(this.toString()));
        }

        //0 + X =X ;
        if (canParseDouble(simpleExp1.toString())) {
            if (parseDouble(simpleExp1.toString()) == 0) {
                return simpleExp2;
            }
        }
        //X + 0=X ;
        if (canParseDouble(simpleExp2.toString())) {
            if (parseDouble(simpleExp2.toString()) == 0) {
                return simpleExp1;
            }
        }
        try {
            return new Num(simpleExp1.evaluate() + simpleExp2.evaluate());
        } catch (ArithmeticException ae) {
            throw new ArithmeticException("Cannot simplify the expression: " + ae.getMessage());
        } catch (Exception e) {
            return new Plus(simpleExp1, simpleExp2);
        }
    }

    /**
     * @return Advanced simplified expression.
     */
    @Override
    public Expression advancedSimplify() {
        //4x +6x
        //1+ (4x + 6x)
        // ((2x) + (2 + ((4x) + 1)))
        Expression advSimpleEx1 = getFirstArgument().advancedSimplify().simplify();
        Expression advSimpleEx2 = getSecondArgument().advancedSimplify().simplify();

        //X + X = 2X
        if (advSimpleEx1.toString().equals(advSimpleEx2.toString())) {
            return new Mult(2, advSimpleEx1).advancedSimplify();
        }

        if (advSimpleEx1 instanceof Neg && !(advSimpleEx2 instanceof Neg)) {
            return new Plus(advSimpleEx2, advSimpleEx1).advancedSimplify();
        }

        // Num + X => X + Num
        if (advSimpleEx1 instanceof Num && !(advSimpleEx2 instanceof Num)) {
            return new Plus(advSimpleEx2, advSimpleEx1).advancedSimplify();
        }
        //((4x +1) + 2) => (4x + (1+2)).
        if (advSimpleEx1 instanceof Plus && advSimpleEx2 instanceof Num) {
            Plus plusExp = (Plus) advSimpleEx1;
            if (plusExp.getSecondArgument() instanceof Num) {
                return new Plus(plusExp.getFirstArgument().simplify(),
                        new Plus(plusExp.getSecondArgument().simplify(), advSimpleEx2)).advancedSimplify();
            }
        }
        if (advSimpleEx1 instanceof Mult) {
            Mult firstMult = (Mult) advSimpleEx1;
            if (advSimpleEx2 instanceof Var) {

                if (firstMult.getSecondArgument().toString().equals(advSimpleEx2.toString())) {
                    return new Mult(new Plus(firstMult.getFirstArgument().simplify(), 1),
                            advSimpleEx2).advancedSimplify();
                }
            }


            //(2x + (4x + 1))
            if (advSimpleEx2 instanceof Plus) {
                Plus plusExp = (Plus) advSimpleEx2;
                if (plusExp.getSecondArgument() instanceof Num) {
                    return new Plus(new Plus(advSimpleEx1, plusExp.getFirstArgument().simplify()),
                            plusExp.getSecondArgument().simplify()).advancedSimplify();
                }
            }
            //(4x + 6x) = ((4+6)x)
            if (advSimpleEx2 instanceof Mult) {
                Mult secondMult = (Mult) advSimpleEx2;
                if (firstMult.getSecondArgument().toString().equals(secondMult.getSecondArgument().toString())) {
                    return new Mult(new Plus(firstMult.getFirstArgument().simplify(),
                            secondMult.getFirstArgument().simplify()),
                            firstMult.getSecondArgument().simplify()).advancedSimplify();
                }
            }

            if (advSimpleEx2 instanceof Neg) {
                Neg neg = (Neg) advSimpleEx2;
                if (neg.getFirstArgument() instanceof Mult) {
                    Mult secondMult = (Mult) neg.getFirstArgument();
                    if (firstMult.getSecondArgument().toString().equals(secondMult.getSecondArgument())) {
                        return new Mult(new Minus(firstMult.getFirstArgument().simplify(),
                                secondMult.getFirstArgument().simplify()),
                                firstMult.getSecondArgument().simplify()).advancedSimplify();
                    }
                }
                if (firstMult.getSecondArgument().toString().equals(neg.getFirstArgument().toString())) {
                    return new Mult(new Minus(firstMult.getFirstArgument().simplify(),
                            1),
                            firstMult.getSecondArgument().simplify()).advancedSimplify();
                }
            }
        }
        if (advSimpleEx2 instanceof Mult) {

            if (advSimpleEx1 instanceof Var) {
                Mult mult = (Mult) advSimpleEx2;
                if (mult.getSecondArgument().toString().equals(advSimpleEx1.toString())) {
                    return new Mult(new Plus(mult.getFirstArgument().simplify(), 1),
                            advSimpleEx1).advancedSimplify();
                }
            }

            //(2x + (4x + 1))
            if (advSimpleEx1 instanceof Plus) {
                Plus plusExp = (Plus) advSimpleEx1;
                if (plusExp.getSecondArgument() instanceof Num) {
                    return new Plus(new Plus(advSimpleEx2.simplify(), plusExp.getFirstArgument().simplify()),
                            plusExp.getSecondArgument().simplify()).advancedSimplify();
                }
            }
            //(4x + 6x) = ((4+6)x)
            if (advSimpleEx1 instanceof Mult) {
                Mult firstMult = (Mult) advSimpleEx2;
                Mult secondMult = (Mult) advSimpleEx1;
                if (firstMult.getSecondArgument().toString().equals(secondMult.getSecondArgument().toString())) {
                    return new Mult(new Plus(firstMult.getFirstArgument().simplify(),
                            secondMult.getFirstArgument().simplify()),
                            firstMult.getSecondArgument().simplify()).advancedSimplify();
                }
            }
        }

        if (advSimpleEx1 instanceof Div && advSimpleEx2 instanceof Div) {
            Div firstDiv = (Div) advSimpleEx1;
            Div secondDiv = (Div) advSimpleEx2;
            //(Y / X +  Z / X = (Y+Z)/X )
            if (firstDiv.getSecondArgument().toString().equals(secondDiv.getSecondArgument())) {
                return new Div(new Plus(firstDiv.getFirstArgument().simplify(),
                        secondDiv.getSecondArgument().simplify()),
                        firstDiv.getSecondArgument()).advancedSimplify();
            }
        }

        return new Plus(advSimpleEx1.simplify(), advSimpleEx2.simplify());
    }
}
