import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The Block class represents a block object,
 * A block is a rectangle that can be drawn to the screen,
 * can be collided with a ball ,has a color and can have hit points.
 *
 * @author Amit Twito
 * @since 24.3.19
 */
public class Block extends Rectangle implements Collidable, Sprite, HitNotifier {

    //Members.

    private Color color; // The color of the block.
    private int hitPoints; // The base hit points of the block.
    private List<HitListener> hitListeners;
    private boolean isBlockWithoutHitPoints; // For a paddle or border blocks.

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
        this.hitListeners = new ArrayList<>();
        this.isBlockWithoutHitPoints = false;
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
        this.hitListeners = new ArrayList<>();
        this.isBlockWithoutHitPoints = true;
    }

    //Getters.

    /**
     * Returns the rectangle that a collision was occurred to.
     *
     * @return Rectangle that a collision was occurred to.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return super.copy();
    }

    /**
     * Returns the Color of the block.
     *
     * @return Color of the block.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Returns the current hit points of the block.
     *
     * @return hit points of the block.
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Returns true if the current block is a border block.
     *
     * @return True if the current block is a border block.
     */
    public boolean isBlockWithoutHitPoints() {
        return this.isBlockWithoutHitPoints;
    }

    //Class Methods.

    /**
     * Returns a new Velocity based on where on the block the collision occurred.
     *
     * @param collisionPoint  The point where the collision occurred.
     * @param currentVelocity The current velocity of the ball, just before the collision.
     * @param hitter          The hitting ball.
     * @return New Velocity based on where on the block the collision occurred.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Line[] blockSides = getRectangleSides();
        Line top = blockSides[0]; //Top side
        Line left = blockSides[1]; //Left side
        Line bottom = blockSides[2];  //Bottom side
        Line right = blockSides[3]; //Right side

        //If the collision point is on the top or bottom sides of the block,
        //return and new velocity with a changed (multiplied by -1) vertical direction,
        if ((collisionPoint.getY() == top.start().getY()
                || collisionPoint.getY() == bottom.start().getY())
                && (collisionPoint.getX() >= top.start().getX()
                && collisionPoint.getX() <= top.end().getX())) {
            //Notify that was a hit.
            notifyHit(hitter);
            reduceHitPoints();
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        //If the collision point is on the left or right sides of the block,
        //return and new velocity with a changed (multiplied by -1) vertical direction,
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() >= left.start().getY()
                && collisionPoint.getY() <= left.end().getY())) {
            //Notify that was a hit.
            notifyHit(hitter);
            reduceHitPoints();
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        //If the collision point equals to one of the block's edges ,
        // change both vertical and horizontal directions.
        if (collisionPoint.equals(top.start()) || collisionPoint.equals(top.end())
                || collisionPoint.equals(bottom.start()) || collisionPoint.equals(bottom.end())) {
            //Notify that was a hit.
            notifyHit(hitter);
            reduceHitPoints();
            return new Velocity(-currentVelocity.getDx(), -currentVelocity.getDy());
        }

        notifyHit(hitter);
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


        if (!this.isBlockWithoutHitPoints) {
            surface.setColor(Color.BLACK);
            surface.drawRectangle(x1, y1, width, height);
        }
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

    /**
     * Removes this block from the given game.
     *
     * @param game The game to remove the block from.
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }


    /**
     * Notifies the listeners of the block that there was a hit.
     *
     * @param hitter The hitting ball.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Adds a HitListener as a listener to hit events.
     *
     * @param hl The HitListener to add to the list of listeners.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Removes hl from the list of listeners to hit events.
     *
     * @param hl The HitListener to remove from the list of listeners list.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
}
