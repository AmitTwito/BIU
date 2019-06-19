package level;

import utility.AnimationRunner;
import animations.GameLevel;
import collidables.Block;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import interfaces.LevelInformation;
import interfaces.Sprite;
import movement.Velocity;
import sprites.ColoredCircle;
import sprites.ColoredLine;
import sprites.ColoredRectangle;
import sprites.LevelBackground;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the fourth level of the game.
 *
 * @author Amit Twito
 */
public class FourthLevel implements LevelInformation {

    public static final int BLOCK_LINES_NUMBER = 7; // Number of blocks lines.
    public static final int BLOCKS_NUMBER = GameLevel.MAX_BLOCKS_PER_ROW; // Number of block in each line.


    private int numberOfBalls;
    private int paddleWidth;
    private String levelName;
    private int numberOfBlocks;

    /**
     * A constructor for the fourth level.
     */
    public FourthLevel() {
        this.numberOfBalls = 3;
        this.paddleWidth = GameLevel.PADDLE_WIDTH;
        this.levelName = "Final Four";
        this.numberOfBlocks = BLOCKS_NUMBER * BLOCK_LINES_NUMBER;
    }

    /**
     * Returns the number of ball of the level.
     *
     * @return Number of balls.
     */
    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    /**
     * Returns list of velocities.
     *
     * @return List of velocities.
     */
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

    /**
     * Returns the paddle speed.
     *
     * @return Paddle speed.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleWidth / 9;
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

        LevelBackground levelBackground = new LevelBackground();

        Rectangle backgroundRectangle = new Rectangle(new Point(0, 0), AnimationRunner.GUI_WIDTH,
                AnimationRunner.GUI_HEIGHT);


        Color[] colors = new Color[7];
        colors[6] = new Color(23, 135, 207);
        colors[5] = new Color(32, 220, 253);
        colors[4] = new Color(22, 106, 254);
        colors[3] = new Color(0, 253, 1);
        colors[2] = new Color(253, 253, 1);
        colors[1] = new Color(238, 136, 22);
        colors[0] = new Color(253, 0, 1);

        levelBackground.addSprite(new ColoredRectangle(backgroundRectangle, colors[6]));

        int radius = (int) (AnimationRunner.GUI_WIDTH - 2 * GameLevel.BORDER_SIDE);
        Point center = new Point(AnimationRunner.GUI_WIDTH / 2, AnimationRunner.GUI_HEIGHT + 250);

        for (int i = 0; i < colors.length; i++) {

            ColoredCircle c = new ColoredCircle(center, radius, null, colors[i]);
            levelBackground.addSprite(c);
            radius = radius - 30;
        }

        colors = new Color[3];
        colors[2] = new Color(203, 203, 203);
        colors[1] = new Color(186, 186, 186);
        colors[0] = new Color(169, 169, 169);

        int x = AnimationRunner.GUI_WIDTH / 2 - 25;
        int y = 2 * AnimationRunner.GUI_HEIGHT / 3 - 90;

        int yPosition = AnimationRunner.GUI_HEIGHT;
        int xPosition = x - 50;
        Color c = new Color(220, 237, 247);

        for (int i = 1; i <= 10; i++) {
            Point start = new Point(xPosition, y);
            Point end = new Point(xPosition - 20, yPosition);

            Line line = new Line(start, end);
            levelBackground.addSprite(new ColoredLine(line, c));
            xPosition += 20;
        }

        center = new Point(x - 40, y + 30);
        radius = 40;
        ColoredCircle coloredCircle = new ColoredCircle(center, radius, null, colors[2]);
        levelBackground.addSprite(coloredCircle);

        center = new Point(x - radius, y + radius);
        radius = 50;
        coloredCircle = new ColoredCircle(center, radius, null, colors[2]);
        levelBackground.addSprite(coloredCircle);

        center = new Point(center.getX() + 60, y);
        radius = 60;
        coloredCircle = new ColoredCircle(center, radius, null, colors[1]);
        levelBackground.addSprite(coloredCircle);

        center = new Point(x, y);
        radius = 50;
        coloredCircle = new ColoredCircle(center, radius, null, colors[1]);
        levelBackground.addSprite(coloredCircle);

        center = new Point(x + 80, y + 20);
        radius = 70;
        coloredCircle = new ColoredCircle(center, radius, null, colors[0]);
        levelBackground.addSprite(coloredCircle);

        center = new Point(x + radius - 40, y + radius);
        radius = 50;
        coloredCircle = new ColoredCircle(center, radius, null, colors[0]);
        levelBackground.addSprite(coloredCircle);


        return levelBackground;
    }

    /**
     * Returns the number of blocks to remove.
     *
     * @return Number of blocks to remove.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
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
        addLevelBlocks(blocks);

        return blocks;
    }

    /**
     * Adds the blocks of the level.
     *
     * @param blocks List to add blocks to.
     * @throws RuntimeException when blocks are gotten off the bottom border of the screen.
     */
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
