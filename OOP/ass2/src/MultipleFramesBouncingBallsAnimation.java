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
public class MultipleFramesBouncingBallsAnimation {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int MAX_BALL_SIZE = 50;
	public static final int MAX_SPEED = 50;

	public static void main(String[] args) {
		int[] ballSizes = stringsToIntegers(args);
		generateMultipleFramesBouncingBallsAnimation(ballSizes);
	}

	public static void generateMultipleFramesBouncingBallsAnimation(int[] ballSizes) {
		GUI gui = new GUI("Multiple Frames Bouncing Balls Animation", WIDTH, HEIGHT);
		Point firstLeftTopCorner = new Point(50 ,50);
		Point firstRightBottomCorner = new Point(500, 500);
		Point secondLeftTopCorner = new Point(450, 450);
		Point secondRightBottomCorner = new Point(600, 600);
		RectangleFrame greyRectangleFrame = new RectangleFrame(firstLeftTopCorner, firstRightBottomCorner, Color.GRAY);
		RectangleFrame yellowRectangleFrame = new RectangleFrame(secondLeftTopCorner,
																  secondRightBottomCorner, Color.YELLOW);
		Random rand = new Random();
		Sleeper sleeper = new Sleeper();
		Ball[] ballsArray = new Ball[ballSizes.length];


		for (int i = 0; i < ballSizes.length; i++){
			int x;
			int y;
			int highValue;
			int lowValue;
			if (i < ballSizes.length / 2) {
				highValue = (int)firstRightBottomCorner.getX();
				lowValue = (int)firstLeftTopCorner.getX();
				x = rand.nextInt(highValue - lowValue + 1) + lowValue;
				highValue = (int)firstRightBottomCorner.getY();
				lowValue = (int)firstLeftTopCorner.getY();
				y = rand.nextInt(highValue - lowValue + 1) + lowValue;
			}  else {
				highValue = (int)secondRightBottomCorner.getX();
				lowValue = (int)secondLeftTopCorner.getX();
				x = rand.nextInt(highValue - lowValue + 1) + lowValue;
				highValue = (int)secondRightBottomCorner.getY();
				lowValue = (int)secondLeftTopCorner.getY();
				y = rand.nextInt(highValue - lowValue + 1) + lowValue;
			}

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

			if (i < ballSizes.length / 2 ) {
				ballsArray[i].setBoundaryFrame(greyRectangleFrame);
			}  else {
				ballsArray[i].setBoundaryFrame(yellowRectangleFrame);
			}
		}

		while (true) {
			DrawSurface d = gui.getDrawSurface();
			greyRectangleFrame.drawOn(d);
			yellowRectangleFrame.drawOn(d);

			for (Ball ball : ballsArray) {
				ball.moveOneStep();
				ball.drawOn(d);
			}

			gui.show(d);
			sleeper.sleepFor(20);  // wait for 50 milliseconds.
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
