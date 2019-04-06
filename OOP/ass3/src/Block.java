import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Block class represents a block object,
 * A block is a rectangle ,and has a color and can have a hit points..
 *
 * @author Amit Twito
 * @since 24.3.19
 */
public class Block extends Rectangle implements Collidable, Sprite {

    //Members.

    protected Color color; // The color of the block.
    private int hitPoints; // The base hit points of the block.

    //Constructors.

    /**
     * Constructors for the Block class.
     *
     * @param rectangle The rectangle that represents the block.
     * @param color     The color of the block.
     * @param hitPoints The base hit points number of the block.
     */
    public Block(Rectangle rectangle, Color color, int hitPoints) {
        super(rectangle);
        this.color = color;
        this.hitPoints = hitPoints;
    }

    /**
     * Constructors for the Block class.
     *
     * @param rectangle The rectangle that represents the block.
     * @param color     The color of the block.
     */
    public Block(Rectangle rectangle, Color color) {
        super(rectangle);
        this.color = color;
    }

    //Getters.

    /**
     * Returns the collision rectangle of the block.
     *
     * @return
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    //Class Methods.

    /**
     * @param collisionPoint  The point where the collision occurred.
     * @param currentVelocity The current velocity of the ball, just before the collision.
     * @return New Velocity based on where on the block the collision occurred.
     * @throws Exception
     */
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Line[] blockSides = getRectangleSides();
        Line top = blockSides[0];//Top side
        Line left = blockSides[1];//Left side
        Line bottom = blockSides[2];//Bottom side
        Line right = blockSides[3];//Right side

        //If the collision point is on the top or bottom sides of the block,
        //return and new velocity with a changed (multiplied by -1) vertical direction,
        if ((collisionPoint.getY() == top.start().getY()
                || collisionPoint.getY() == bottom.start().getY())
                && (collisionPoint.getX() >= top.start().getX()
                && collisionPoint.getX() <= top.end().getX())) {
            reduceHitPoints();
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //If the collision point is on the left or right sides of the block,
        //return and new velocity with a changed (multiplied by -1) vertical direction,
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() >= left.start().getY()
                && collisionPoint.getY() <= left.end().getY())) {
            reduceHitPoints();
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        //If the collision point equals to one of the block's edges ,
		// change both vertical and horizontal directions.
        if (collisionPoint.equals(top.start()) || collisionPoint.equals(top.end())
				|| collisionPoint.equals(bottom.start()) || collisionPoint.equals(bottom.end())) {
			reduceHitPoints();
			return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
		}

        return currentVelocity;
    }


    /**
     * Draws the Block on a given DrawSurface.
     *
     * @param surface The DrawSurface to draw the Block on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        int x1 = (int) getUpperLeft().getX();
        int y1 = (int) getUpperLeft().getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        surface.fillRectangle(x1, y1, width, height);

        surface.setColor(Color.BLACK);
        surface.drawRectangle(x1, y1, width, height);

        surface.setColor(Color.WHITE);
        String textToDraw = this.hitPoints == 0 ? "X" : "" + this.hitPoints;

        int x = (int) (getUpperLeft().getX() + getWidth() / 2);
        int y = (int) (getUpperLeft().getY() + getHeight() / 2);

        surface.drawText(x, y, textToDraw, 20);

    }

    /**
     * Notifies the Block that time has passed.
     */
    @Override
    public void timePassed() {

    }

    /**
     * Adds the block to a given Game.
     *
     * @param g The game to add the block to.
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Reduces the current hit points of the block by one,
     * at a minimum number of 0 points.
     */
    public void reduceHitPoints() {
        int newHitPoints = this.hitPoints - 1;
        this.hitPoints = newHitPoints <= 0 ? 0 : newHitPoints;
    }

}
