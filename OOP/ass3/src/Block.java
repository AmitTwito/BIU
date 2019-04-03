import biuoop.DrawSurface;
import java.awt.Color;

public class Block implements Collidable, Sprite {

    private Rectangle rectangle;
    private Color color;

    public Block(Rectangle rectangle, Color color) {
        this.rectangle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight());
        this.color = color;
    }

    @Override
    public Rectangle getCollisionRectangle(){

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
                && collisionPoint.getX() <= top.end().getX()) ) {
            return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
        }
        if ((collisionPoint.getX() == left.start().getX()
                || collisionPoint.getX() == right.start().getX())
                && (collisionPoint.getY() >= left.start().getY()
                && collisionPoint.getY() <= left.end().getY()) ) {
			return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
		}
		return currentVelocity;
    }

	@Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);

        int x1 = (int) this.rectangle.getUpperLeft().getX();
        int y1 = (int) this.rectangle.getUpperLeft().getY();
        int x2 = x1 + (int) this.rectangle.getWidth();
        int y2 = y1 + (int) this.rectangle.getHeight();
        surface.fillRectangle(x1, y1, x2, y2);
    }

    @Override
    public void timePassed() {

	}

}
