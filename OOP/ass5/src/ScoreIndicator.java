import biuoop.DrawSurface;

import java.awt.Color;

public class ScoreIndicator implements Sprite {

	private Rectangle rectangleToDrawTheTextOn;
	private Counter currentScore;

	public ScoreIndicator(Rectangle rectangleToDrawTheTextOn, Counter currentScore) {
		this.rectangleToDrawTheTextOn = rectangleToDrawTheTextOn;
		this.currentScore = currentScore;
	}

	@Override
	public void drawOn(DrawSurface d) {
		d.setColor(Color.BLACK);
		String textToDraw = "Score: " + this.currentScore.getValue();

		int x = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getX());
						//+ this.rectangleToDrawTheTextOn.getWidth() / 2);
		int y = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getY()
				+ this.rectangleToDrawTheTextOn.getHeight() - 5);

		d.drawText(x, y, textToDraw, 20);
	}

	@Override
	public void timePassed() {

	}

}
