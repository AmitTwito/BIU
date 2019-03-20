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
 * */
public class AbstractArtDrawing {

	public static final int CIRCLE_RADIUS = 3;
	public static final int MAX_LINES_NUMBER = 10;

	public void drawRandomLines() {
		Random rand = new Random(); // create a random-number generator
		// Create a window with the title "Random Circles Example"
		// which is 400 pixels wide and 300 pixels high.

		Line[] linesArray = new Line[MAX_LINES_NUMBER];

		GUI gui = new GUI("Random Circles Example", 600, 600);
		DrawSurface d = gui.getDrawSurface();
		for (int i = 0; i < MAX_LINES_NUMBER; ++i) {
			linesArray[i] = Line.generateRandomLine(d.getHeight(), d.getWidth());
			drawLine(d, linesArray[i]);
			d.setColor(Color.BLUE);
			d.fillCircle((int)linesArray[i].middle().getX() ,(int)linesArray[i].middle().getY() ,CIRCLE_RADIUS);
		}

		int count = 0;
		d.setColor(Color.RED);
		for (int i = 0; i < MAX_LINES_NUMBER; ++i) {
			Point intersectionPoint;
			for (int j = 0; j < MAX_LINES_NUMBER; ++j) {
				if (i != j) {
					intersectionPoint = linesArray[i].intersectionWith(linesArray[j]);
					if (intersectionPoint != null) {
							d.fillCircle((int)intersectionPoint.getX() ,(int)intersectionPoint.getY() ,CIRCLE_RADIUS);
					}
				}
			}
		}
		gui.show(d);
	}

	private static void drawLine(DrawSurface d, Line line) {
		d.setColor(Color.BLACK);
		d.drawLine((int)line.start().getX(), (int)line.start().getY(), (int)line.end().getX(),(int) line.end().getY());
	}


	public static void main(String[] args) {
		AbstractArtDrawing abstractArtDrawing = new AbstractArtDrawing();
		abstractArtDrawing.drawRandomLines();
	}
}