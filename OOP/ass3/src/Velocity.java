/**
 * The Velocity class represents a velocity of a ball object.
 * Velocity specifies the change in position on the x and the y axises.
 *
 * @author Amit Twito
 * @since 3.4.19
 */
public class Velocity {

    //Members.
    private double dx;
    private double dy;

    //Constructors.

    /**
     * Constructor for the Velocity class.
     * Builds a velocity from a given change in x (Delta x) and y (Delta y) axises, radius and a color.
     *
     * @param dx Center point of the ball.
     * @param dy Radius of the ball.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //Getters.

    /**
     * Returns the delta x - the change in x axis of the velocity.
     *
     * @return The change in x axis of the velocity.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the delta y -  the change in y axis of the velocity.
     *
     * @return The change in y axis of the velocity.
     */
    public double getDy() {
        return dy;
    }

    //Class methods.

    /**
     * Applies the velocity to a given point.
     *
     * @param p The point the change in x and y axises need to be applied to.
     * @return Changed point after the velocity was applied.
     */
    public Point applyToPoint(Point p) {
        /* Take a point with position (x,y) and return a new point
         * with position (x+dx, y+dy).*/
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Converts an angle and a speed of a ball to a velocity.
     *
     * @param angle The angle of the ball to move in.
     * @param speed The speed of the ball.
     * @return Velocity made from angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        /* To get the dx and dy - we need to dismantle the velocity vector.
         * Normally, the dx is the horizontal component of the vector : cos(angle), and the dy
         * is the vertical component of the vector: sin(angle).
         * In our case, we turn the axises by 90 degrees clockwise and now the dx
         * is the sin component and the dy is the cos component.
         * Then multiply the components by the given speed.*/
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = speed * Math.cos(Math.toRadians(angle)) * (-1);
        //Multiply dy by -1 because we assume that up direction is 0 degrees.
        return new Velocity(dx, dy);
    }
}