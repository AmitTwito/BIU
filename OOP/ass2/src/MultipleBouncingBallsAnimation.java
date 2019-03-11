import biuoop.*;
import java.util.Random;
import java.awt.Color;

public class MultipleBouncingBallsAnimation {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int MAX_BALL_SIZE = 50;
	public static final int MAX_SPEED = 50;

	public static void main(String[] args) {

		GUI gui = new GUI("Bouncing Ball Animation", WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Random rand = new Random();
		Ball[] ballsArray = new Ball[args.length];
		RectangleFrame boundaryFrame = new RectangleFrame(new Point(WIDTH, HEIGHT));

		for (int i = 0; i < args.length; i++){
			int x = rand.nextInt(WIDTH) + 1;
			int y = rand.nextInt(HEIGHT) + 1;

			int radius = Integer.parseInt(args[i]);

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
}