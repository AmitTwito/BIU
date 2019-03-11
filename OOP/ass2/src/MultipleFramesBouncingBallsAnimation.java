import biuoop.*;
import java.util.Random;
import java.awt.Color;

public class MultipleFramesBouncingBallsAnimation {
	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;
	public static final int MAX_BALL_SIZE = 50;
	public static final int MAX_SPEED = 200;

	public static void main(String[] args) {
		GUI gui = new GUI("Multiple Frames Bouncing Balls Animation", WIDTH, HEIGHT);
		Random rand = new Random();
		Sleeper sleeper = new Sleeper();
		Ball[] ballsArray = new Ball[args.length];
		int x;
		int y;
		for (int i = 0; i < args.length; i++){
			if (i < args.length / 2 ) {
				x = rand.nextInt(500) + 50;
				y = rand.nextInt(500) + 50;
			}  else {
				x = rand.nextInt(600) + 450;
				y = rand.nextInt(600) + 450;
			}

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
			ballsArray[i].setWidthAndHeightDistances(WIDTH, HEIGHT);
		}

		while (true) {
			DrawSurface d = gui.getDrawSurface();
			d.setColor(Color.GRAY);
			d.drawRectangle(50, 50, 450, 450);
			d.setColor(Color.YELLOW);
			d.drawRectangle(450, 450, 150, 150);

			for (Ball ball : ballsArray) {
				ball.moveOneStep();
				ball.drawOn(d);
			}
			gui.show(d);
			sleeper.sleepFor(20);  // wait for 50 milliseconds.

		}
	}
}
