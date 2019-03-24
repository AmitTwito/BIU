public class Block implements Collidable {

	private Rectangle rectangle;

	@Override
	public Rectangle getCollisionRectangle(){

		return this.rectangle;
	}

	@Override
	public Velocity hit(Point collisionPoint, Velocity currentVelocity) {

		return null;
	}

}
