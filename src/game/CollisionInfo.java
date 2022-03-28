// ID: 584698174

package game;

import geometry.Point;

/**
 * Stores basic information about a collision--the object that was hit
 * and the location of the collision.
 * @author David Dinkevich
 */
public class CollisionInfo {
    /** The point at which the collision occurred. */
    private Point collisionPoint;
    /** The object that was hit in the collision. */
    private Collidable collisionObject;

    /**
     * Instantiates a new CollisionInfo object, with the given point of
     * collision and hit object.
     * @param collisionPoint the point of collision
     * @param collisionObject the object that was hit
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Get the point at which the collision occurs.
     * @return the point at which the collision occurs
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Get the Collidable object involved in the collision.
     * @return the Collidable object involved in the collision
     */
    public Collidable collisionObject() {
        return collisionObject;
    }

}
