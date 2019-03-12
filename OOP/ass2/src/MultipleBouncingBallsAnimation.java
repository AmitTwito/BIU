import biuoop.*;
import java.util.Random;
import java.awt.Color;

public class MultipleBouncingBallsAnimation {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int MAX_BALL_SIZE = 50;
	public static final int MAX_SPEED = 50;

	public static void main(String[] args) {

		int[] ballSizes = stringsToIntegers(args);
		generateMultipleBouncingBallsAnimation(ballSizes);

	}

	public static void generateMultipleBouncingBallsAnimation(int[] ballSizes) {

		GUI gui = new GUI("Bouncing Ball Animation", WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Random rand = new Random();
		Ball[] ballsArray = new Ball[ballSizes.length];
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

		return  intArray;

	}
}
