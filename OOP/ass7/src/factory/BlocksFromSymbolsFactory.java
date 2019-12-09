package factory;

import collidables.Block;
import interfaces.BlockCreator;

import java.util.Map;

/**
 * The BlocksFromSymbolsFactory represents a factory of blocks from symbol.
 *
 * @author Amit Twito
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * A constructor for the BlocksFromSymbolsFactory class.
     *
     * @param spacerWidths  Spaces widths.
     * @param blockCreators Block creators.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;

    }


    /**
     * returns true if 's' is a valid space symbol.
     *
     * @param s Symbol.
     * @return returns true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.get(s) == null ? false : true;
    }

    /**
     * returns true if 's' is a valid block symbol.
     *
     * @param s Symbol.
     * @return returns true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.get(s) == null ? false : true;
    }


    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s Symbol of spacer.
     * @return Width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     *
     * @param s Symbol for block.
     * @param x x position.
     * @param y y position.
     * @return Block.
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}
