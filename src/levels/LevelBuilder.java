// ID: 584698174

package levels;

import core.Block;
import core.Sprite;

import geometry.Velocity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A concrete, Map-backed implementation of the LevelInformation interface.
 * @author David Dinkevich
 */
public class LevelBuilder implements LevelInformation {

    /** The map that stores the level information. */
    private Map<String, String> data;
    /** List of the blocks in the level. */
    private List<Block> blocks;
    /** The background of the level. */
    private Sprite background;

    /**
     * Instantiates a new LevelBuilder object.
     * @param data a map containing the level data
     * @param background a sprite background of the level
     * @param blocks the list of blocks of the level
     */
    public LevelBuilder(Map<String, String> data, Sprite background, List<Block> blocks) {
        this.data = data;
        this.background = background;
        this.blocks = blocks;
    }

    @Override
    public List<Block> blocks() {
        return blocks;
    }

    @Override
    public Sprite getBackground() {
        return background;
    }

    @Override
    public int numberOfBalls() {
        return initialBallVelocities().size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // Velocity is stored as a string in the form:
        // "a,b c,d..."
        String[] velocities = data.get("ball_velocities").split(" "); // Split around spaces
        List<Velocity> ballVelocities = new ArrayList<>();
        // Convert each angle-speed pair to a Velocity object
        for (String vel : velocities) {
            String[] components = vel.split(",");
            ballVelocities.add(Velocity.fromAngleAndSpeed(
                    Integer.parseInt(components[0]),
                    Integer.parseInt(components[1])
            ));
        }
        return ballVelocities;
    }

    @Override
    public int paddleSpeed() {
        return Integer.parseInt(data.get("paddle_speed"));
    }

    @Override
    public int paddleWidth() {
        return Integer.parseInt(data.get("paddle_width"));
    }

    @Override
    public String levelName() {
        if (!data.containsKey("level_name")) {
            throw new RuntimeException("Level information missing");
        }
        return data.get("level_name");
    }

    @Override
    public int numberOfBlocksToRemove() {
        return Integer.parseInt(data.get("num_blocks"));
    }

}
