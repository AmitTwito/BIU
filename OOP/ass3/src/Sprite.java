import biuoop.DrawSurface;

/**
 * The Sprite interface represents every object that can be drawn to the screen.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public interface Sprite {

    /**
     * Draws the Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the Sprite on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the Sprite that time has passed.
     */
    void timePassed();
}
