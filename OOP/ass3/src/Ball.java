import biuoop.DrawSurface;

/**
 * The Ball class represents a ball object.
 * Represented by point - the center of the ball on the x-y axises,
 * a radius and a color.
 *
 * @author Amit Twito
 * @since 8.3.19
 */
public class Ball implements Sprite {

    //Members.
    private Point point;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private Rectangle boundaryFrame;
    private GameEnvironment gameEnvironment;

    //Constructors.

    /**
     * Constructor for the Ball class.
     * Builds a ball from a center point, radius and a color.
     *
     * @param center Center point of the ball.
     * @param r      Radius of the ball.
     * @param color  Color of the ball.
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
     * @param x     X coordinate for the center point of the ball.
     * @param y     Y coordinate for the center point of the ball.
     * @param r     Radius of the ball.
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

    public GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    //Setters

    /**
     * Sets the ball's velocity.
     *
     * @param v The velocity to be set to the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
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

    /**
     * Sets the ball's boundary frame - what area is the ball allowed to bounce in.
     *
     * @param frame Boundary frame of the ball.
     */
    public void setBoundaryFrame(Rectangle frame) {
        this.boundaryFrame = new Rectangle(frame.getUpperLeft(), frame.getWidth(), frame.getHeight());
    }

    /**
     * Sets the ball's game environment - the environment that the ball will be used for the game.
     *
     * @param gameEnvironment Boundary frame of the ball.
     */
    public void setGameEnvironment(GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
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
        Line trajectory = new Line(start, end);

        CollisionInfo collisionInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (collisionInfo != null) {
            Point collisionPoint = collisionInfo.collisionPoint();
            Collidable collidableObject = collisionInfo.collisionObject();
            if (this.point.distance(collisionPoint) <= this.r) {
                this.velocity = collidableObject.hit(collisionPoint, this.velocity);
            }
        }
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
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }
}