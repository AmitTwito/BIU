import biuoop.GUI;
import biuoop.DrawSurface;

public class Ball {

	private Point point;
	private int r;
	private java.awt.Color color;
	private Velocity v;
	private RectangleFrame boundaryFrame;
	// constructor
	public Ball(Point center, int r, java.awt.Color color) {
		this.point = new Point(center.getX(), center.getY());
		this.r = r;
		this.color = color;
	}
	public Ball(int x, int y, int r, java.awt.Color color) {
		this.point = new Point(x, y);
		this.r = r;
		this.color = color;
	}

	// accessors
	public int getX() {return (int)this.point.getX();}
	public int getY() {return (int)this.point.getY();}
	public int getSize() {return this.r;}
	public java.awt.Color getColor() {return this.color;}
	public Velocity getVelocity() {return this.v;}

	// draw the ball on the given DrawSurface
	public void drawOn(DrawSurface surface) {
		surface.setColor(this.color);
		surface.fillCircle((int)this.point.getX(), (int)this.point.getY(), this.r);
	}

	public void setVelocity(Velocity v) {this.v = v;}
	public void setVelocity(double dx, double dy) {this.v = new Velocity(dx, dy);}
	public void setBoundaryFrame(RectangleFrame frame) {
		this.boundaryFrame = new RectangleFrame(frame.getLeftTopCorner(), frame.getRightBottomCorner());
	}

	public void moveOneStep() {

		Point leftTopCorner = this.boundaryFrame.getLeftTopCorner();
		Point rightBottomCorner = this.boundaryFrame.getRightBottomCorner();

		Point topSide = new Point(this.point.getX(),leftTopCorner.getY());
		Point bottomSide = new Point(this.point.getX() ,rightBottomCorner.getY());
		Point leftSide = new Point(leftTopCorner.getX(), this.point.getY());
		Point rightSide = new Point(rightBottomCorner.getX(), this.point.getY());

		if (this.point.distance(topSide) <= this.r || this.getY() + this.v.getDy() < leftTopCorner.getY()) {
			this.point = new Point(this.getX(), this.r);
			this.v = new Velocity(this.v.getDx(), - this.v.getDy());
		} else if (this.point.distance(bottomSide) <= this.r || this.getY() + this.v.getDy() > rightBottomCorner.getY()) {
			this.point = new Point(this.getX(), rightBottomCorner.getY() - this.r);
			this.v = new Velocity(this.v.getDx(), - this.v.getDy());
		} else if (this.point.distance(leftSide) <= this.r || this.getX() + this.v.getDx() < leftTopCorner.getX()) {
			this.point = new Point(this.r, this.getY());
			this.v = new Velocity(- this.v.getDx(), this.v.getDy());
		} else if (this.point.distance(rightSide) <= this.r || this.getX() + this.v.getDx() > rightBottomCorner.getX()) {
			this.point = new Point(rightBottomCorner.getX() - this.r, this.getY());
			this.v = new Velocity(- this.v.getDx(), this.v.getDy());
		}
		this.point = this.getVelocity().applyToPoint(this.point);
	}


}