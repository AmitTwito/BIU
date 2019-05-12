import biuoop.DrawSurface;

/**
 * The Collidable interface represents every object that can be collided with.
 *
 * @author Amit Twito
 * @since 23.3.19
 */
public interface Collidable {

    /**
     * Returns the collision shape of the object.
     *
     * @return Collision shape of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that we collided with it at collisionPoint with
     * a given velocity. The return is the new velocity expected after the hit.
     *
     * @param collisionPoint  The point where the collision occurred.
     * @param currentVelocity The current velocity of the ball, just before the collision.
     * @return New velocity.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draws the collidable object on a given DrawSurface.
     *
     * @param surface The DrawSurface to draw the collidable object on.
     */
    void drawOn(DrawSurface surface);
}
