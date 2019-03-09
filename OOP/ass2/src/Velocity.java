// Velocity specifies the change in position on the `x` and the `y` axes.
public class Velocity {

	private double dx;
	private double dy;

	// constructor
	public Velocity(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

	// Take a point with position (x,y) and return a new point
	// with position (x+dx, y+dy)
	public Point applyToPoint(Point p) {

		return new Point(p.getX() + this.dx, p.getY() + this.dy);
	}

	public static Velocity fromAngleAndSpeed(double angle, double speed) {
		double dx = Math.cos(angle + 270) * speed;
		double dy = Math.sin(angle + 270) * speed;
		return new Velocity(dx, dy);
	}
}