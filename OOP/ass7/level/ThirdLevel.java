package level;

import utility.AnimationRunner;
import animations.GameLevel;
import collidables.Block;
import geometry.Point;
import geometry.Rectangle;
import interfaces.LevelInformation;
import interfaces.Sprite;
import movement.Velocity;
import sprites.ColoredCircle;
import sprites.ColoredRectangle;
import sprites.LevelBackground;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the third level of the game.
 *
 * @author Amit Twito
 */
public class ThirdLevel implements LevelInformation {

    public static final int BLOCK_LINES_NUMBER = 5; // Number of blocks lines.
    public static final int BLOCKS_NUMBER = 10; // Number of block in each line.

    private int numberOfBalls;
    private int paddleWidth;
    private String levelName;

    /**
     * A constructor for the third level.
     */
    public ThirdLevel() {
        this.numberOfBalls = 2;
        this.paddleWidth = GameLevel.PADDLE_WIDTH;
        this.levelName = "Green 3";
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
            angle += -180;
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

        Color color = new Color(42, 129, 21);

        levelBackground.addSprite(new ColoredRectangle(backgroundRectangle, color));


        Rectangle towerRectangle = new Rectangle(
                new Point(GameLevel.BORDER_SIDE + 50, 2 * AnimationRunner.GUI_HEIGHT / 3),
                AnimationRunner.GUI_WIDTH / 7.5,
                AnimationRunner.GUI_HEIGHT / 3);
        ColoredRectangle coloredRectangle = new ColoredRectangle(towerRectangle, Color.BLACK);
        levelBackground.addSprite(coloredRectangle);

        double windowWidth = towerRectangle.getWidth() / 10;
        double windowHeight = towerRectangle.getHeight() / 7;
        double distanceBetweenWindows = towerRectangle.getHeight() / 23;


        double yPosition = towerRectangle.getUpperLeft().getY() - 20;
        double xPosition = towerRectangle.getUpperLeft().getX() - 10;
        for (int i = 0; i < 6; i++) {

            yPosition = yPosition + windowHeight + distanceBetweenWindows;
            for (int j = 0; j < 5; j++) {
                xPosition = xPosition + windowWidth + distanceBetweenWindows;
                Point upperLeft = new Point(xPosition, yPosition);
                Rectangle windowRectangle = new Rectangle(upperLeft, windowWidth, windowHeight);
                ColoredRectangle window = new ColoredRectangle(windowRectangle, Color.WHITE);
                levelBackground.addSprite(window);
            }

            xPosition = towerRectangle.getUpperLeft().getX() - 10;

        }
        double upperBuildHeight = 70;
        double upperBuildWidth = towerRectangle.getWidth() / 3;
        Point upperLeft = new Point(towerRectangle.getUpperLeft().getX() + upperBuildWidth,
                towerRectangle.getUpperLeft().getY() - upperBuildHeight);
        Rectangle rectangle = new Rectangle(upperLeft, upperBuildWidth, upperBuildHeight);
        coloredRectangle = new ColoredRectangle(rectangle, new Color(62, 58, 57));
        levelBackground.addSprite(coloredRectangle);

        upperBuildHeight = AnimationRunner.GUI_HEIGHT / 3.5;
        upperBuildWidth = upperBuildWidth / 3;
        upperLeft = new Point(upperLeft.getX() + upperBuildWidth, upperLeft.getY() - upperBuildHeight + 1);
        rectangle = new Rectangle(upperLeft, upperBuildWidth, upperBuildHeight);
        coloredRectangle = new ColoredRectangle(rectangle, new Color(78, 74, 73));
        levelBackground.addSprite(coloredRectangle);

        int radius = 16;

        Color[] colors = new Color[3];
        colors[2] = new Color(253, 253, 254);
        colors[1] = new Color(245, 77, 54);
        colors[0] = new Color(215, 171, 102);
        Point center = new Point(upperLeft.getX() + radius / 3, upperLeft.getY() - radius + radius);


        for (int i = 0; i < colors.length; i++) {

            ColoredCircle c = new ColoredCircle(center, radius, null, colors[i]);
            levelBackground.addSprite(c);
            radius = radius - 5;
        }
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
        int blocksNumber = BLOCKS_NUMBER;
        double yPosition = 5 * GameLevel.BLOCK_HEIGHT;

        try {
            //First set top row of blocks with 2 hit points.
            addColoredBlocks(blocksNumber, GameLevel.generateRandomColor(), yPosition, 2, blocks);
            //Then for every other row (till BLOCK_LINES_NUMBER) add a row with 1 hit points.
            for (int i = 2; i <= BLOCK_LINES_NUMBER; i++) {
                //Starting from BLOCKS_NUMBER, each loop reduce the blocks number by one.
                blocksNumber = blocksNumber - 1;
                //Change the position of each row by adding a BLOCK_HEIGHT to the y position.
                yPosition = yPosition + GameLevel.BLOCK_HEIGHT;
                if (yPosition >= AnimationRunner.GUI_HEIGHT - GameLevel.BORDER_SIDE) {
                    throw new RuntimeException("A row of blocks have gotten outside of the bottom border block "
                            + "and will not shown.");
                }
                //Add the row of blocks.
                addColoredBlocks(blocksNumber, GameLevel.generateRandomColor(), yPosition, 1, blocks);
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
     * @param blocksNumber The number of blocks in the row.
     * @param color        The color of the row.
     * @param yPosition    The y position of the row.
     * @param hitPoints    The hit points of the row.
     * @param blocks       The list of blocks to add the blocks.
     * @throws RuntimeException when blocks where drawn out of the left border block.
     */
    private void addColoredBlocks(int blocksNumber, Color color, double yPosition, int hitPoints,
                                  List<Block> blocks)
            throws RuntimeException {
        //Start the adding the blocks at point of startPositionX.
        double startPositionX = AnimationRunner.GUI_WIDTH - GameLevel.BORDER_SIDE;
        for (int i = 1; i <= blocksNumber; i++) {
            //Change the current startPositionX to startPositionX - BLOCK_WIDTH :
            // the next block will be in the left to the previous block.
            startPositionX = startPositionX - GameLevel.BLOCK_WIDTH;

            //build the block.
            Point upperLeft = new Point(startPositionX, yPosition);
            Rectangle rec = new Rectangle(upperLeft, GameLevel.BLOCK_WIDTH, GameLevel.BLOCK_HEIGHT);
            blocks.add(new Block(rec, color, hitPoints));

            if (startPositionX < GameLevel.BORDER_SIDE) {
                throw new RuntimeException("Blocks have gotten out of the left border block and will not be shown.");
            }
        }
    }
}
