import biuoop.GUI;
import biuoop.DrawSurface;

public class Ball {

	private Point point;
	private int x;
	private int y;
	private int r;
	private int size;
	private java.awt.Color color;
	private Velocity v;

	// constructor
	public Ball(Point center, int r, java.awt.Color color) {
		this.point = new Point(center.getX(), center.getY());
		this.x = (int)center.getX();
		this.y = (int)center.getY();
		this.r = r;
		//this.size = Math.PI;
		this.color = color;
	}
	public Ball(int x, int y, int r, java.awt.Color color) {
		this.point = new Point((double)x,(double)y);
		this.x = x;
		this.y = y;
		this.r = r;
		//this.size = Math.PI;
		this.color = color;
	}

	// accessors
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getSize() {return this.size;}
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
		this.point = this.getVelocity().applyToPoint(this.point);
	}

}