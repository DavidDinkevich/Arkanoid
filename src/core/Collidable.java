// ID: 584698174

package core;

import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;

/**
 * Represents objects that can can collide/be collided with other
 * objects.
 * @author David Dinkevich
 */
public interface Collidable {

    /**
     * Get the collision shape of the object.
     * @return the collision shape
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * @param hitter the Ball colliding with the Collidable
     * @param collisionPoint the point of collision with the object
     * @param currentVelocity our velocity at the point of collision
     * @return the new expected vector after the collision
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

}
