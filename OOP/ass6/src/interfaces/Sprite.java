package interfaces;

import biuoop.DrawSurface;

/**
 * The interfaces.Sprite interface represents every object that can be drawn to the screen.
 *
 * @author Amit Twito
 * @since 30.3.19
 */
public interface Sprite {

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    void timePassed();
}
