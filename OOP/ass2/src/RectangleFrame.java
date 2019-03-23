import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The RectangleFrame class represents a rectangle frame object.
 * Represented by left top corner point and a right bottom corner point.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 */
public class RectangleFrame {

    //Members.
    private Point leftTopCorner;
    private Point rightBottomCorner;
    private Color color;

    //Constructors.

    /**
     * The default constructor for the RectangleFrame class.
     * The left top corner point of the frame is (0,0).
     *
     * @param rightBottomCorner x coordinate.
     */
    public RectangleFrame(Point rightBottomCorner) {
        this.leftTopCorner = new Point(0, 0);
        this.rightBottomCorner = new Point(rightBottomCorner.getX(), rightBottomCorner.getY());
    }

    /**
     * Constructor for the RectangleFrame class.
     * Builds the frame from 2 corners points.
     *
     * @param leftTopCorner     x coordinate.
     * @param rightBottomCorner y coordinate.
     */
    public RectangleFrame(Point leftTopCorner, Point rightBottomCorner) {
        this.leftTopCorner = new Point(leftTopCorner.getX(), leftTopCorner.getY());
        this.rightBottomCorner = new Point(rightBottomCorner.getX(), rightBottomCorner.getY());
    }

    /**
     * Constructor for the RectangleFrame class.
     * Builds the frame from 2 corners points and a color.
     *
     * @param leftTopCorner     x coordinate.
     * @param rightBottomCorner y coordinate.
     * @param color             Color of the frame.
     */
    public RectangleFrame(Point leftTopCorner, Point rightBottomCorner, Color color) {
        this(leftTopCorner, rightBottomCorner);
        this.color = color;
    }

    //Getters.

    /**
     * Returns the left top corner point of the frame.
     *
     * @return Left top corner of the frame value of this point.
     */
    public Point getLeftTopCorner() {
        return leftTopCorner;
    }

    /**
     * Returns the right bottom corner point of the frame.
     *
     * @return Right bottom corner of the frame.
     */
    public Point getRightBottomCorner() {
        return rightBottomCorner;
    }

    //Class methods.

    /**
     * Draws the frame on a drawing surface.
     *
     * @param surface Drawing surface the frame to be drawn on.
     */
    public void drawOn(DrawSurface surface) {

        /*The drawRectangle function accepts a left top corner,
         width and a height for the rectangle.*/

        surface.setColor(this.color);

        int x = (int) this.leftTopCorner.getX();
        int y = (int) this.leftTopCorner.getY();

        int rectangleWidth = (int) (this.rightBottomCorner.getX() - x);
        int rectangleHeight = (int) (this.rightBottomCorner.getY() - y);

        surface.drawRectangle(x, y, rectangleWidth, rectangleHeight);
    }
}
