// ID: 584698174

package communication;

import core.Ball;
import core.Block;
import core.Counter;
import levels.GameLevel;

/**
 * A listener that removes Balls that collide with
 * certain Blocks.
 * @author David Dinkevich
 */
public class BallRemover implements HitListener {
    /** The GameLevel to remove the balls from. */
    private GameLevel gameLevel;
    /** A Counter that stores the current number of Balls. */
    private Counter numBalls;

    /**
     * Instantiates a new BallRemover object.
     * @param gameLevel the GameLevel from which the Balls will be removed
     * @param numBalls a Counter that keeps track of the current number of Balls
     */
    public BallRemover(GameLevel gameLevel, Counter numBalls) {
        this.gameLevel = gameLevel;
        this.numBalls = numBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        numBalls.decrease(1);
        hitter.removeFromGame(gameLevel);
    }
}
