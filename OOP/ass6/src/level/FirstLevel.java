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

public class FirstLevel implements LevelInformation {

    private int numberOfBalls;
    private int paddleWidth;
    private String levelName;

    public FirstLevel() {
        this.numberOfBalls = 1;
        this.paddleWidth = GameLevel.PADDLE_WIDTH;
        this.levelName = "Direct Hit";
    }

    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        velocities.add(Velocity.fromAngleAndSpeed(0, GameLevel.BALL_SPEED));

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

        return new ColoredRectangle(backgroundRectangle, Color.BLACK);    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        double width = 50;
        Point upperLeftPoint = new Point(AnimationRunner.GUI_WIDTH / 2 - width / 2,
                AnimationRunner.GUI_HEIGHT / 4);
        Rectangle rec = new Rectangle(upperLeftPoint, width, width);
        Block block = new Block(rec, Color.RED, 1);

        blocks.add(block);

        return blocks;
    }


}
