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
		System.out.println(e.simplify());
		Expression de = e.differentiate("x");
		System.out.println(de.simplify());

		Expression e1 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
		System.out.println(e1.differentiate("x"));
// the result is:
// (((x + y) ^ 2.0) * (((1.0 + 0.0) * (2.0 / (x + y))) + (0.0 * log(e, (x + y)))))
		System.out.println(e1.differentiate("x").simplify());
// the result is:
// (((x + y) ^ 2.0) * (2.0 / (x + y)))


		e3 = new Pow(new Var("e"), new Var("x"));
		System.out.println(e3.differentiate("x"));
// ((e ^ x) * ((0.0 * (x / e)) + (1.0 * log(e, e))))
		System.out.println(e3.differentiate("x").simplify());
// (e ^ x)

		Expression e4 = new Sin(new Cos(180));
		try {
			System.out.println(e4.simplify());
		}
		catch (Exception ex){

		}

		Expression e5 = new Sin(
				new Pow(
						new Mult(
								new Plus(
										new Mult(new Num(2), new Var("x")),
										new Var("y")),
								new Num(4)),
						new Var("x")));
		System.out.println(e5);

		System.out.println(e5.simplify());
		System.out.println(e5.differentiate("x").simplify().simplify());
		for (String var : e5.getVariables()) {
			System.out.println(var);

		}
		Expression e6 = new Pow(
				new Mult(
						new Plus(
								new Mult(new Num(2), new Var("x")),
								new Var("y")),
						new Num(4)),
				new Var("x"));

		System.out.println(e6.simplify());
		System.out.println(e6.differentiate("x"));
		Expression e8 = e6.differentiate("X");

		System.out.println(e8.simplify());

		System.out.println((new Mult(1,new Plus(new Mult("x", 0) , 2))).simplify().simplify());

	}
}
