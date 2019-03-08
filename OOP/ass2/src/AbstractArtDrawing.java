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

			int x1 = rand.nextInt(400) + 1; // get integer in range 1-400
			int y1 = rand.nextInt(300) + 1; // get integer in range 1-300
			int x2 = rand.nextInt(400) + 1; // get integer in range 1-400
			int y2 = rand.nextInt(300) + 1; // get integer in range 1-300
			Line line = new Line(x1, y1, x2, y2);
			linesArray[i] = line;
			d.setColor(Color.BLACK);
			d.drawLine((int)line.start().getX(), (int)line.start().getY(), (int)line.end().getX(),(int) line.end().getY());
			d.setColor(Color.BLUE);
			d.fillCircle((int)line.middle().getX() ,(int)line.middle().getY() ,CIRCLE_RADIUS);
		}

		d.setColor(Color.RED);
		for (int i = 0; i < 10; i++) {
			LinearEquation linearEquation = new LinearEquation(linesArray[i]);
			Point intersectionPoint;
			for (int j = 0; j < 10; j++) {
				if (i != j) {
					LinearEquation otherLinearEquation = new LinearEquation(linesArray[j]);
					intersectionPoint = linearEquation.intersectingPointWith(otherLinearEquation);
					if (intersectionPoint != null) {

						d.fillCircle((int)intersectionPoint.getX() ,(int)intersectionPoint.getY() ,CIRCLE_RADIUS);
					}
				}
			}
		}
		gui.show(d);
	}

	public static void main(String[] args) {
		AbstractArtDrawing abstractArtDrawing = new AbstractArtDrawing();
		abstractArtDrawing.drawRandomLines();
	}
}