package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import utilities.Counter;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The sprites.ScoreIndicator indicates on score of the game.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class ScoreIndicator implements Sprite {

    private Rectangle rectangleToDrawTheTextOn;
    private Counter currentScore;

    /**
     * A constructor for the sprites.ScoreIndicator class.
     *
     * @param rectangleToDrawTheTextOn The rectangle zone to draw the sprites.ScoreIndicator.
     * @param currentScore             The counter of the current score.
     */
    public ScoreIndicator(Rectangle rectangleToDrawTheTextOn, Counter currentScore) {
        this.rectangleToDrawTheTextOn = rectangleToDrawTheTextOn;
        this.currentScore = currentScore;
    }

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        String textToDraw = "Score: " + this.currentScore.getValue();

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
