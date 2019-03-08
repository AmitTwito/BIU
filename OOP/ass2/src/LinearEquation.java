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
		double x;
		double y;
		if (this.isVertical && !other.isVertical) {
			x = this.line.start().getX();
			y = other.slope * x + other.interceptWithY;
		} else if (!this.isVertical && other.isVertical) {
			x = other.line.start().getX();
			y = this.slope * x + this.interceptWithY;
		} else if (!this.isVertical && !other.isVertical) {
			// y = m1x + b, y = m2x +c => m1x +b = m2x +c => x = (m1 - m2) / (c - b).

			x = (this.slope - other.slope) / (this.interceptWithY - other.interceptWithY);
			y = this.slope * x;
		} else {
			return null;
		}

		If (this.line.start().getX() <)
	 	if ((x < this.line.start().getX() && x < this.line.end().getX()) ||
			(x > this.line.end().getX() && x > this.line.start().getX()) ||
			(y > this.line.end().getY() && y > this.line.start().getY()) ||
			(y < this.line.start().getY() && y < this.line.end().getY()) ||
				(!this.isVertical && !other.isVertical)	) {
			return null ;
		}
		return new Point(x, y);
	}

	public double getSlope() {
		return this.slope;
	}

	private double calculateSlope(Line line) {

		double deltaY = line.end().getY() - line.start().getY();
		double deltaX = line.end().getX() - line.start().getX();

		if (deltaX == 0) {

		}
		return deltaY / deltaX;
	}

}