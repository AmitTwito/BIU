import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.*;

public class Paddle extends Block implements Sprite, Collidable {
    private KeyboardSensor keyboard;
    private Rectangle bordersFrame;

    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard) {
        super(rectangle, color);
        this.keyboard = keyboard;
    }
    public void moveLeft() {
        double dx = this.rectangle.getWidth() / 10;
        double x = this.rectangle.getUpperLeft().getX() - dx;
        double y = this.rectangle.getUpperLeft().getY();
        Point newUpperLeft = new Point(x, y);
        this.rectangle.setUpperLeft(newUpperLeft);
    }

    public void moveRight() {
        double dx = this.rectangle.getWidth() / 10;
        double x = this.rectangle.getUpperLeft().getX() + dx;
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
    public void setBordersFrame(Rectangle frame) {
        this.bordersFrame = new Rectangle(frame.getUpperLeft(), frame.getWidth(), frame.getHeight());
    }
    // Collidable

    @Override
    public Rectangle getCollisionRectangle() {
        return super.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        this.rectangle.getWidth();


        return currentVelocity;
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }
}