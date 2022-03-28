// ID: 584698174

package listeners;

import game.Ball;
import game.Block;
import game.Counter;
import game.Game;

/**
 * A listener that removes Balls that collide with
 * certain Blocks.
 * @author David Dinkevich
 */
public class BallRemover implements HitListener {
    /** The game to remove the balls from. */
    private Game game;
    /** A Counter that stores the current number of Balls. */
    private Counter numBalls;

    /**
     * Instantiates a new BallRemover object.
     * @param game the Game from which the Balls will be removed
     * @param numBalls a Counter that keeps track of the current number of Balls
     */
    public BallRemover(Game game, Counter numBalls) {
        this.game = game;
        this.numBalls = numBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        numBalls.decrease(1);
        hitter.removeFromGame(game);
    }
}
