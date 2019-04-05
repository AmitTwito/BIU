import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.*;

public class Paddle extends Block implements Sprite, Collidable {

    private static final int REGIONS_NUMBER = 5;

    private KeyboardSensor keyboard;
    private Region movingRegion;
    private Region[] hitRegions;

    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard) {
        super(rectangle, color);
        this.keyboard = keyboard;
    }

    public void moveLeft() {
        double dX = this.rectangle.getWidth() / 10;
        double x = this.rectangle.getUpperLeft().getX() - dX;
        if (x < this.movingRegion.left()) {
            x = this.movingRegion.left();
        }
        double y = this.rectangle.getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        this.rectangle.setUpperLeft(newUpperLeft);
    }

    public void moveRight() {
        double dX = this.rectangle.getWidth() / 10;
        double x = this.rectangle.getUpperLeft().getX() + dX;
        if (x > this.movingRegion.right() - this.rectangle.getWidth()) {
            x = this.movingRegion.right() - this.rectangle.getWidth();
        }
        double y = this.rectangle.getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        this.rectangle.setUpperLeft(newUpperLeft);
    }

    // Sprite
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        int x1 = (int) this.rectangle.getUpperLeft().getX();
        int y1 = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();
        surface.fillRectangle(x1, y1, width, height);

        surface.setColor(Color.BLACK);
        surface.drawRectangle(x1, y1, width, height);
    }

    public void setMovingRegion(double x1, double x2) {
        this.movingRegion = new Region(x1, x2);
    }

    // Collidable

    @Override
    public Rectangle getCollisionRectangle() {
        return super.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint.getY() == this.rectangle.getUpperLeft().getY()) {
            setHitRegions();
            double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
            if (this.hitRegions[0].isContains(collisionPoint.getX())) {
                return Velocity.fromAngleAndSpeed(300, speed);
            } else if (this.hitRegions[1].isContains(collisionPoint.getX())) {
                return Velocity.fromAngleAndSpeed(330, speed);
            } else if (this.hitRegions[2].isContains(collisionPoint.getX())) {
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            } else if (this.hitRegions[3].isContains(collisionPoint.getX())) {
                return Velocity.fromAngleAndSpeed(30, speed);
            } else if (this.hitRegions[4].isContains(collisionPoint.getX())
                    || collisionPoint.getX() == this.hitRegions[4].left()) {
                return Velocity.fromAngleAndSpeed(60, speed);
            }
        }

        return currentVelocity;
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    private void setHitRegions() {
        this.hitRegions = new Region[REGIONS_NUMBER];
        double regionLength = this.rectangle.getWidth() / REGIONS_NUMBER;
        double startX = this.rectangle.getUpperLeft().getX();
        this.hitRegions[0] = new Region(startX, startX + regionLength);
        this.hitRegions[1] = new Region(this.hitRegions[0].right(), this.hitRegions[0].right() + regionLength);
        this.hitRegions[2] = new Region(this.hitRegions[1].right(), this.hitRegions[1].right() + regionLength);
        this.hitRegions[3] = new Region(this.hitRegions[2].right(), this.hitRegions[2].right() + regionLength);
        this.hitRegions[4] = new Region(this.hitRegions[3].right(), this.hitRegions[3].right() + regionLength);
    }
}