package geometry;

import biuoop.DrawSurface;
import interfaces.Sprite;
import utilities.LinearEquation;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * The geometry.Line class represents a line object,
 * built from 2 points, one at each edge.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 */
public class Line {

    //Members.
    private Point start; //Start geometry.Point of the geometry.Line.
    private Point end; //End geometry.Point of the geometry.Line.

    // Constructors

    /**
     * Constructor for the geometry.Line class.
     * Builds a line with 2 points.
     *
     * @param start geometry.Point of the first edge of the line.
     * @param end   geometry.Point of the second edge of the line.
     */
    public Line(Point start, Point end) {

        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructor for the geometry.Line class.
     * Builds a line with 4 coordinates, each 2 values for each point.
     *
     * @param x1 Start point x coordinate.
     * @param y1 Start point y coordinate.
     * @param x2 End point x coordinate.
     * @param y2 End point y coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {

        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    //Getters.

    /**
     * Returns the start point of the line.
     *
     * @return Start point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return End point of the line.
     */
    public Point end() {
        return this.end;
    }

    //Class methods.

    /**
     * Returns the length of the line.
     *
     * @return Length of the line.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return Middle point of the line.
     */
    public Point middle() {
        //The middle point of a line is the sum of each point's x and y coordinates divided by 2.
        double middleX = (this.start.getX() + this.end.getX()) / 2.0;
        double middleY = (this.start.getY() + this.end.getY()) / 2.0;

        return new Point(middleX, middleY);
    }

    // Returns true if the lines intersect, false otherwise

    /**
     * Checks if this line intersects with other line,
     * returns true, otherwise false.
     *
     * @param other Other line to check if this line intersects it.
     * @return true or false on intersection.
     */
    public boolean isIntersecting(Line other) {
        LinearEquation linearEquation = new LinearEquation(this);
        LinearEquation otherLinearEquation = new LinearEquation(other);
        Point intersectionPoint = linearEquation.intersectionPointWith(otherLinearEquation);

        //If the intersection point is null -
        //it means that it is not on the line (between their start and end points)
        //or both are equal / same slopes.
        //or both are vertical.
        if (intersectionPoint == null) {
            return false;
        }

        return true;
    }

    /**
     * Returns the intersection point if the lines intersect,
     * otherwise - null.
     *
     * @param other Other line for the intersection with this line.
     * @return Intersection point, if exists.
     */
    public Point intersectionWith(Line other) {
        LinearEquation linearEquation = new LinearEquation(this);
        LinearEquation otherLinearEquation = new LinearEquation(other);

        if (this.isIntersecting(other)) {
            return linearEquation.intersectionPointWith(otherLinearEquation);
        } else {
            return null;
        }
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     *
     * @param other Other line to check equality with this line.
     * @return True or false on equality of lines.
     */
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end)
                || this.end.equals(other.start) && this.start.equals(other.end);
    }

    /**
     * Generates a random line.
     *
     * @param height height of the drawing surface for random generation within the range.
     * @param width  width of the drawing surface for random generation within the range.
     * @return Generated line.
     */
    public static Line generateRandomLine(int height, int width) {
        Random rand = new Random();
        int x1 = rand.nextInt(width) + 1; // get integer in range 1-width
        int y1 = rand.nextInt(height) + 1; // get integer in range 1-height
        int x2 = rand.nextInt(width) + 1; // get integer in range 1-width
        int y2 = rand.nextInt(height) + 1; // get integer in range 1-height

        return new Line(x1, y1, x2, y2);
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.

    /**
     * If this line does not intersect with the rectangle, returns null.
     * Otherwise, returns the closest intersection point to the
     * start of the line.
     *
     * @param rect .
     * @return Generated line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        List<Point> intersectionPoints = rect.intersectionPoints(this);

        //If the list of the intersection points is empty, return null.
        if (intersectionPoints.isEmpty()) {
            return null;
        }

        //Set the smallest distance of the first point in the list to the start of the line,
        //and the first point as the closest intersection.
        double smallest = intersectionPoints.get(0).distance(this.start);
        Point closestIntersection = intersectionPoints.get(0);

        //foreach of the other points in the list (if there are any) compare it to the current smallest distance.
        for (int i = 1; i < intersectionPoints.size(); i++) {
            Point currentPoint = intersectionPoints.get(i);
            double currentDistance = currentPoint.distance(this.start);

            if (currentDistance < smallest) {
                //If the current distance is smaller, set it as the smallet distance and the current point as the
                //closest intersection point.
                smallest = currentDistance;
                closestIntersection = currentPoint;
            }
        }

        return closestIntersection;
    }


}