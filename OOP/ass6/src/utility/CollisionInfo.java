package utility;

import geometry.Point;
import interfaces.Collidable;

/**
 * The utility.CollisionInfo class represents a collision info object.
 * A utility.CollisionInfo class is represented by a collision point,
 * and a collidable object involved in the collision.
 *
 * @author Amit Twito
 * @since 23.3.19
 */
public class CollisionInfo {

    //Members.

    private Point collisionPoint; // The point where the collision occurred.
    private Collidable collidableObject; // The collidable object.

    //Constructors.

    /**
     * Constructor for the utility.CollisionInfo class.
     * Builds a utility.CollisionInfo with a collision point,
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
     * @return interfaces.Collidable object.
     */
    public Collidable collisionObject() {
        return this.collidableObject;
    }
}