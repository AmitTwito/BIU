import java.lang.reflect.Member;

/**
 * The CollisionInfo class represents a CollisionInfo object.
 * A CollisionInfo class is represented by a collision point,
 * and a collidable object involved in the collision.
 *
 * @author Amit Twito
 * @since 23.3.19
 */
public class CollisionInfo {

    //Members.

    private Point collisionPoint;
    private Collidable collidableObject;

    //Constructors.

    /**
     * Constructor for the CollisionInfo class.
     * Builds a CollisionInfo with a collision point,
     * and the collidable object involved in the collision.
     *
     * @param point      The collision point.
     * @param collidable The collidable object..
     */
    public CollisionInfo(Point point, Collidable collidable) {

        this.collisionPoint = new Point(point.getX(), point.getY());
        this.collidableObject = collidable;
    }

    //Getters.

    /**
     * Returns the collision point.
     *
     * @return Collision point.
     */
    public Point collisionPoint() {
        return new Point(this.collisionPoint.getX(), this.collisionPoint.getY());
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return Collidable object.
     */
    public Collidable collisionObject() {
        return this.collidableObject;
    }
}