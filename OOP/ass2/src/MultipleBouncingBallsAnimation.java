import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.util.Random;
import java.awt.Color;

/**
 * The MultipleBouncingBallsAnimation class generates a multiple bouncing balls animation.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 * */
public class MultipleBouncingBallsAnimation {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public static final int MAX_BALL_SIZE = 50;
    public static final int MAX_SPEED = 50;
    public static final double MAX_ANGLE = 90;
    public static final String GUI_TITLE = "Multiple Bouncing Balls Animation";
    public static final long SLEEP_MILLISECONDS = 40;


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
     * Generates a multiple bouncing balls animation, with a given list of ball sizes.
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

        /*Create a ball from each of the ball sizes, give it a random center point within the boundary frame,
         *and randomize it a color.*/
        for (int i = 0; i < ballSizes.length; i++) {
            int x = rand.nextInt(WIDTH) + 1;
            int y = rand.nextInt(HEIGHT) + 1;

            int radius = ballSizes[i];

            //Generate random numbers from 0 to 255 for a random color.
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            Color c = new Color(r, g, b);

            //Keep the ball in an array.
            ballsArray[i] = new Ball(x, y, radius, c);

            //Give the ball a random angle.
            double angle = MAX_ANGLE * rand.nextDouble();

            //Give balls with MAX_BALL_SIZE a permanent speed.Others - the smaller the faster.
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
            /* Move every ball one step and draw it on the surface on each frame -
             * all of the balls are drawn in one frame.*/
            for (Ball ball : ballsArray) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(SLEEP_MILLISECONDS);
        }

    }
    /**
     * Convert an array of Strings to int numbers.
     *
     * @param strings Array of Strings to convert to int numbers.
     * @return Array of converted int numbers.
     * */
    private static int[] stringsToIntegers(String[] strings) {
        int[] intArray = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            intArray[i] = Integer.parseInt(strings[i]);
        }

        return intArray;

    }
}
