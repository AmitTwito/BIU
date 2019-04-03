import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
 * The AbstractArtDrawing class draws random Lines on a gui,
 * and colors the middle of them and intersection points with other lines.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 */
public class AbstractArtDrawing {
    public static final int CIRCLE_RADIUS = 3;
    public static final int MAX_LINES_NUMBER = 10;
    public static final String GUI_TITLE = "Random Circles Example";
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    /**
     * Draw MAX_LINES_NUMBER of lines on a gui with their middle points,
     * and intersection points with each other.
     */
    public void drawRandomLines() {
        Random rand = new Random();

        Line[] linesArray = new Line[MAX_LINES_NUMBER];

        GUI gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);
        DrawSurface d = gui.getDrawSurface();

        /*First, generate MAX_LINES_NUMBER random lines within the gui boundaries, and draw them,
         * then, draw and fill a circle with the line's middle point's coordinates.*/
        for (int i = 0; i < MAX_LINES_NUMBER; ++i) {
            linesArray[i] = Line.generateRandomLine(d.getHeight(), d.getWidth());
            drawLine(d, linesArray[i]);
            d.setColor(Color.BLUE);
            int x = (int) linesArray[i].middle().getX();
            int y = (int) linesArray[i].middle().getY();
            d.fillCircle(x, y, CIRCLE_RADIUS);
        }

        //Part of drawing the intersection points.

        d.setColor(Color.RED);
        /*Second, for each of the generated lines, find (if exists) the intersection point with every other line,
         * and draw it.
         * */
        for (int i = 0; i < MAX_LINES_NUMBER; ++i) {
            Point intersectionPoint;
            for (int j = 0; j < MAX_LINES_NUMBER; ++j) {
                if (i != j) {
                    intersectionPoint = linesArray[i].intersectionWith(linesArray[j]);
                    if (intersectionPoint != null) {
                        int x = (int) intersectionPoint.getX();
                        int y = (int) intersectionPoint.getY();
                        d.fillCircle(x, y, CIRCLE_RADIUS);
                    }
                }
            }
        }
        gui.show(d);
    }

    /**
     * Draws a given Line on a given DrawSurface.
     *
     * @param d DrawSurface to draw the line on.
     * @param line The line to draw.
     */
    private static void drawLine(DrawSurface d, Line line) {
        d.setColor(Color.BLACK);
        int x1 = (int) line.start().getX();
        int y1 = (int) line.start().getY();
        int x2 = (int) line.end().getX();
        int y2 = (int) line.end().getY();
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * Generates an AbstractArtDrawing picture.
     *
     * @param args Array of String arguments from the command line.
     */
    public static void main(String[] args) {
        AbstractArtDrawing abstractArtDrawing = new AbstractArtDrawing();
        abstractArtDrawing.drawRandomLines();
    }
}