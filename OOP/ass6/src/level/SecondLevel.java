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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SecondLevel implements LevelInformation {

    private int numberOfBalls;
    private int paddleWidth;
    private String levelName;
    private int blocksNumber;

    public SecondLevel() {
        this.numberOfBalls = 10;
        this.paddleWidth = 600;
        this.levelName = "Wide Easy";
        this.blocksNumber = GameLevel.MAX_BLOCKS_PER_ROW;
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();

        double angle = -50;
        for (int i = 0; i < 5; i++) {

            velocities.add(Velocity.fromAngleAndSpeed(angle, GameLevel.BALL_SPEED));
            angle += -8;
        }

        angle = 50;
        for (int i = 0; i < 5; i++) {

            velocities.add(Velocity.fromAngleAndSpeed(angle, GameLevel.BALL_SPEED));
            angle += 8;
        }

        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleWidth / 200;
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
                AnimationRunner.GUI_HEIGHT);

        return new ColoredRectangle(backgroundRectangle, Color.WHITE);
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksNumber;
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        Color[] colors = new Color[this.blocksNumber];

        int num = (this.blocksNumber - 3) / 2;

        colors[0] = GameLevel.generateRandomColor();
        colors[1] = colors[0];
        colors[2] = GameLevel.generateRandomColor();
        colors[3] = colors[2];
        colors[4] = GameLevel.generateRandomColor();
        colors[5] = colors[4];
        colors[6] = GameLevel.generateRandomColor();
        colors[7] = colors[6];
        colors[8] = colors[6];
        colors[9] = GameLevel.generateRandomColor();
        colors[10] = colors[9];
        colors[11] = GameLevel.generateRandomColor();
        colors[12] = colors[11];
        colors[13] = GameLevel.generateRandomColor();
        colors[14] = colors[13];

        //Start the adding the blocks at point of startPositionX.

        double yPosition = 10 * GameLevel.BLOCK_HEIGHT;
        double startPositionX = AnimationRunner.GUI_WIDTH  - GameLevel.BORDER_SIDE;
        for (int i = 0; i < this.blocksNumber; i++) {
			//Change the current startPositionX to startPositionX - BLOCK_WIDTH :
			// the next block will be in the left to the previous block.
			startPositionX = startPositionX - GameLevel.BLOCK_WIDTH;

			//build the block.
            Point upperLeft = new Point(startPositionX, yPosition);
            Rectangle rec = new Rectangle(upperLeft, GameLevel.BLOCK_WIDTH, GameLevel.BLOCK_HEIGHT);
            blocks.add(new Block(rec, colors[i], 1));


            if (startPositionX < GameLevel.BORDER_SIDE) {
               throw new RuntimeException("Blocks have gotten out of the left border block and will not be shown.");
            }
        }

        return blocks;
    }


}
