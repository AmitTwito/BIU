import biuoop.DrawSurface;

/**
 * The Ball class represents a ball object.
 * Represented by point - the center of the ball on the x-y axises,
 * a radius and a color, and a game environment - so the ball can have
 * interaction with the objects of this environment.
 *
 * @author Amit Twito
 * @version 2.0
 * @since 6.4.19 (original since 8.3.19)
 */
public class Ball implements Sprite {

    //Members.
    private Point point; // Center point of the ball.
    private int r; // The radius of the ball.
    private java.awt.Color color; // Color of the ball.
    private Velocity velocity; // The velocity of the ball.
    private GameEnvironment gameEnvironment; // The game environment of the ball.

    //Constructors.

    /**
     * Constructor for the Ball class.
     * Builds a ball from a center point, radius and a color.
     *
     * @param center          Center point of the ball.
     * @param r               Radius of the ball.
     * @param color           Color of the ball.
     * @param gameEnvironment Game environment of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.point = new Point(center.getX(), center.getY());
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor for the Ball class.
     * Builds a ball from a x and y coordinates (center point), radius and a color.
     *
     * @param x               X coordinate for the center point of the ball.
     * @param y               Y coordinate for the center point of the ball.
     * @param r               Radius of the ball.
     * @param color           Color of the ball.
     * @param gameEnvironment Game environment of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.point = new Point(x, y);
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
        this.velocity = new Velocity(0, 0);
    }

    //Getters.

    /**
     * Returns the x coordinate value of the ball's center point.
     *
     * @return X coordinate of the ball's center.
     */
    public int getX() {
        return (int) this.point.getX();
    }

    /**
     * Returns the Y coordinate value of the ball's center point.
     *
     * @return Y coordinate of the ball's center.
     */
    public int getY() {
        return (int) this.point.getY();
    }

    /**
     * Returns the size - the radius value of the ball.
     *
     * @return Size of the ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Returns the color of the ball.
     *
     * @return Color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Returns the velocity of the ball.
     *
     * @return Velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    //Setters

    /**
     * Sets the ball's velocity.
     *
     * @param v The velocity to be set to the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    /**
     * Sets the ball's velocity, given a dx and dy values.
     *
     * @param dx Change in x axis.
     * @param dy Change in y axis.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }


    //Class methods

    /**
     * Draws the ball on a given drawing surface.
     *
     * @param surface DrawSurface of the ball to be drawn on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.point.getX(), (int) this.point.getY(), this.r);
    }

    /**
     * Changes the ball's state by his current position.
     */
    public void moveOneStep() {

        Point start = this.point;
        Point end = this.velocity.applyToPoint(start);
        //First build the trajectory of the ball if no collision occur.
        Line trajectory = new Line(start, end);

        //Get the CollisionInfo of the supposed to happen collision on the route of the ball's trajectory.
        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collidableObject = collisionInfo.collisionObject();
            //If the collisionInfo is not null, check if the distance between the ball's center,
            //and the collision point is smaller or equal to the speed of the ball - difference in number of units,
            //the ball "hits" the collidable object - set it's velocity as the new velocity from the hit function.
            //By that we can achieve more pleasant looks that a part of the ball wont get inside a collidable.
            double speed = this.velocity.toSpeed();
            if (this.point.distance(collisionPoint) <= speed) {
                this.velocity = collidableObject.hit(this, collisionPoint, this.velocity);
            }
        }
        //Even if the collisionInfo is null (if its not, the new point affected by the new velocity),
        //move the ball one step.
        this.point = this.velocity.applyToPoint(this.point);
    }

    /**
     * Notifies the ball that time has passed, change it's current state.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Add this ball to the sprites collection of a given Game.
     *
     * @param g The game to add the ball to.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }

}