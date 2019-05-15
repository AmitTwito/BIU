import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import javax.swing.JOptionPane;
import java.awt.Color;


/**
 * The Paddle class represents a paddle object.
 * A Paddle is a block that is controlled by the arrow keys,
 * and moves according to the key that the player presses.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public class Paddle extends Block implements Sprite, Collidable {

    //Constants.

    private static final int REGIONS_NUMBER = 5;
    private static final double MOVING_DISTANCE_DIVIDER = 10;

    //Members.

    private KeyboardSensor keyboard; // The keyboard sensor of the current gui.
    private Region movingRegion; // The region the paddle can move in.
    private Region[] hitRegions; // The different-behaviour-hit-regions for the ball.

    //Constructors.

    /**
     * Constructor for the Paddle class.
     *
     * @param rectangle The rectangle that the paddle will be represented by.
     * @param color     The color of the paddle.
     * @param keyboard  The KeyBoardSensor of the current gui.
     */
    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard) {
        super(rectangle, color);
        this.keyboard = keyboard;
    }

    //Getters.

    /**
     * Returns the Rectangle that a collision was occurred to.
     *
     * @return Rectangle that a collision was occurred to.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        //A paddle is a block which is a rectangle.
        return this;
    }

    //Setters.

    /**
     * @param first  Left value of the moving Region.
     * @param second Right value of the moving Region.
     * @throws RuntimeException when setting the moving region.
     */
    public void setMovingRegion(double first, double second) throws RuntimeException {
        try {
            this.movingRegion = new Region(first, second);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error: Paddle's moving region, " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Sets the hit Regions : each Region changes the ball's velocity differently.
     *
     * @throws RuntimeException when setting the hit regions.
     */
    private void setHitRegions() throws RuntimeException {

        try {
            //Divide the paddle to 5 regions, each region has equal length.
            this.hitRegions = new Region[REGIONS_NUMBER];
            double regionLength = getWidth() / REGIONS_NUMBER;
            double startX = getUpperLeft().getX();
            //Each regions second edge is the first edge of the previous one.
            this.hitRegions[0] = new Region(startX, startX + regionLength);
            this.hitRegions[1] = new Region(this.hitRegions[0].second(), this.hitRegions[0].second() + regionLength);
            this.hitRegions[2] = new Region(this.hitRegions[1].second(), this.hitRegions[1].second() + regionLength);
            this.hitRegions[3] = new Region(this.hitRegions[2].second(), this.hitRegions[2].second() + regionLength);
            this.hitRegions[4] = new Region(this.hitRegions[3].second(), this.hitRegions[3].second() + regionLength);
        } catch (Exception e) {
            System.out.println("Error: Paddle's hit regions\n" + e.getMessage());
        }

    }

    //Class methods.

    /**
     * Moves the paddle in left direction.
     */
    public void moveLeft() {
        //Set the distance that the paddle will be moved from his current position.
        double dX = getWidth() / MOVING_DISTANCE_DIVIDER;
        double x = getUpperLeft().getX() - dX;
        // If the paddle will be passing the left border of the movingRegion,
        // set the next point (x coordinate) to be exactly the left border.
        if (x < this.movingRegion.first()) {
            x = this.movingRegion.first();
        }
        double y = getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        //Change the current upper left point of the paddle to the new one.
        setUpperLeft(newUpperLeft);
    }

    /**
     * Moves the paddle in right direction.
     */
    public void moveRight() {
        //Set the distance that the paddle will be moved from his current position.
        double dX = getWidth() / MOVING_DISTANCE_DIVIDER;

        double x = getUpperLeft().getX() + dX;
        // If the paddle will be passing the right border of the movingRegion,
        // set the next point (x coordinate) to be exactly the right border.
        if (x > this.movingRegion.second() - getWidth()) {
            x = this.movingRegion.second() - getWidth();
        }
        double y = getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        //Change the current upper left point of the paddle to the new one.
        setUpperLeft(newUpperLeft);
    }

    /**
     * Notifies the paddle tha time has passed,
     * moves the paddle by a pressed button - right key or left key.
     */
    @Override
    public void timePassed() {

        //Check what key is pressed, then move to the matched direction.
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    /**
     * Draws the paddle on a given DrawSurface.
     *
     * @param surface The DrawSurface to draw the paddle on.s
     */
    @Override
    public void drawOn(DrawSurface surface) {
        //First FILL a rectangle with the paddle's color.
        surface.setColor(getColor());
        int x1 = (int) getUpperLeft().getX();
        int y1 = (int) getUpperLeft().getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        surface.fillRectangle(x1, y1, width, height);

        //Then DRAW a black rectangle - a black frame.
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x1, y1, width, height);
    }


    /**
     * Returns a new Velocity based on where on the Paddle the collision occurred.
     *
     * @param hitter The hitting ball.
     * @param collisionPoint  The collision point of the ball.
     * @param currentVelocity The current velocity of the ball.
     * @return New Velocity based on where on the paddle the collision occurred.
     * @throws RuntimeException if there was a problem with setting the hit regions.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) throws RuntimeException {

        //Check if the y coordinate of the collision is on the top side of the paddle.
        if (collisionPoint.getY() == getUpperLeft().getY()) {
            //Set hit regions.
            setHitRegions();

            //Get the speed in units of the ball.
            double speed = currentVelocity.toSpeed();
            if (this.hitRegions[0].isContains(collisionPoint.getX())) {
                //If the balls hits the first region, send the ball in 300 degrees.
                return Velocity.fromAngleAndSpeed(300, speed);
            } else if (this.hitRegions[1].isContains(collisionPoint.getX())) {
                //If the balls hits the second region, send the ball in 339 degrees.
                return Velocity.fromAngleAndSpeed(330, speed);
            } else if (this.hitRegions[2].isContains(collisionPoint.getX())) {
                //If the balls hits the third region, change the balls vertical direction.
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (this.hitRegions[3].isContains(collisionPoint.getX())) {
                //If the balls hits the fourth region, send the ball in 30 degrees.
                return Velocity.fromAngleAndSpeed(30, speed);
            } else if (this.hitRegions[4].isContains(collisionPoint.getX())
                    || collisionPoint.getX() == this.hitRegions[4].second()) {
                //If the balls hits the fifth region, send the ball in 60 degrees.
                //Needs to check if the collision point's x coordinate is the right edge of the paddle,
                //because of the way isContains is implemented.
                return Velocity.fromAngleAndSpeed(60, speed);
            }
        }

        Line[] blockSides = getRectangleSides();
        Line left = blockSides[1]; //Left side
        Line right = blockSides[3]; //Right side
        //If the ball hits the left or right sides of the paddle, change its velocity too so it wont get in it.
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() > left.start().getY()
                && collisionPoint.getY() < left.end().getY())) {
            return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
        }

        return currentVelocity;
    }


    /**
     * Adda this paddle to the game.
     *
     * @param g The game to add this paddle to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

}