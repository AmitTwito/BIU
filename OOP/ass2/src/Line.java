public class Line {

	private Point start;
	private Point end;

	// constructors
	public Line(Point start, Point end) {

		this.start = new Point(start.getX(), start.getY());
		this.end = new Point(end.getX(), end.getY());;
	}
	public Line(double x1, double y1, double x2, double y2) {

		this.start = new Point(x1, y1);
		this.end = new Point(x2, y2);
	}

	// Return the length of the line
	public double length() {
		return this.start.distance(this.end);
	}

	// Returns the middle point of the line
	public Point middle() {
		double middleX = (this.start.getX() + this.end.getX()) / 2.0;
		double middleY = (this.start.getY() + this.end.getY()) / 2.0;

		return new Point(middleX, middleY);
	}

	// Returns the start point of the line
	public Point start() { return this.start; }

	// Returns the end point of the line
	public Point end() { return this.end; }

	// Returns true if the lines intersect, false otherwise
	public boolean isIntersecting(Line other) {
		LinearEquation linearEquation = new LinearEquation(this);
		LinearEquation otherLinearEquation = new LinearEquation(other);

		if (linearEquation.getSlope() == otherLinearEquation.getSlope()) {
			return false;
		} else {
			return true;
		}
	}

	// Returns the intersection point if the lines intersect,
	// and null otherwise.
	public Point intersectionWith(Line other) {
		LinearEquation linearEquation = new LinearEquation(this);
		LinearEquation otherLinearEquation = new LinearEquation(other);
		if (this.isIntersecting(other)) {
			return linearEquation.intersectingPointWith(otherLinearEquation) ;
		} else {
			return null;
		}

	}

	// equals -- return true is the lines are equal, false otherwise
	public boolean equals(Line other) {

		return this.start.equals(other.start) && this.end.equals(other.end) ||
				this.end.equals(other.start) && this.start.equals(other.end);
	}


}