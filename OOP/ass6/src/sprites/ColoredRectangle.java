package sprites;

import biuoop.DrawSurface;
import geometry.Rectangle;
import interfaces.Sprite;

import java.awt.Color;

public class ColoredRectangle implements Sprite {

	private Rectangle rectangle;
	private Color color;

	public ColoredRectangle(Rectangle rectangle, Color color) {
		this.rectangle = new Rectangle(rectangle);
		this.color = color;
	}

	@Override
	public void drawOn(DrawSurface d) {
		d.setColor(this.color);
		d.fillRectangle((int) this.rectangle.getUpperLeft().getX(),(int)  this.rectangle.getUpperLeft().getY(),
				(int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
	}

	@Override
	public void timePassed() {

	}
}
