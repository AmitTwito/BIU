public class LinearEquation {

	private Line line;
	private double slope;
	private double interceptWithY;
	private boolean isVertical;

	public LinearEquation (Line line) {
		//y - y1 = m(x-x1) => y = mx + y1 - mx1

		this.line = new Line(line.start(), line.end());
		if (line.start().getX() == line.end().getX()) {
			this.isVertical = true;
		} else {
			this.slope = calculateSlope(line);
		}
		this.interceptWithY = line.start().getY() - this.slope * line.start().getX();
	}
	//y = mx + y1 - mx1 => y1 - mx1 is where the line crosses the y-axis.
	public Point intersectingPointWith(LinearEquation other) {
		double x = 0;
		double y = 0;
		if (this.isVertical && !other.isVertical) {
			x = this.line.start().getX();
			y = other.slope * x + other.interceptWithY;
		} else if (!this.isVertical && other.isVertical) {
			x = other.line.start().getX();
			y = this.slope * x + this.interceptWithY;
		} else if (!this.isVertical && !other.isVertical) {
			// y = m1x + b, y = m2x +c => m1x +b = m2x +c => x = (c - b) / (m1 - m2).
			x = (other.interceptWithY - this.interceptWithY) / (this.slope - other.slope);
			y = this.slope * x + this.interceptWithY;
		}

		Point intersectingPoint = new Point(x, y);
		double distance1 = intersectingPoint.distance(this.line.start());
		double distance2 = intersectingPoint.distance(this.line.end());
		double distance3 = intersectingPoint.distance(other.line.start());
		double distance4 = intersectingPoint.distance(other.line.end());
		if (this.line.length() >= distance1 + distance2 && other.line.length() >= distance3 + distance4) {
			return intersectingPoint;
		}
		return null;
	}

	public double getSlope() {
		return this.slope;
	}

	private double calculateSlope(Line line) {

		double deltaY = line.end().getY() - line.start().getY();
		double deltaX = line.end().getX() - line.start().getX();

		return deltaY / deltaX;
	}

}