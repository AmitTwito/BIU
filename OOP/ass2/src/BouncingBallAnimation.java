import biuoop.*;

import java.awt.*;

/**
 * The Point class represents a Point object.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 * */
public class BouncingBallAnimation {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	public static final int RADIUS = 20;
	public static void main(String[] args) {
		generateBouncingBallAnimation();

	}

	public static void generateBouncingBallAnimation() {
		GUI gui = new GUI("Bouncing Ball Animation", WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Ball ball = new Ball(0, 0, RADIUS, java.awt.Color.BLACK);
		RectangleFrame boundaryFrame = new RectangleFrame(new Point(WIDTH, HEIGHT));
		ball.setBoundaryFrame(boundaryFrame);
		Velocity v = Velocity.fromAngleAndSpeed(90, 2);
		//Velocity v = new Velocity(2,0);
		ball.setVelocity(v);
		while (true) {
			ball.moveOneStep();
			DrawSurface d = gui.getDrawSurface();
			ball.drawOn(d);
			gui.show(d);
			sleeper.sleepFor(50);  // wait for 50 milliseconds.
		}

	}
	public static void generateRandomBouncingBallAnimation(GUI gui, RectangleFrame frame) {


	}

	private static void generateRandomBall(RectangleFrame frame) {


	}
}
