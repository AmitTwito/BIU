import biuoop.*;
import java.util.Random;
import java.awt.Color;

/**
 * The Point class represents a Point object.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 * */
public class MultipleBouncingBallsAnimation {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int MAX_BALL_SIZE = 50;
	public static final int MAX_SPEED = 50;
	public static final String GUI_TITLE = "Bouncing Ball Animation";

	/**
	 * Generates a MultipleBouncingBallsAnimation with a given list of ball sizes.
	 *
	 * @param args Array of String ball sizes from the command line.
	 * */
	public static void main(String[] args) {

		int[] ballSizes = stringsToIntegers(args);
		MultipleBouncingBallsAnimation multipleBouncingBallsAnimation = new MultipleBouncingBallsAnimation();
		multipleBouncingBallsAnimation.generateAnimation(ballSizes);

	}

	/**
	 * Generates a multiple bouncing balls animation, with a given list of ball sizes..
	 *
	 * @param ballSizes Array of int ball sizes.
	 * */
	public void generateAnimation(int[] ballSizes) {

		GUI gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Random rand = new Random();
		Ball[] ballsArray = new Ball[ballSizes.length];

		//Set the boundary frame for the balls.
		RectangleFrame boundaryFrame = new RectangleFrame(new Point(WIDTH, HEIGHT));

		for (int i = 0; i < ballSizes.length; i++){
			int x = rand.nextInt(WIDTH) + 1;
			int y = rand.nextInt(HEIGHT) + 1;

			int radius = ballSizes[i];

			//Generate random numbers from 0 to 255 for a random color.
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			Color c = new Color(r, g, b);
			ballsArray[i] = new Ball(x, y, radius, c);

			double angle = 90 * rand.nextDouble();
			double speed;
			if (radius >= MAX_BALL_SIZE) {
				speed = MAX_SPEED / MAX_BALL_SIZE;
			} else {
				speed = MAX_SPEED / radius;
			}
			Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
			ballsArray[i].setVelocity(v);
			ballsArray[i].setBoundaryFrame(boundaryFrame);
		}

		//Animate the balls.
		while (true) {
			DrawSurface d = gui.getDrawSurface();
			for (Ball ball : ballsArray) {
				ball.moveOneStep();
				ball.drawOn(d);
			}
			gui.show(d);
			sleeper.sleepFor(40);
		}

	}

	private static int[] stringsToIntegers(String[] strings) {
		int[] intArray = new int[strings.length];

		for (int i = 0; i < strings.length; i++) {
			intArray[i] = Integer.parseInt(strings[i]);
		}

		return intArray;

	}
}
