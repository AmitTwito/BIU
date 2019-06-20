package factory;

import collidables.Block;
import interfaces.BlockCreator;

import java.util.Map;

public class BlocksFromSymbolsFactory {
	private Map<String, Integer> spacerWidths;
	private Map<String, BlockCreator> blockCreators;


	public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths){
		this.spacerWidths = spacerWidths;

	}


	// returns true if 's' is a valid space symbol.
	public boolean isSpaceSymbol(String s) {
		return this.spacerWidths.get(s) == null? false : true;
	}
	// returns true if 's' is a valid block symbol.
	public boolean isBlockSymbol(String s) {
		return this.blockCreators.get(s) == null? false : true;
	}


	// Return a block according to the definitions associated
	// with symbol s. The block will be located at position (xpos, ypos).

	// Returns the width in pixels associated with the given spacer-symbol.
	public int getSpaceWidth(String s) {
		return this.spacerWidths.get(s);
	}

	public Block getBlock(String s, int x, int y) {
		return this.blockCreators.get(s).create(x, y);
	}
}
