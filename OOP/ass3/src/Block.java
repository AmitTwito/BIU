import biuoop.DrawSurface;
import java.awt.Color;

public class Block implements Collidable, Sprite {

    protected Rectangle rectangle;
    protected Color color;
    private int hitPoints;

    public Block(Rectangle rectangle, Color color, int hitPoints) {
        this.rectangle = new Rectangle(rectangle);
        this.color = color;
        this.hitPoints = hitPoints;
    }

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = new Rectangle(rectangle);
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        Line[] blockSides = this.rectangle.getRectangleSides();
        Line top = blockSides[0];//Top side
        Line left = blockSides[1];//Left side
        Line bottom = blockSides[2];//Bottom side
        Line right = blockSides[3];//Right side

        if ((collisionPoint.getY() == top.start().getY()
                || collisionPoint.getY() == bottom.start().getY())
                && (collisionPoint.getX() >= top.start().getX()
                && collisionPoint.getX() <= top.end().getX())) {
            reduceHitPoints();
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() >= left.start().getY()
                && collisionPoint.getY() <= left.end().getY())) {
            reduceHitPoints();
            return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
        return currentVelocity;
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

        surface.setColor(Color.WHITE);
        String textToDraw = this.hitPoints == 0 ? "X" : "" + this.hitPoints;

        int x = (int) (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() / 2);
        int y = (int) (this.rectangle.getUpperLeft().getY() + this.rectangle.getHeight() / 2);

        surface.drawText(x, y, textToDraw, 20);

    }

    @Override
    public void timePassed() {

    }

    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    public void reduceHitPoints() {
        int newHitPoints = this.hitPoints - 1;
        this.hitPoints = newHitPoints <= 0 ? 0 : newHitPoints;
    }

}
