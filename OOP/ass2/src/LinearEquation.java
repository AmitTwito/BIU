/**
 * The LinearEquation class represents a linear equation object.
 * Linear equation is represented by a Line ,
 * a slope and an interception point with the Y axis.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 */
public class LinearEquation {

    //Members.
    private Line line;
    private double slope;
    private double interceptWithY;
    private boolean isVertical;

    //Constructors.

    /**
     * Constructor for the LinearEquation class.
     * Builds a LinearEquation with a Line.
     *
     * @param line Line of the first edge of the line.
     */
    public LinearEquation(Line line) {
        /*y - y1 = m(x-x1) => y = mx + y1 - mx1
         m is the slope.*/

        this.line = new Line(line.start(), line.end());

        /*Determine if this linear equation represented by a vertical line.
         * if not - its has a slope.*/
        if (line.start().getX() == line.end().getX()) {
            this.isVertical = true;
        } else {
            this.slope = calculateSlope(line);
        }

        //y = mx + y1 - mx1 => y1 - mx1 is where the line crosses the y-axis.
        this.interceptWithY = line.start().getY() - this.slope * line.start().getX();
    }

    //Getters.

    /**
     * Returns the slope of the linear equation.
     *
     * @return Slope of the linear equation.
     */
    public double getSlope() {
        return this.slope;
    }

    //Class Methods.

    /**
     * Returns the intersection point of this LinearEquation with other LinearEquation.
     *
     * @param other The other LinearEquation that intersects with this LinearEquation.
     * @return Intersection point.
     */
    public Point intersectionPointWith(LinearEquation other) {
        /*If both have the same slope, or their lines are equal or both vertical
            - they are parallel to each other and an intersection does not exist.*/
        if (this.slope == other.slope || this.line.equals(other.line)
                || (this.isVertical && other.isVertical)) {
            return null;
        }
        //coordinates of the intersection point.
        double x = 0;
        double y = 0;
        /*Handel the cases that one of the equations is vertical.
         * If so, get a x value from a point of one of the vertical equation and insert it in the other.*/
        if (this.isVertical && !other.isVertical) {
            x = this.line.start().getX();
            y = other.slope * x + other.interceptWithY;
        } else if (!this.isVertical && other.isVertical) {
            x = other.line.start().getX();
            y = this.slope * x + this.interceptWithY;
            /*If both are not vertical, compare both equations to each other,
             * isolate the x and express it with both equations' intercepts* with Y and both's slopes.
             * Then, insert it into one of the equations.*/
        } else if (!this.isVertical && !other.isVertical) {
            // y = m1x + b, y = m2x +c => m1x +b = m2x +c => x = (c - b) / (m1 - m2).
            x = (other.interceptWithY - this.interceptWithY) / (this.slope - other.slope);
            y = this.slope * x + this.interceptWithY;
        }

        Point intersectionPoint = new Point(x, y);
        //Calculate the distance of the intersection point with every point of each equations' lines.
        double distance1 = intersectionPoint.distance(this.line.start());
        double distance2 = intersectionPoint.distance(this.line.end());
        double distance3 = intersectionPoint.distance(other.line.start());
        double distance4 = intersectionPoint.distance(other.line.end());

        /* Check if the intersection point is on BOTH lines (of the linear equations) and not
         * outside their zone - the distance from the "intersection" point to each of the start
         * and end points of the lines, combined together HAS to be lower or equal to the length of each line,
         * respectively.
         * Cast every length to int to get the floor value of the distances -  fix the accuracy of the distance
         * function for similar results on comparing.*/
        if ((int) this.line.length() >= (int) (distance1 + distance2)
                && (int) other.line.length() >= (int) (distance3 + distance4)) {
            return intersectionPoint;
        }

        //In any other case - the intersection point is not on BOTH lines and return null.
        return null;
    }


    //

    /**
     * A private method for calculating this linear equation's slope.
     *
     * @param l Line which built from two points for the slope calculation.
     * @return The slope of the linear equation.
     */
    private double calculateSlope(Line l) {

        double deltaY = l.end().getY() - line.start().getY();
        double deltaX = l.end().getX() - line.start().getX();

        return deltaY / deltaX;
    }

}