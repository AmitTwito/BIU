import java.util.Map;
import java.util.TreeMap;

public class Tester {
	public static void main(String[] args) {

		Expression e2 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));

		String s = e2.toString();
		System.out.println(s);
		try {
			System.out.println(e2.evaluate());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		Expression e3 = e2.assign("y", e2);
		System.out.println(e3);
		// (x + ((x + y)^2))^2
		e3 = e3.assign("x", new Num(1));
		System.out.println(e3);
		// (1 + ((1 + y)^2))^2
		Map<String, Double> assignment = new TreeMap<>();
		assignment.put("x", 2.0);
		assignment.put("y", 4.0);
		System.out.println("e2 var are:");
		for (String var : e2.getVariables()) {
			System.out.println(var);

		}
		try {
			double value = e2.evaluate(assignment);
			System.out.println("The result is: " + value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Expression e = new Pow(new Var("x"), new Num(4));
		Expression de = e2.differentiate("x");
		System.out.println(de);
	}
}
