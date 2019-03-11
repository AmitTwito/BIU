import biuoop.DrawSurface;

import java.awt.*;

public class RectangleFrame {

	private Point leftTopCorner;
	private Point rightBottomCorner;
	private Color color;
	public RectangleFrame(Point rightBottomCorner) {
		this.leftTopCorner = new Point(0,0);
		this.rightBottomCorner = new Point(rightBottomCorner.getX(), rightBottomCorner.getY());
	}

	public RectangleFrame(Point  leftTopCorner,  Point rightBottomCorner) {
		this.leftTopCorner = new Point(leftTopCorner.getX(),leftTopCorner.getY());
		this.rightBottomCorner = new Point(rightBottomCorner.getX(), rightBottomCorner.getY());
	}

	public Point getLeftTopCorner() {return leftTopCorner;}

	public Point getRightBottomCorner() { return rightBottomCorner; }

	public void drawOn(DrawSurface surface) {
		surface.setColor(this.color);

		int x = (int)this.leftTopCorner.getX();
		int y = (int)this.leftTopCorner.getY();
		int rectangleWidth = (int)(this.rightBottomCorner.getX() - x);
		int rectangleHieght = (int)(this.rightBottomCorner.getY() - y);

		surface.drawRectangle(x, y, rectangleWidth, rectangleHieght);
	}
}
