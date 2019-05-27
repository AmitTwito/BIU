package level;

import animations.AnimationRunner;
import animations.GameLevel;
import collidables.Block;
import geometry.Point;
import geometry.Rectangle;
import interfaces.LevelInformation;
import interfaces.Sprite;
import movement.Velocity;
import sprites.ColoredRectangle;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FourthLevel implements LevelInformation {

    public static final int BLOCK_LINES_NUMBER = 5; // Number of blocks lines.
    public static final int BLOCKS_NUMBER = GameLevel.MAX_BLOCKS_PER_ROW; // Number of block in each line.


    private int numberOfBalls;
    private int paddleWidth;
    private String levelName;
    private int numberOfBlocks;

    public FourthLevel() {
        this.numberOfBalls = 3;
        this.paddleWidth = GameLevel.PADDLE_WIDTH;
        this.levelName = "Final Four";
        this.numberOfBlocks = BLOCKS_NUMBER * BLOCK_LINES_NUMBER;
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();

        double angle = -45;
        for (int i = 0; i < numberOfBalls; i++) {

            velocities.add(Velocity.fromAngleAndSpeed(angle, GameLevel.BALL_SPEED));
            angle += 45;
        }

        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleWidth / 9;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    @Override
    public Sprite getBackground() {


        Rectangle backgroundRectangle = new Rectangle(new Point(0, 0), AnimationRunner.GUI_WIDTH,
                AnimationRunner.GUI_HEIGHT
        );
        Color color = new Color(23, 135, 207);

        return new ColoredRectangle(backgroundRectangle, color);
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocks;
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();
        addLevelBlocks(blocks);

        return blocks;
    }

    private void addLevelBlocks(List<Block> blocks) throws RuntimeException {

        //Build the inner colored blocks (random color) and add them to the game.

        //Build lines of same-color blocks by a y position on the screen.
        double yPosition = 5 * GameLevel.BLOCK_HEIGHT;

        try {
            //First set top row of blocks with 2 hit points.

            addColoredBlocks(GameLevel.generateRandomColor(), yPosition, 2, blocks);
            //Then for every other row (till BLOCK_LINES_NUMBER) add a row with 1 hit points.

            for (int i = 2; i <= BLOCK_LINES_NUMBER; i++) {
                //Starting from BLOCKS_NUMBER, each loop reduce the blocks number by one.
                //Change the position of each row by adding a BLOCK_HEIGHT to the y position.
                yPosition = yPosition + GameLevel.BLOCK_HEIGHT;
                if (yPosition >= AnimationRunner.GUI_HEIGHT - GameLevel.BORDER_SIDE) {
                    throw new RuntimeException("A row of blocks have gotten outside of the bottom border block "
                            + "and will not shown.");
                }
                //Add the row of blocks.s
                addColoredBlocks(GameLevel.generateRandomColor(), yPosition, 1, blocks);

            }
        } catch (Exception e) {
            //Pop a message window on the screen with the error.
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }


    }

    /**
     * Adds a row of colored blocks to the game.
     *
     * @param color     The color of the row.
     * @param yPosition The y position of the row.
     * @param hitPoints The hit points of the row.
     * @param blocks    The list of blocks to add the blocks.
     * @throws RuntimeException when blocks where drawn out of the left border block.
     */
    private void addColoredBlocks(Color color, double yPosition, int hitPoints,
                                  List<Block> blocks)
            throws RuntimeException {
        //Start the adding the blocks at point of startPositionX.
        double startPositionX = AnimationRunner.GUI_WIDTH - GameLevel.BORDER_SIDE;
        for (int i = 1; i <= BLOCKS_NUMBER; i++) {
            //Change the current startPositionX to startPositionX - BLOCK_WIDTH :
            // the next block will be in the left to the previous block.
            startPositionX = startPositionX - GameLevel.BLOCK_WIDTH;

            //build the block.
            geometry.Point upperLeft = new geometry.Point(startPositionX, yPosition);
            geometry.Rectangle rec = new Rectangle(upperLeft, GameLevel.BLOCK_WIDTH, GameLevel.BLOCK_HEIGHT);
            blocks.add(new Block(rec, color, hitPoints));


            if (startPositionX < GameLevel.BORDER_SIDE) {
                throw new RuntimeException("Blocks have gotten out of the left border block and will not be shown.");
            }
        }
    }
}
