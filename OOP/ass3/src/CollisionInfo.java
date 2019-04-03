public class CollisionInfo {

	private Point collisionPoint;
	private Collidable collidableObject;

	public CollisionInfo(Point point, Collidable collidable) {

		this.collisionPoint = new Point(point.getX(), point.getY());
		this.collidableObject = collidable;
	}


	// the point at which the collision occurs.
	public Point collisionPoint() {
		return new Point(this.collisionPoint.getX(), this.collisionPoint.getY());
	}

	// the collidable object involved in the collision.
	public Collidable collisionObject() {
		return this.collidableObject;
	}
}