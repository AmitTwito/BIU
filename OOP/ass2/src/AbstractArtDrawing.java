import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {
	public static final int CIRCLE_RADIUS = 3;

	public void drawRandomLines() {
		Random rand = new Random(); // create a random-number generator
		// Create a window with the title "Random Circles Example"
		// which is 400 pixels wide and 300 pixels high.

		Line[] linesArray = new Line[10];

		GUI gui = new GUI("Random Circles Example", 400, 300);
		DrawSurface d = gui.getDrawSurface();
		for (int i = 0; i < 10; ++i) {

			double x1 = 400 * rand.nextDouble(); // get integer in range 1-400
			double y1 = 300 * rand.nextDouble(); // get integer in range 1-300
			double x2 = 400 * rand.nextDouble(); // get integer in range 1-400
			double y2 = 300 * rand.nextDouble(); // get integer in range 1-300
			Line line = new Line(x1, y1, x2, y2);
			linesArray[i] = line;
			d.setColor(Color.BLUE);
			d.fillCircle(line.middle().getX() ,line.middle().getY() ,CIRCLE_RADIUS);
		}

		for (int i = 0; i < 10; ++i) {
			LinearEquation linearEquation = new LinearEquation(linesArray[i]);
			Point intersectionPoint;
			for (int j = 0; i < 10; ++j) {
				if (i != j) {
					LinearEquation otherLinearEquation = new LinearEquation(linesArray[i]);
					intersectionPoint = linearEquation.intersectingPointWith(otherLinearEquation);
					if (intersectionPoint != null) {
						d.fillCircle(intersectionPoint.getX() ,intersectionPoint.getY() ,CIRCLE_RADIUS);
					}
				}
			}
		}
		gui.show(d);
	}

	public static void main(String[] args) {
		AbstractArtDrawing example = new AbstractArtDrawing();
		example.drawRandomLines();
	}
}