package factory;

import collidables.Block;
import interfaces.BlockCreator;

import java.util.Map;

public class BlocksFromSymbolsFactory {
	private Map<String, Integer> spacerWidths;
	private Map<String, BlockCreator> blockCreators;

	public int getSpaceWidth(String s) {
		return this.spacerWidths.get(s);
	}

	public Block getBlock(String s, int x, int y) {
		return this.blockCreators.get(s).create(x, y);
	}
}
