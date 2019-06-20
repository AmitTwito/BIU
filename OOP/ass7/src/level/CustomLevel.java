package level;

import collidables.Block;
import factory.BlocksFromSymbolsFactory;
import interfaces.LevelInformation;
import interfaces.Sprite;
import movement.Velocity;
import sprites.LevelBackground;

import java.util.List;

public class CustomLevel implements LevelInformation {
    private String levelName;
    private List<Velocity> velocities;
    private String backgroundImage;
    private int paddleSpeed;
    private int paddleWidth;
	private BlocksFromSymbolsFactory blocksFromSymbolsFactory;
    public CustomLevel(String levelName, List<Velocity> ballVelocities, String backgroundImage,
                       int paddleSpeed, int paddleWidth, BlocksFromSymbolsFactory blocksFromSymbolsFactory) {
        this.levelName = levelName;
        this.velocities = ballVelocities;
        this.backgroundImage = backgroundImage;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
		this.blocksFromSymbolsFactory = blocksFromSymbolsFactory;


    }

    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
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
        return new LevelBackground();
    }

    @Override
    public List<Block> blocks() {
        return null;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 0;
    }
}
