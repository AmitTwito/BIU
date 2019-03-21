import biuoop.GUI;
import biuoop.DrawSurface;

/**
 * The Ball class represents a ball object.
 * Represented by point - the center of the ball on the x-y axises,
 * a radius and a color.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 * */
public class Ball {

	//Members.
	private Point point;
	private int r;
	private java.awt.Color color;
	private Velocity v;
	private RectangleFrame boundaryFrame;


	//Constructors.

	/**
	 * Constructor for the Ball class.
	 * Builds a ball from a center point, radius and a color.
	 *
	 * @param center Center point of the ball.
	 * @param r Radius of the ball.
	 * @param color Color of the ball.
	 */
	public Ball(Point center, int r, java.awt.Color color) {
		this.point = new Point(center.getX(), center.getY());
		this.r = r;
		this.color = color;
	}

	/**
	 * Constructor for the Ball class.
	 * Builds a ball from a x and y coordinates (center point), radius and a color.
	 *
	 * @param x X coordinate for the center point of the ball.
	 * @param y Y coordinate for the center point of the ball.
	 * @param r Radius of the ball.
	 * @param color Color of the ball.
	 */
	public Ball(int x, int y, int r, java.awt.Color color) {
		this.point = new Point(x, y);
		this.r = r;
		this.color = color;
	}

	//Getters.

	/**
	 * Returns the x coordinate value of the ball's center point.
	 *
	 * @return X coordinate of the ball's center.
	 */
	public int getX() {return (int)this.point.getX();}

	/**
	 * Returns the Y coordinate value of the ball's center point.
	 *
	 * @return Y coordinate of the ball's center.
	 */
	public int getY() {return (int)this.point.getY();}

	/**
	 * Returns the size - the radius value of the ball.
	 *
	 * @return Size of the ball.
	 */
	public int getSize() {return this.r;}

	/**
	 * Returns the color of the ball.
	 *
	 * @return Color of the ball.
	 */
	public java.awt.Color getColor() {return this.color;}

	/**
	 * Returns the velocity of the ball.
	 *
	 * @return Velocity of the ball.
	 */
	public Velocity getVelocity() {return this.v;}


	//Setters

	/**
	 * Sets the ball's velocity.
	 *
	 * @param v The velocity to be set to the ball.
	 */
	public void setVelocity(Velocity v) {this.v = v;}

	/**
	 * Sets the ball's velocity, given a dx and dy values.
	 *
	 * @return
	 */
	public void setVelocity(double dx, double dy) {this.v = new Velocity(dx, dy);}

	/**
	 * Sets the ball's boundary frame - what area is the ball allowed to bounce in.
	 *
	 * @param frame Boundary frame of the ball.
	 */
	public void setBoundaryFrame(RectangleFrame frame) {
		this.boundaryFrame = new RectangleFrame(frame.getLeftTopCorner(), frame.getRightBottomCorner());
	}

	//Class methods

	/**
	 * Draws the ball on a given drawing surface.
	 *
	 * @param surface DrawSurface of the ball to be drawn on.
	 */
	public void drawOn(DrawSurface surface) {
		surface.setColor(this.color);
		surface.fillCircle((int)this.point.getX(), (int)this.point.getY(), this.r);
	}

	/**
	 * Changes the ball's state by his current position.
	 *
	 */
	public void moveOneStep() {
		/*In this method, handle the cases when the ball reaches the sides of the frame/window,
		 * while avoiding getting out of the frame and make it look like it hits the sides in the animation.*/

		//In general, the x and y axises is defined in a way that the point (0,0) is the left top corner of the gui.

		//Define the corners of the boundary frame.
		Point leftTopCorner = this.boundaryFrame.getLeftTopCorner();
		Point rightBottomCorner = this.boundaryFrame.getRightBottomCorner();

		//Define the sides of the frame in any current position of the ball on the frame.
		Point topSide = new Point(this.point.getX(),leftTopCorner.getY());
		Point bottomSide = new Point(this.point.getX() ,rightBottomCorner.getY());
		Point leftSide = new Point(leftTopCorner.getX(), this.point.getY());
		Point rightSide = new Point(rightBottomCorner.getX(), this.point.getY());


		if(this.getX() == 0 && this.getY() == 0) {
			this.point = new Point(this.r, this.r );

		/* For each of the frame's sides:
			if the distance between the ball's position (center point)
			and the side of frame (the closest one to the ball) is lower then the ball's radius,
			OR after adding the velocity (in x or y axis, respectively) the ball could (or part of the ball)
			get out of the frame - beyond the closest side:
			adjust it's state so it's current center be on distance equal to the radius, from the closest side.
			Then flip it's direction.
		* */

		} else if (this.point.distance(topSide) <= this.r || this.getY() + this.v.getDy() < leftTopCorner.getY()) {
			//Top side of the frame

			this.point = new Point(this.getX(), leftTopCorner.getY() + this.r);
			/*If the ball comes from right or left to the top side - give it a negative change in the y axis -
			*flip it's direction.*/
			this.v = new Velocity(this.v.getDx(), - this.v.getDy());
		} else if (this.point.distance(bottomSide) <= this.r
				   || this.getY() + this.v.getDy() > rightBottomCorner.getY()) {
			//Bottom side of the frame

			this.point = new Point(this.getX(), rightBottomCorner.getY() - this.r);
			/*If the ball comes from right or left to the top side - give it a negative change in the y axis -
			 *flip it's direction.*/
			this.v = new Velocity(this.v.getDx(), - this.v.getDy());
		} else if (this.point.distance(leftSide) <= this.r || this.getX() + this.v.getDx() < leftTopCorner.getX()) {
			//Left side of the frame

			this.point = new Point(leftTopCorner.getY() + this.r, this.getY());
			/*If the ball comes from up or down to the left side - give it a negative change in the x axis -
			 *flip it's direction.*/
			this.v = new Velocity(- this.v.getDx(), this.v.getDy());
		} else if (this.point.distance(rightSide) <= this.r
				|| this.getX() + this.v.getDx() > rightBottomCorner.getX()) {
			//Right side of the frame

			this.point = new Point(rightBottomCorner.getX() - this.r, this.getY());
			/*If the ball comes from up or down to the left side - give it a negative change in the x axis -
			 *flip it's direction.*/
			this.v = new Velocity(- this.v.getDx(), this.v.getDy());
		}

		/*After changes has been done (or not - the ball),
		 *apply the velocity to the current ball's center point.*/
		this.point = this.getVelocity().applyToPoint(this.point);
	}
}