package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import utility.Counter;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The sprites.LivesIndicator indicates on the remaining lives in the game.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class LivesIndicator implements Sprite {
    private Counter currentLives;
    private Rectangle rectangleToDrawTheTextOn;

    /**
     * A constructor for the sprites.LivesIndicator.
     *
     * @param rectangleToDrawTheTextOn The rectangle zone to draw the sprites.LivesIndicator.
     * @param currentLives             The counter of the current lives.
     */
    public LivesIndicator(Rectangle rectangleToDrawTheTextOn, Counter currentLives) {
        this.rectangleToDrawTheTextOn = rectangleToDrawTheTextOn;
        this.currentLives = currentLives;
    }

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        String textToDraw = "Lives: " + this.currentLives.getValue();

        //Set the location on the right side at the top of the screen.

        int x = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getX());
        int y = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getY()
                + this.rectangleToDrawTheTextOn.getHeight() - 5);

        d.drawText(x, y, textToDraw, 20);
    }

    /**
     * Notifies the collidables.Block that time has passed.
     */
    @Override
    public void timePassed() {

    }

}
