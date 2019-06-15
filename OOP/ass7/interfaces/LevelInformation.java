package interfaces;

import collidables.Block;
import movement.Velocity;

import java.util.List;


/**
 * The interfaces.LevelInformation interface represents the information of the level..
 *
 * @author Amit Twito
 * @since 29.5.19
 */
public interface LevelInformation {

    /**
     * Returns the number of balls of the level.
     *
     * @return Number of balls of the level.
     */
    int numberOfBalls();

    // The initial velocity of each ball
    // Note that initialBallVelocities().size() == numberOfBalls()

    /**
     * Returns list of velocities.
     *
     * @return List of velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * Returns the paddle speed.
     *
     * @return Paddle speed.
     */
    int paddleSpeed();

    /**
     * Returns the paddle width.
     *
     * @return Paddle width.
     */
    int paddleWidth();

    /**
     * Returns the name of the level.
     *
     * @return Name of the level.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return Sprite with the background of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return List of blocks.
     */
    List<Block> blocks();

    // Number of levels that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();

    /**
     * Returns the number of blocks to remove.
     *
     * @return Number of blocks to remove.
     */
    int numberOfBlocksToRemove();
}
