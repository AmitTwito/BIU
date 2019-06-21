package interfaces;

import collidables.Block;

/**
 * The BlockCreator interface represents block creators objects.
 *
 * @author Amit Twito
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos X position of the block.
     * @param ypos Y position of the block.
     * @return New Block.
     */
    Block create(int xpos, int ypos);
}
