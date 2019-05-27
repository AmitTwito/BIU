package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import interfaces.Sprite;

import java.awt.Color;

public class ColoredCircle implements Sprite {

    private Point center;
    private int radius;
    private Color frameColor;
    private Color fillColor;

    public ColoredCircle(Point center, int radius, Color frameColor, Color fillColor) {
        this.center = center;
        this.radius = radius;
        this.frameColor = frameColor;
        this.fillColor = fillColor;
    }


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

    @Override
    public void timePassed() {

    }
}
