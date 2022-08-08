// ID: 584698174

package communication;

import core.Ball;
import core.Block;
import core.Counter;
import levels.GameLevel;

/**
 * BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author David Dinkevich
 */
public class BlockRemover implements HitListener {
    /** The GameLevel to remove the Blocks from. */
    private GameLevel gameLevel;
    /** A Counter that stores the current number of Blocks. */
    private Counter remainingBlocks;

    /**
     * Instantiates a new BlockRemover object.
     * @param gameLevel the GameLevel from which the Blocks will be removed
     * @param remainingBlocks a Counter that keeps track of the current number of Blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(gameLevel);
        remainingBlocks.decrease(1);
    }
}