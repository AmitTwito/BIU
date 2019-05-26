package listeners;

import collidables.Block;
import animations.GameLevel;
import utilities.Counter;
import interfaces.HitListener;
import sprites.Ball;

/**
 * A listeners.BallRemover is in charge of removing ball from the game, as well as keeping count
 * of the number of balls that remain.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class BallRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter availableBalls;

    /**
     * A constructor for the listeners.BallRemover class.
     *
     * @param gameLevel           The game to remove balls from.
     * @param availableBalls The current available balls.
     */
    public BallRemover(GameLevel gameLevel, Counter availableBalls) {
        this.gameLevel = gameLevel;
        this.availableBalls = availableBalls;
    }


    /**
     * This method is called whenever the beingHit object is hit.
     * Removes the hitting ball.
     *
     * @param beingHit The object that being hit by a ball.
     * @param hitter   The sprites.Ball that's doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        availableBalls.decrease(1);
    }
}
