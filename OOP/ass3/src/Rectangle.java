import java.util.ArrayList;
import java.util.List;


public class Rectangle {

    static final int RECTANGLE_SIDE_NUMBER = 4;

    private Point upperLeft;
    private double width;
    private double height;

    // Create a new rectangle with location and width/height.

    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rectangle) {
        this.upperLeft =  new Point(rectangle.upperLeft.getX(), rectangle.upperLeft.getY());
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPointsList = new ArrayList<>();
        Line[] rectSidesArray = getRectangleSides();
        boolean intersectionExists = false;
        for (Line side : rectSidesArray) {
            if (side.isIntersecting(line)) {
                intersectionExists = true;
                intersectionPointsList.add(side.intersectionWith(line));
            }
            if (intersectionPointsList.size() == 2) {
                break;
            }
        }
        if (!intersectionExists) {
            return null;
        }

        return intersectionPointsList;
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY());
    }
    public Point getLowerRight() {
        return new Point(this.width, this.height);
    }


    public void setUpperLeft(Point newUpperLeft) {
        this.upperLeft = new Point(newUpperLeft.getX(), newUpperLeft.getY());
    }

    public Line[] getRectangleSides() {
        Line[] rectSidesArray = new Line[RECTANGLE_SIDE_NUMBER];

        Point upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        Point lowerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + height);
        Point lowerRight = new Point(lowerLeft.getX() + this.width, upperRight.getY());

        rectSidesArray[0] = new Line(this.upperLeft, upperRight);//Top side
        rectSidesArray[1] = new Line(this.upperLeft, lowerLeft);//Left side
        rectSidesArray[2] = new Line(lowerLeft, lowerRight);//Bottom side
        rectSidesArray[3] = new Line(upperRight, lowerRight);//Right side

        return rectSidesArray;
    }



}
