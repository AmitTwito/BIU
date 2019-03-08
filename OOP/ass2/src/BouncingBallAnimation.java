import biuoop.*;



public class BouncingBallAnimation {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static void main(String[] args) {

		GUI gui = new GUI("Bouncing Ball Animation", WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Ball ball = new Ball(0, 0, 30, java.awt.Color.BLACK);
		Velocity v = Velocity.fromAngleAndSpeed(90, 4);
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
