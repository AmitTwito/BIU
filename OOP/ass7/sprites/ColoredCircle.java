package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The ColoredCircle class represents a sprite of colored circle.
 *
 * @author Amit Twito
 */
public class ColoredCircle implements Sprite {

    private Point center;
    private int radius;
    private Color frameColor;
    private Color fillColor;

    /**
     * A constructor for the ColoredCircle class.
     *
     * @param center     The center of the circle.
     * @param radius     The radius of the circle.
     * @param frameColor The frame color.
     * @param fillColor  The fill color.
     */
    public ColoredCircle(Point center, int radius, Color frameColor, Color fillColor) {
        this.center = center;
        this.radius = radius;
        this.frameColor = frameColor;
        this.fillColor = fillColor;
    }


    /**
     * Draws the interfaces.Sprite on a given DrawSurface (to the screen).
     *
     * @param d The DrawSurface to draw the interfaces.Sprite on.
     */
    @Override
    public void drawOn(DrawSurface d) {

        if (this.frameColor != null) {
            d.setColor(this.frameColor);
            d.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        }
        if (this.fillColor != null) {
            d.setColor(this.fillColor);
            d.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
        }

    }

    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
