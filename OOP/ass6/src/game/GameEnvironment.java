package game;

import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import utilities.CollisionInfo;
import interfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * The game.GameEnvironment class represents a game environment object.
 * A game.GameEnvironment  object is built by List of interfaces.Collidable objects,
 * objects that the ball can be collided with.
 *
 * @author Amit Twito
 * @since 23.3.19
 */
public class GameEnvironment {

    //Members.

    private List<Collidable> collidablesList; //The Collidables list.

    //Constructors.

    /**
     * Constructor for the game.GameEnvironment class.
     */
    public GameEnvironment() {
        this.collidablesList = new ArrayList<>();
    }

    //Class Methods.

    /**
     * Adds the given interfaces.Collidable to the environment.
     *
     * @param c The collidable to be added.
     */
    public void addCollidable(Collidable c) {
        this.collidablesList.add(c);
    }


    /**
     * Returns the information about the closest collision that is going to occur.
     * If this object will not collide with any of the collidables
     * in this collection, return null.
     *
     * @param trajectory The trajectory of the ball to check intersections with collidables on it's route.
     * @return Collision info of a collision occurred in the environment, or null if there'ss no collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //Assume that the object moves from line.start() to line.end().


        //First, calculate the closest intersection point to start of the trajectory,
        //with every collidable in the environment.

        //Set a collisionExists flag.
        boolean collisionExists = false;
        List<CollisionInfo> intersectionsList = new ArrayList<>();
        for (Collidable c : this.collidablesList) {
            Rectangle rectangle = c.getCollisionRectangle();
            Point point = trajectory.closestIntersectionToStartOfLine(rectangle);
            if (point != null) {
                //If there is an intersection, add the intersection point and the collidable object,
                // as a utilities.CollisionInfo to the intersectionsList.
                intersectionsList.add(new CollisionInfo(point, c));
                //Set the flag to true.
                collisionExists = true;
            }
        }
        //If there wasn't even one intersection, return null.
        if (!collisionExists) {
            return null;
        }

        //Else, foreach utilities.CollisionInfo in intersectionsList,
        // calculate the distance between the start of the trajectory, and find the smallest one.

        //Set the smallest distance as the first distance between the point of the first CollisionINfo
        //in the intersectionsList for comparing with the other CollisionInfos.
        CollisionInfo collisionInfo = intersectionsList.get(0);
        double smallestDistance = trajectory.start().distance(collisionInfo.collisionPoint());
        //If there was only one intersection, the first in the intersectionsList is the
        //utilities.CollisionInfo that we need.
        if (intersectionsList.size() > 1) {
            //Else, proceed on checking with the one after,
            for (int i = 1; i < intersectionsList.size(); i++) {

                //Calculate the current distance.
                CollisionInfo currentCollisionInfo = intersectionsList.get(i);
                double currentDistance = trajectory.start().distance(currentCollisionInfo.collisionPoint());
                //If the current distance is smaller then the first one, set the current collision info as the
                //One we need and the current distance as the smallest.
                if (currentDistance < smallestDistance) {
                    smallestDistance = currentDistance;
                    collisionInfo = currentCollisionInfo;
                }
            }
        }
        //Eventually, return the found utilities.CollisionInfo,
        return new CollisionInfo(collisionInfo.collisionPoint(), collisionInfo.collisionObject());
    }

    /**
     * Removes a collidable from the game environment.
     * @param c The collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.collidablesList.remove(c);
    }

}
