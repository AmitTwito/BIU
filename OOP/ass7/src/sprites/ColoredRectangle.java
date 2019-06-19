package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The ColoredRectangle class represents a sprite of colored rectangle.
 *
 * @author Amit Twito
 */
public class ColoredRectangle implements Sprite {

    private Rectangle rectangle;
    private Color color;

    /**
     * A constructor for the ColoredRectangle class.
     *
     * @param rectangle The rectangle.
     * @param color     The color of the rectangle.
     */
    public ColoredRectangle(Rectangle rectangle, Color color) {
        this.rectangle = new Rectangle(rectangle);
        this.color = color;
    }

    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }

    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
