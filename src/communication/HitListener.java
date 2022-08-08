// ID: 584698174

package communication;

import core.Ball;
import core.Block;

/**
 * A listener that listens for collisions between Blocks and Balls.
 * @author David Dinkevich
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit by
     * the hitter object.
     * @param beingHit the Block that was hit
     * @param hitter the Ball that hit the Block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
