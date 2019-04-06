import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

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
     * @return Rectangle that a collision was occurred to.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    //Setters.

    /**
     * @param left  Left value of the moving Region.
     * @param right Right value of the moving Region.
     */
    public void setMovingRegion(double left, double right) {
        this.movingRegion = new Region(left, right);
    }

    /**
     * Sets the hit Regions : each Region changes the ball's velocity differently.
     */
    private void setHitRegions() {

        //Divide the paddle to 5 regions, each region has equal length.
        this.hitRegions = new Region[REGIONS_NUMBER];
        double regionLength = getWidth() / REGIONS_NUMBER;
        double startX = getUpperLeft().getX();
        this.hitRegions[0] = new Region(startX, startX + regionLength);
        this.hitRegions[1] = new Region(this.hitRegions[0].right(), this.hitRegions[0].right() + regionLength);
        this.hitRegions[2] = new Region(this.hitRegions[1].right(), this.hitRegions[1].right() + regionLength);
        this.hitRegions[3] = new Region(this.hitRegions[2].right(), this.hitRegions[2].right() + regionLength);
        this.hitRegions[4] = new Region(this.hitRegions[3].right(), this.hitRegions[3].right() + regionLength);
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
        if (x < this.movingRegion.left()) {
            x = this.movingRegion.left();
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
        if (x > this.movingRegion.right() - getWidth()) {
            x = this.movingRegion.right() - getWidth();
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
        surface.setColor(this.color);
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
     * @param collisionPoint  The collision point of the ball.
     * @param currentVelocity The current velocity of the ball.
     * @return New Velocity based on where on the paddle the collision occurred.
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {

        //Check if the y coordinate of the collision is on the top side of the paddle.
        if (collisionPoint.getY() == getUpperLeft().getY()) {
            //Set hit regions.
            setHitRegions();
            //Calculate the current speed : The speed vector is the longest side of a triangular,
            //we can get that by the square root of the sum of the squares of the other two sides,
            //which here are the velocity's changes in the x axis and y axis.
            double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));

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
                    || collisionPoint.getX() == this.hitRegions[4].left()) {
                //If the balls hits the fifth region, send the ball in 60 degrees.
                //
                return Velocity.fromAngleAndSpeed(60, speed);
            }
        }

        Line[] blockSides = getRectangleSides();
        Line left = blockSides[1];//Left side
		Line bottom = blockSides[2];//Bottom side
        Line right = blockSides[3];//Right side
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() > left.start().getY()
                && collisionPoint.getY() < left.end().getY())) {
            return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
        }
		/*if (collisionPoint.getY() == bottom.start().getY()) {
			throw new Exception("The ball hit the bottom side of the paddle.");
		}*/


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