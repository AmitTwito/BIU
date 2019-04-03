import biuoop.DrawSurface;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GameEnvironment {

    private List<Collidable> collidablesList;


    public GameEnvironment() {
        this.collidablesList = new ArrayList<>();
    }


    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidablesList.add(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {

        boolean collisionExists = false;
        List<CollisionInfo> intersectionsList = new ArrayList<>();
        for (Collidable c : this.collidablesList) {
            Rectangle rectangle = c.getCollisionRectangle();
            Point point = trajectory.closestIntersectionToStartOfLine(rectangle);
            if (point != null) {
                intersectionsList.add(new CollisionInfo(point, c));
                collisionExists = true;
            }
        }
		if (!collisionExists) {
			return null;
		}

        CollisionInfo collisionInfo = intersectionsList.get(0);
        double smallestDistance = trajectory.start().distance(collisionInfo.collisionPoint());
		if (intersectionsList.size() > 1) {
			for (int i = 1; i < intersectionsList.size(); i++) {

				CollisionInfo currentCollisionInfo = intersectionsList.get(i);
				double currentDistance = trajectory.start().distance(currentCollisionInfo.collisionPoint());

				if (currentDistance < smallestDistance) {
					smallestDistance = currentDistance;
					collisionInfo = currentCollisionInfo;
				}
			}
		}
        return new CollisionInfo(collisionInfo.collisionPoint(), collisionInfo.collisionObject());
    }

    public void drawCollidableOn(DrawSurface surface) {
        for (Collidable c : this.collidablesList) {
            c.drawOn(surface);
        }
    }
}
