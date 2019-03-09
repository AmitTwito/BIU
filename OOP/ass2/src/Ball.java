import biuoop.GUI;
import biuoop.DrawSurface;

public class Ball {

	private Point point;
	private int x;
	private int y;
	private int r;
	private java.awt.Color color;
	private Velocity v;

	// constructor
	public Ball(Point center, int r, java.awt.Color color) {
		this.point = new Point(center.getX(), center.getY());
		this.x = (int)center.getX();
		this.y = (int)center.getY();
		this.r = r;
		this.color = color;
	}
	public Ball(int x, int y, int r, java.awt.Color color) {
		this.point = new Point((double)x,(double)y);
		this.x = x;
		this.y = y;
		this.r = r;
		this.color = color;
	}

	// accessors
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getSize() {return this.r;}
	public java.awt.Color getColor() {return this.color;}

	// draw the ball on the given DrawSurface
	public void drawOn(DrawSurface surface) {
		surface.setColor(this.color);
		surface.fillCircle((int)this.point.getX(), (int)this.point.getY(), this.r);
	}

	public void setVelocity(Velocity v) {this.v = v;}
	public void setVelocity(double dx, double dy) {this.v = new Velocity(dx, dy);}
	public Velocity getVelocity() {return this.v;}

	public void moveOneStep() {
		Point topSide = new Point(this.point.getX(),0);
		Point bottomSide = new Point(this.point.getX() ,BouncingBallAnimation.HEIGHT);
		Point leftSide = new Point(BouncingBallAnimation.WIDTH, this.point.getY());
		Point rightSide = new Point(0, this.point.getY());


		if (this.point.distance(topSide) <= this.r) {

			if (this.v.getDx() < 0 && this.v.getDy() < 0) {
				this.v = new Velocity(this.v.getDx(), - this.v.getDy());

			} else {
				this.v = new Velocity(Math.abs(this.v.getDx()), Math.abs(this.v.getDy()));

			}
		}
		if (this.point.distance(bottomSide) <= this.r) {
			if (this.v.getDx() > 0 && this.v.getDy() > 0) {
				this.v = new Velocity(this.v.getDx(), - this.v.getDy());
			} else {
				this.v = new Velocity(this.v.getDx(), - this.v.getDy());
			}

		}
		if (this.point.distance(leftSide) <= this.r) {
			if (this.v.getDx() < 0 && this.v.getDy() > 0) {
				this.v = new Velocity(- this.v.getDx(), this.v.getDy());
			} else {
				this.v = new Velocity(- this.v.getDx(),  this.v.getDy());
			}


		}
		if (this.point.distance(rightSide) <= this.r) {
			if (this.v.getDx() > 0 && this.v.getDy() < 0) {
				this.v = new Velocity(- this.v.getDx(), this.v.getDy());
			} else {
				this.v = new Velocity(- this.v.getDx(), this.v.getDy());
			}

		}

		this.point = this.getVelocity().applyToPoint(this.point);
	}

}