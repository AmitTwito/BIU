import java.util.ArrayList;
import java.util.List;

/**
 * The Rectangle class represents a rectangle object.
 * A Rectangle is represented by an upper left point,
 * and has a width and height.
 *
 * @author Amit Twito
 * @since 23.3.19
 */
public class Rectangle {


    //Members.

    private Point upperLeft; // The upper left Point of the rectangle.
    private double width; // The width of the rectangle.
    private double height; // The height of the rectangle.

    //Constructors

    /**
     * Constructor for the Point class.
     *
     * @param upperLeft Upper left point of the rectangle.
     * @param width     Width of the rectangle
     * @param height    Height of the rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor for the Point class.
     *
     * @param rectangle The rectangle to copy from.
     */
    public Rectangle(Rectangle rectangle) {
        this.upperLeft = new Point(rectangle.upperLeft.getX(), rectangle.upperLeft.getY());
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    //Getters.

    /**
     * Returns the width of the rectangle.
     *
     * @return Width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return Height of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }


    /**
     * Returns the upper left point of the rectangle.
     *
     * @return Upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY());
    }

    //Setters.

    /**
     * Sets the upper left point of the rectangle.
     *
     * @param newUpperLeft New upper left point of the rectangle.
     */
    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = new Point(newUpperLeft.getX(), newUpperLeft.getY());
    }


    //Class Methods.

    /**
     * Returns a (possibly empty) List of intersection points
     * with a given line.
     *
     * @param line The line to calculate the intersection points of this rectangle with.
     * @return List of intersection points with a given line (possibly empty).
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPointsList = new ArrayList<>();
        Line[] rectSidesArray = getRectangleSides();

        //For each of the rectangle's sides, check if it intersects with the given line.
        for (Line side : rectSidesArray) {
            if (side.isIntersecting(line)) {
                //If true, add it to the intersection points list.
                intersectionPointsList.add(side.intersectionWith(line));
            }
            //The maximum intersection points of a line with a rectangle is 2,
            //if 2 are found, no need to continue checking.
            if (intersectionPointsList.size() == 2) {
                break;
            }
        }

        //Even if there are no intersection points, an empty list will be returned.
        return intersectionPointsList;
    }

    /**
     * Returns and array of Lines - each line is a side of the rectangle.
     * A[0] is the top side.
     * A[1] is the left side.
     * A[2] is the bottom side.
     * A[3] is the right side.
     *
     * @return Array of the sides of the rectangle.
     */
    public Line[] getRectangleSides() {
        Line[] rectSidesArray = new Line[4];

        //Set the remaining points of the rectangle.
        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        Point lowerRight = new Point(lowerLeft.getX() + this.width, lowerLeft.getY());

        //Build every side of the rectangle.
        rectSidesArray[0] = new Line(this.upperLeft, upperRight); //Top side
        rectSidesArray[1] = new Line(this.upperLeft, lowerLeft); //Left side
        rectSidesArray[2] = new Line(lowerLeft, lowerRight); //Bottom side
        rectSidesArray[3] = new Line(upperRight, lowerRight); //Right side

        return rectSidesArray;
    }


}
