package level;

import animations.AnimationRunner;
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
import sun.security.tools.policytool.PolicyTool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FirstLevel implements LevelInformation {

    private static final int WIDTH = 50;

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

        LevelBackground levelBackground = new LevelBackground();

        Rectangle backgroundRectangle = new Rectangle(new Point(0, 0), AnimationRunner.GUI_WIDTH,
                AnimationRunner.GUI_HEIGHT);

        levelBackground.addSprite(new ColoredRectangle(backgroundRectangle, Color.BLACK));

        Point center = new Point(AnimationRunner.GUI_WIDTH / 2, AnimationRunner.GUI_HEIGHT / 4 + WIDTH / 2);
        Color color = new Color(0, 0, 179);
        int radius = (int) (AnimationRunner.GUI_HEIGHT / 3.3
                - GameLevel.INDICATORS_BLOCK_HEIGHT - GameLevel.BORDER_SIDE);
        for (int i = 1; i <= 3; i++) {
            ColoredCircle c = new ColoredCircle(center, radius, color, null);
            levelBackground.addSprite(c);
            radius = radius - 30;
        }

        radius = (int) (AnimationRunner.GUI_HEIGHT / 3.3
                - GameLevel.INDICATORS_BLOCK_HEIGHT - GameLevel.BORDER_SIDE);

        int distanceFromBlock = 8;
        Point start = new Point(center.getX(),
                GameLevel.INDICATORS_BLOCK_HEIGHT + GameLevel.BORDER_SIDE);
        Point end = new Point(center.getX(), center.getY() - WIDTH / 2 - distanceFromBlock);
        Line line = new Line(start, end);
        ColoredLine l = new ColoredLine(line, color);
        levelBackground.addSprite(l);

        start = new Point(center.getX() + distanceFromBlock + WIDTH / 2, center.getY());
        end = new Point(start.getX() + radius, center.getY());
        line = new Line(start, end);
        l = new ColoredLine(line, color);
        levelBackground.addSprite(l);

        start = new Point(center.getX() - radius - WIDTH / 2,
                center.getY());
        end = new Point(center.getX() - WIDTH / 2 - distanceFromBlock, center.getY());
        line = new Line(start, end);
        l = new ColoredLine(line, color);
        levelBackground.addSprite(l);

        start = new Point(center.getX(),
                center.getY() + WIDTH / 2 + distanceFromBlock);
        end = new Point(center.getX(), center.getY() + radius + distanceFromBlock);
        line = new Line(start, end);
        l = new ColoredLine(line, color);
        levelBackground.addSprite(l);


        return levelBackground;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();


        Point upperLeftPoint = new Point(AnimationRunner.GUI_WIDTH / 2 - WIDTH / 2,
                AnimationRunner.GUI_HEIGHT / 4);
        Rectangle rec = new Rectangle(upperLeftPoint, WIDTH, WIDTH);
        Block block = new Block(rec, Color.RED, 1);

        blocks.add(block);

        return blocks;
    }


}
