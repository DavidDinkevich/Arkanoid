// ID: 584698174

package core;

import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameEnvironment is in charge of handling collisions
 * between Collidables.
 * @author David Dinkevich
 */
public class GameEnvironment {

    /** The list of collidables. */
    private List<Collidable> collidables;

    /**
     * Instantiates a GameEnvironment object.
     */
    public GameEnvironment() {
        collidables = new ArrayList<>();
    }

    /**
     * Add the given Collidable object to the environment.
     * @param c the Collidable object
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Removes a collidable from the GameEnvironment.
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }

    /**
     * Assume an object is moving from line.start() to line.end(). If this
     * object won't collide with any of the collidables in this collection,
     * return null. Else, return the information about the closest collision
     * that is going to occur.
     * @param trajectory the trajectory that will be checked for collisions
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // Information about the closest hitObject
        Collidable hitObject = null;
        Point closestPoint = null;

        for (Collidable obj : collidables) {
            Rectangle collShape = obj.getCollisionRectangle();
            // Get the closest intersection
            Point curr = trajectory.closestIntersectionToStartOfLine(collShape);
            if (curr != null) { // If there is an intersection
                double currDist = curr.distance(trajectory.start());
                // Compare it with the previous closest intersection
                if (hitObject == null || currDist < closestPoint.distance(trajectory.start())) {
                    hitObject = obj;
                    closestPoint = curr;
                }
            }
        }
        // If we didn't find a closest collision, return null. Otherwise,
        // create a CollisionInfo object with the closestPoint and hitObject.
        return hitObject == null ? null : new CollisionInfo(closestPoint, hitObject);
    }
}
