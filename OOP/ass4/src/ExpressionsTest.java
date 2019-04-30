import java.util.Map;
import java.util.TreeMap;

public class ExpressionsTest {
	public static void main(String[] args) {
		Expression e = new Plus(
				new Mult(2, "x"),
				new Plus(
						new Sin(
								new Mult(4, "y")),
						new Pow(
								new Var("e"), "x")));
		System.out.println(e);

		Map<String, Double> assignment = new TreeMap<>();
		assignment.put("x", 2.0);
		assignment.put("y", 0.25);
		assignment.put("e", 2.71);
		try {
			System.out.println(e.evaluate(assignment));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println(e.differentiate("x"));
		try {
			System.out.println(e.differentiate("x").evaluate(assignment));
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		System.out.println(e.differentiate("x").simplify());

		Expression e4 = new Pow(new Num("e"), 2);
		Expression e5 = new Pow("e", "2");

		try {
			System.out.println(e4.evaluate());
			System.out.println(e5.evaluate());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}


	}
}
