import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.util.Random;
import java.awt.Color;

/**
 * The MultipleFramesBouncingBallsAnimation class generates a multiple frames
 * with multiple bouncing balls (inside them) animation.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 7.3.19
 */
public class MultipleFramesBouncingBallsAnimation {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final int MAX_BALL_SIZE = 50;
    public static final int MAX_SPEED = 50;
    public static final String GUI_TITLE = "Multiple Frames Bouncing Balls Animation";
    public static final long SLEEP_MILLISECONDS = 20;
    public static final double MAX_ANGLE = 90;

    /**
     * Generates a MultipleFramesBouncingBallsAnimation with a given list of ball sizes.
     *
     * @param args Array of String ball sizes from the command line.
     */
    public static void main(String[] args) {
        int[] ballSizes = stringsToIntegers(args);
        MultipleFramesBouncingBallsAnimation multipleFramesBouncingBallsAnimation =
                new MultipleFramesBouncingBallsAnimation();
        multipleFramesBouncingBallsAnimation.generateAnimation(ballSizes);
    }

    /**
     * Generates a multiple frames with bouncing balls animation, with a given list of ball sizes.
     *
     * @param ballSizes Array of int ball sizes.
     */
    public void generateAnimation(int[] ballSizes) {
        GUI gui = new GUI(GUI_TITLE, WIDTH, HEIGHT);

        //Set the corners of the frames.
        Point firstLeftTopCorner = new Point(50, 50);
        Point firstRightBottomCorner = new Point(500, 500);
        Point secondLeftTopCorner = new Point(450, 450);
        Point secondRightBottomCorner = new Point(600, 600);

        //Set the frames.
        RectangleFrame greyRectangleFrame = new RectangleFrame(firstLeftTopCorner,
                firstRightBottomCorner, Color.GRAY);
        RectangleFrame yellowRectangleFrame = new RectangleFrame(secondLeftTopCorner,
                secondRightBottomCorner, Color.YELLOW);
        Random rand = new Random();
        Sleeper sleeper = new Sleeper();
        Ball[] ballsArray = new Ball[ballSizes.length];


        /*Create a ball from each of the ball sizes, give it a random center point within the boundary frame,
         *and randomize it a color.*/
        for (int i = 0; i < ballSizes.length; i++) {
            /*The first half of the balls needs to be within the greyRectangleFrame,
             *the other half within the yellowRectangleFrame.*/
            int x;
            int y;
            int highValue;
            int lowValue;
            /*The first half of the balls needs to be within the greyRectangleFrame,
             *the other half within the yellowRectangleFrame.
             *So, generate random coordinates within the matched boundary frames area.
             */
            if (i < ballSizes.length / 2) {
                highValue = (int) firstRightBottomCorner.getX();
                lowValue = (int) firstLeftTopCorner.getX();
                x = rand.nextInt(highValue - lowValue + 1) + lowValue;
                highValue = (int) firstRightBottomCorner.getY();
                lowValue = (int) firstLeftTopCorner.getY();
                y = rand.nextInt(highValue - lowValue + 1) + lowValue;
            } else {
                highValue = (int) secondRightBottomCorner.getX();
                lowValue = (int) secondLeftTopCorner.getX();
                x = rand.nextInt(highValue - lowValue + 1) + lowValue;
                highValue = (int) secondRightBottomCorner.getY();
                lowValue = (int) secondLeftTopCorner.getY();
                y = rand.nextInt(highValue - lowValue + 1) + lowValue;
            }

            int radius = ballSizes[i];

            //Generate random numbers from 0 to 255 for a random color.
            int r = rand.nextInt(255);
            int g = rand.nextInt(255);
            int b = rand.nextInt(255);
            Color c = new Color(r, g, b);

            //Keep the ball in an array.
            ballsArray[i] = new Ball(x, y, radius, c);

            //Give the ball a random angle angle between 0 to MAX_ANGLE.
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

            /*The first half of the balls needs to be within the greyRectangleFrame,
             *the other half within the yellowRectangleFrame.*/
            if (i < ballSizes.length / 2) {
                ballsArray[i].setBoundaryFrame(greyRectangleFrame);
            } else {
                ballsArray[i].setBoundaryFrame(yellowRectangleFrame);
            }
        }

        while (true) {
            /*Every loop is a frame - in every frame draw both frames,
             *then move one step every ball and draw them,
             *then sleep for SLEEP_MILLISECONDS. */
            DrawSurface d = gui.getDrawSurface();
            greyRectangleFrame.drawOn(d);
            yellowRectangleFrame.drawOn(d);

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
     */
    private static int[] stringsToIntegers(String[] strings) {

        int[] intArray = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            intArray[i] = Integer.parseInt(strings[i]);
        }

        return intArray;

    }
}
