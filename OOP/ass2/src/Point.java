/**
 * The Point class represents a Point object.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 * */
public class Point {

	//Members

	//x, y are the coordinates for the Point.
	private double x;
	private double y;

	// constructors

	/**
	 * Constructor for the Point class.
	 *
	 * @param x x coordinate.
	 * @param y y coordinate.
	 * */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	//Getters.

	/**
	 * Returns the x coordinate of this point.
	 *
	 * @return x value of this point.
	 * */
	public double getX() { return this.x; }

	/**
	 * Returns the y coordinate of this point.
	 *
	 * @return y value of this point.
	 * */
	public double getY() { return this.y; }


	//Class methods.

	/**
	 *	Calculates and returns the distance between this point and other point.
	 *
	 * @param other Other point for the distance calculation.
	 * @return double value of the distance.
	 * */
	public double distance(Point other) {
		return Math.sqrt(Math.pow((this.x) - other.getX(), 2) + Math.pow((this.y) - other.getY(), 2));
	}

	/**
	 * Checks if this point equals to the other point,
	 * returns true, other wise - false.
	 *
	 * @param other Other point to compare with this point.
	 * @return True if both points equal, else - false.
	 * */
	public boolean equals(Point other) {
		return this.x == other.x && this.y == other.y;
	}
}