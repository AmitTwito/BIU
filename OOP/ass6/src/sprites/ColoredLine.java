package sprites;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import interfaces.Sprite;

import java.awt.Color;

public class ColoredLine implements Sprite {

	private Line line;
	private Color color;
	public ColoredLine(Line line, Color color) {

		this.line = line;
		this.color = color;
	}

	@Override
	public void drawOn(DrawSurface d) {

		d.setColor(this.color);
		Point start = this.line.start();
		Point end = this.line.end();
		d.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
	}

	@Override
	public void timePassed() {

	}
}
