import biuoop.*;

import java.awt.*;


public class BouncingBallAnimation {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;
	public static final int RADIUS = 20;
	public static void main(String[] args) {

		GUI gui = new GUI("Bouncing Ball Animation", WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Ball ball = new Ball(100, 0, RADIUS, java.awt.Color.BLACK);
		Velocity v = Velocity.fromAngleAndSpeed(45, 2);
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
}
