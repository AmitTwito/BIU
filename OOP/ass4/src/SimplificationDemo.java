import java.util.Map;
import java.util.TreeMap;

public class SimplificationDemo {
	public static void main(String[] args) {

		Expression e = new Pow(new Pow("x", "y"), "z");
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		e = new Plus(new Mult(4, "x"), new Mult(4, "x"));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		e = new Plus(new Mult(4, "x"), new Mult(6, "x"));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		e = new Plus(new Mult(4, "x"), new Plus(1, "x"));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		e = new Div(new Sin("x"), new Cos("x"));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		e = new Plus(new Mult(4, "x"), new Neg(new Mult(1, "x")));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());

		//(2x) + (2 + ((4x) + 1))

		e = new Plus(new Mult(2, "x"), new Plus(2, new Plus(new Mult(4, "x"),
				new Plus(new Mult(4, "x"), new Plus(new Mult(4,"x"),1)))));
		System.out.println(e);
		System.out.println(e.simplify());
		System.out.println(e.advancedSimplify());
	}
}
