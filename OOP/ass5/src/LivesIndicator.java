import biuoop.DrawSurface;

import java.awt.Color;

public class LivesIndicator implements Sprite {
    private Counter currentLives;
    private Rectangle rectangleToDrawTheTextOn;

    public LivesIndicator(Rectangle rectangleToDrawTheTextOn, Counter currentLives) {
        this.rectangleToDrawTheTextOn = rectangleToDrawTheTextOn;
        this.currentLives = currentLives;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        String textToDraw = "Lives: " + this.currentLives.getValue();

        int x = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getX()
                + this.rectangleToDrawTheTextOn.getWidth() - this.rectangleToDrawTheTextOn.getWidth() / 11);
        int y = (int) (this.rectangleToDrawTheTextOn.getUpperLeft().getY()
                + this.rectangleToDrawTheTextOn.getHeight() - 5);

        d.drawText(x, y, textToDraw, 20);
    }

    @Override
    public void timePassed() {

    }

}
