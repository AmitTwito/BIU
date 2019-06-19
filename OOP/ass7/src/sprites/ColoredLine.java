package sprites;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;

import java.awt.Color;

/**
 * The ColoredLine class represents a sprite of colored line.
 *
 * @author Amit Twito
 */
public class ColoredLine implements Sprite {

    private Line line;
    private Color color;

    /**
     * A constructor for the ColoredLine class.
     *
     * @param line  The line.
     * @param color The color of the line.
     */
    public ColoredLine(Line line, Color color) {

        this.line = line;
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
        Point start = this.line.start();
        Point end = this.line.end();
        d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }

    /**
     * Notifies the interfaces.Sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
