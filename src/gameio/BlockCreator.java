// ID: 584698174

package gameio;

import core.Block;

/**
 * Defines behavior for creating a Block at a given (x, y) coordinate.
 * @author David Dinkevich
 */
public interface BlockCreator {
    /**
     * Creates a block at the specified location.
     * @param xpos the x location
     * @param ypos the y location
     * @return the Block
     */
    Block create(int xpos, int ypos);

}
