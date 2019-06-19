package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The sprites.LevelIndicator indicates on the current level of the game.
 *
 * @author Amit Twito
 * @since 29.5.19
 */
public class LevelIndicator implements Sprite {
    private String levelName;
    private Rectangle rectangleToDrawTheTextOn;

    /**
     * A constructor for the sprites.LivesIndicator.
     *
     * @param rectangleToDrawTheTextOn The rectangle zone to draw the sprites.LivesIndicator.
     * @param levelName                The name of the level.
     */
    public LevelIndicator(Rectangle rectangleToDrawTheTextOn, String levelName) {
        this.rectangleToDrawTheTextOn = rectangleToDrawTheTextOn;
        this.levelName = levelName;
    }

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        String textToDraw = "Level: " + this.levelName;

        //Set the location on the right side at the top of the screen.
        int x = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getX()
                + this.rectangleToDrawTheTextOn.getWidth() - textToDraw.length() * 10);
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
