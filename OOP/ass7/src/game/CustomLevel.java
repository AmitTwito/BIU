package game;

import collidables.Block;
import factory.BlocksFromSymbolsFactory;
import interfaces.LevelInformation;
import interfaces.Sprite;
import movement.Velocity;
import sprites.LevelBackground;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for custom level of the game.
 *
 * @author Amit Twito
 */
public class CustomLevel implements LevelInformation {
    private String levelName;
    private List<Velocity> velocities;
    private String background;
    private int paddleSpeed;
    private int paddleWidth;
    private BlocksFromSymbolsFactory blocksFromSymbolsFactory;
    private int blockStartX;
    private int blockStartY;
    private int rowHeight;
    private int numBlocks;
    private String blockSymbolsString;

    /**
     * A constructor for the Level class.
     *
     * @param levelName                Name of level.
     * @param ballVelocities           Ball velocities.
     * @param background               Background of level.
     * @param paddleSpeed              Speed of paddle.
     * @param paddleWidth              Width of paddle.
     * @param blocksFromSymbolsFactory Blocks factory.
     * @param blockStartX              Blocks starting x position.
     * @param blockStartY              Blocks starting y position.
     * @param rowHeight                Row height.
     * @param numBlocks                Number of blocks.
     * @param blockSymbolsString       Block symbols string.
     */
    public CustomLevel(String levelName, List<Velocity> ballVelocities, String background,
                       int paddleSpeed, int paddleWidth, BlocksFromSymbolsFactory blocksFromSymbolsFactory
            , int blockStartX, int blockStartY, int rowHeight, int numBlocks, String blockSymbolsString) {
        this.levelName = levelName;
        this.velocities = ballVelocities;
        this.background = background;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocksFromSymbolsFactory = blocksFromSymbolsFactory;
        this.blockStartX = blockStartX;
        this.blockStartY = blockStartY;
        this.rowHeight = rowHeight;
        this.numBlocks = numBlocks;
        this.blockSymbolsString = blockSymbolsString;
    }

    /**
     * Returns the number of ball of the level.
     *
     * @return Number of balls.
     */
    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    /**
     * Returns list of velocities.
     *
     * @return List of velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * Returns the paddle speed.
     *
     * @return Paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Returns the paddle width.
     *
     * @return Paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Returns the name of the level.
     *
     * @return Name of the level.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return Sprite with the background of the level.
     */
    @Override
    public Sprite getBackground() {
        return new LevelBackground(this.background);
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return List of blocks.
     */
    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        String[] rows = this.blockSymbolsString.split("\n");

        int yPosition = this.blockStartY;
        for (String row : rows) {
            int startPositionX = this.blockStartX;
            if (!row.isEmpty()) {
                for (int i = 0; i < row.length(); i++) {
                    String symbol = "" + row.charAt(i);
                    if (this.blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                        startPositionX = startPositionX + this.blocksFromSymbolsFactory.getSpaceWidth(symbol);
                    }
                    if (this.blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                        Block block = this.blocksFromSymbolsFactory.getBlock(symbol, startPositionX, yPosition);
                        blocks.add(block);
                        startPositionX = startPositionX + (int) block.getWidth();
                    }

                }
                yPosition = yPosition + this.rowHeight;
            }
        }
        return blocks;
    }

    /**
     * Returns the number of blocks to remove.
     *
     * @return Number of blocks to remove.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }
}
