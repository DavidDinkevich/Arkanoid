// ID: 584698174

package listeners;

import game.Ball;
import game.Block;
import game.Counter;

/**
 * Listens for collision events between balls and blocks, and increases
 * the score accordingly.
 * @author David Dinkevich
 */
public class ScoreTrackingListener implements HitListener {
    /** A counter that keeps track of the score. */
    private Counter currentScore;

    /**
     * Instantiates a new ScoreTrackingListener object.
     * @param scoreCounter a Counter that keeps track of the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(5); // Hit the block
    }
}
