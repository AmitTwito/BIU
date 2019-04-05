import biuoop.DrawSurface;

/**
 * The Ball class represents a ball object.
 * Represented by point - the center of the ball on the x-y axises,
 * a radius and a color.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
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
     * Changes the ball's state by his current position.
     *//*
    public void moveOneStep() {
        *//*In this method, handle the cases when the ball reaches the sides of the frame/window,
         * while avoiding getting out of the frame and make it look like it hits the sides in the animation.*//*

        *//*In general, the x and y axises is defined in a way that the point (0,0)
         *is the left top corner of the gui.*//*

        //Define the corners of the boundary frame.
        Point leftTopCorner = this.boundaryFrame.getLeftTopCorner();
        Point rightBottomCorner = this.boundaryFrame.getRightBottomCorner();

        //Define the sides of the frame in any current position of the ball on the frame.
        Point topSide = new Point(this.point.getX(), leftTopCorner.getY());
        Point bottomSide = new Point(this.point.getX(), rightBottomCorner.getY());
        Point leftSide = new Point(leftTopCorner.getX(), this.point.getY());
        Point rightSide = new Point(rightBottomCorner.getX(), this.point.getY());

        *//* For each of the frame's sides:
            if the distance between the ball's position (center point)
            and the side of frame (the closest one to the ball) is lower then the ball's radius,
            OR after adding the velocity (in x or y axis, respectively)
            the ball could (or part of the ball) get out of the frame - beyond the closest side:
            adjust it's state so it's current center be on distance equal
            to the radius, from the closest side. Then flip it's direction.*//*

        if (this.point.distance(topSide) <= this.r     //Top side of the frame
                || this.getY() + this.velocity.getDy() < leftTopCorner.getY()) {
            this.point = new Point(this.getX(), leftTopCorner.getY() + this.r);
            *//*If the ball comes from right or left to the top side-
             *give it a negative change in the y axis-
             *flip it's direction.*//*
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        if (this.point.distance(bottomSide) <= this.r  //Bottom side of the frame
                || this.getY() + this.velocity.getDy() > rightBottomCorner.getY()) {
            this.point = new Point(this.getX(), rightBottomCorner.getY() - this.r);
            *//*If the ball comes from right or left to the bottom side-
             *give it a negative change in the y axis-
             *flip it's direction.*//*
            this.velocity = new Velocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        if (this.point.distance(leftSide) <= this.r   //Left side of the frame
                || this.getX() + this.velocity.getDx() < leftTopCorner.getX()) {
            this.point = new Point(leftTopCorner.getX() + this.r, this.getY());
            *//*If the ball comes from up or down to the left side-
             *give it a negative change in the x axis-
             *flip it's direction.*//*
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        if (this.point.distance(rightSide) <= this.r  //Right side of the frame
                || this.getX() + this.velocity.getDx() > rightBottomCorner.getX()) {
            this.point = new Point(rightBottomCorner.getX() - this.r, this.getY());
            *//*If the ball comes from up or down to the right side-
             *give it a negative change in the x axis-
             *flip it's direction.*//*
            this.velocity = new Velocity(-this.velocity.getDx(), this.velocity.getDy());
        }

        *//*After changes has been done (or not - the ball),
         *apply the velocity to the current ball's center point.*//*
        this.point = this.getVelocity().applyToPoint(this.point);
    }*/

    public void timePassed() {
        moveOneStep();
    }

    public void addToGame(Game g) {
        g.addSprite(this);
    }
}