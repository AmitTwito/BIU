import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import java.awt.Color;
public class Testing {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	public static final int RADIUS = 15;
	public static final String GUI_TITLE = "Bouncing Ball Animation";
	public static final int START_WIDTH = 10;
	public static final int START_HEIGHT = 10;
	public static final double ANGLE = 45;
	public static final double SPEED = 2;
	public static final long SLEEP_MILLISECONDS = 50;
	public static void main(String[] args) {

		GUI gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);
		Sleeper sleeper = new Sleeper();
		Ball ball = new Ball(START_WIDTH, START_HEIGHT, RADIUS, Color.BLACK);

		GameEnvironment ge = new GameEnvironment();

		Point upperLeft1 = new Point(50,50);
		Point upperLeft2 = new Point(150,150);

		Rectangle r1 = new Rectangle(upperLeft1, 50,70);
		Rectangle r2 = new Rectangle(upperLeft2, 60,80);

		ge.addCollidable(new Block(r1, Color.BLACK));
		ge.addCollidable(new Block(r2, Color.BLACK));
		ge.addCollidable(new Block(new Rectangle(new Point(0,0),400,4),Color.BLACK));
		ge.addCollidable(new Block(new Rectangle(new Point(0,0),4,400),Color.BLACK));
		ge.addCollidable(new Block(new Rectangle(new Point(0,396),400,4),Color.BLACK));
		ge.addCollidable(new Block(new Rectangle(new Point(396,0),4,400),Color.BLACK));
		//Set the ball's velocity.
		Velocity v = Velocity.fromAngleAndSpeed(ANGLE, SPEED);
		ball.setVelocity(v);
		ball.setGameEnvironment(ge);
		//Animate the ball's movement.
		// Each loop is a frame, after moving one step drawing - wait SLEEP_MILLISECONDS.
		while (true) {
			DrawSurface d = gui.getDrawSurface();
			ge.drawCollidableOn(d);
			ball.drawOn(d);
			ball.moveOneStep();
			gui.show(d);
			sleeper.sleepFor(SLEEP_MILLISECONDS);
		}

	}
}
