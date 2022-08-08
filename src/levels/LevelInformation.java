// ID: 584698174

package levels;

import core.Block;
import core.Sprite;

import geometry.Velocity;

import java.util.List;

/**
 * Contains critical information about levels.
 */
public interface LevelInformation {
    /**
     * Get the initial number of balls in the level.
     * @return the initial number of balls in the level
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball. Note that
     * initialBallVelocities().size() == numberOfBalls().
     * @return the initial velocities of the balls
     */
    List<Velocity> initialBallVelocities();

    /**
     * Get the speed of the paddle.
     * @return the speed of the paddle
     */
    int paddleSpeed();

    /**
     * Get the width of the paddle.
     * @return the width of the paddle
     */
    int paddleWidth();

    /**
     * Get the name of the level.
     * @return the name of the level
     */
    String levelName();

    /**
     * Get a Sprite representing the background of the level.
     * @return a Sprite representing the background of the level
     */
    Sprite getBackground();

    /**
     * Get the blocks that make up this level.
     * @return the blocks that make up this level
     */
    List<Block> blocks();

    /**
     * Grt the number of blocks that should be removed before this
     * level is considered to be "cleared".
     * @return the number of blocks that should be removed before this
     * level is considered to be "cleared".
     */
    int numberOfBlocksToRemove();
}
