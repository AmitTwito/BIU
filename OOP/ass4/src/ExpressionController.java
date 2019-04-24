public class ExpressionController {




	public static double CalculateByMathFunction(UnaryExpression unaryExpression) throws Exception {
		double value = unaryExpression.getExpression1().evaluate();
		if (unaryExpression.expressionString.equals(Cos.EXPRESSION_STRING)) {
			return Math.cos(Math.toRadians(value));
		} else if (unaryExpression.expressionString.equals(Sin.EXPRESSION_STRING)) {
			return Math.sin(Math.toRadians(value));
		} else {
			return -1 * value;
		}
	}

	public static double CalculateByMathFunction(BinaryExpression binaryExpression) throws Exception{
		double value1 = binaryExpression.getExpression1().evaluate();
		double value2 = binaryExpression.getExpression2().evaluate();
		if (binaryExpression.expressionString.equals(Plus.EXPRESSION_STRING)) {
			return value1 + value2;
		} else if (binaryExpression.expressionString.equals(Minus.EXPRESSION_STRING)) {
			return value1 - value2;
		} else if (binaryExpression.expressionString.equals(Mult.EXPRESSION_STRING)){
			return value1 * value2;
		} else if (binaryExpression.expressionString.equals(Div.EXPRESSION_STRING)){
			return value1 / value2;
		} else if (binaryExpression.expressionString.equals(Pow.EXPRESSION_STRING)){
			return Math.pow(value1, value2);
		} else {
			return Math.log(value1) / Math.log(value2);
		}

	}

}
