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
	public static final String GUI_TITLE = "Bouncing Ball Animation";
	public static final double ANGLE = 90;
	public static final double SPEED = 2;
	public static final long SLEEP_MILLISECONDS = 50;

	/**
	 * Generates a BouncingBallAnimation
	 *
	 * @param args Array of String arguments from the command line.
	 * */
	public static void main(String[] args) {
		BouncingBallAnimation bouncingBallAnimation = new BouncingBallAnimation();
		bouncingBallAnimation.generateAnimation();
	}

	/**
	 * Generates a bouncing ball animation.
	 *
	 * */
	public void generateAnimation() {
		GUI gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Ball ball = new Ball(0, 0, RADIUS, java.awt.Color.BLACK);

		//Set the ball's boundary frame.
		RectangleFrame boundaryFrame = new RectangleFrame(new Point(WIDTH, HEIGHT));
		ball.setBoundaryFrame(boundaryFrame);
		//Set the ball's velocity.
		Velocity v = Velocity.fromAngleAndSpeed(ANGLE, SPEED);
		ball.setVelocity(v);

		//Animate the ball's movement. Each loop is a frame, after moving one step drawing - wait SLEEP_MILLISECONDS.
		while (true) {
			ball.moveOneStep();
			DrawSurface d = gui.getDrawSurface();
			ball.drawOn(d);
			gui.show(d);
			sleeper.sleepFor(SLEEP_MILLISECONDS);  // wait for 50 milliseconds.
		}
	}
}
