// ID: 584698174

package listeners;

import game.Ball;
import game.Block;
import game.Counter;
import game.Game;

/**
 * BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 * @author David Dinkevich
 */
public class BlockRemover implements HitListener {
    /** The Game to remove the Blocks from. */
    private Game game;
    /** A Counter that stores the current number of Blocks. */
    private Counter remainingBlocks;

    /**
     * Instantiates a new BlockRemover object.
     * @param game the Game from which the Blocks will be removed
     * @param remainingBlocks a Counter that keeps track of the current number of Blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(game);
        remainingBlocks.decrease(1);
    }
}