package listeners;

import collidables.Block;
import animations.GameLevel;
import sprites.Ball;
import utility.Counter;
import interfaces.HitListener;

/**
 * A listeners.BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;


    /**
     * A constructor for the listeners.BlockRemover class.
     *
     * @param gameLevel       The game to remove the blocks from.
     * @param remainingBlocks The counter of remaining blocks in the game.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }


    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.

    /**
     * This method is called whenever the beingHit object is hit.
     * Removes Blocks that are hit and reach 0 hit-points, in other words,
     * when they have 1 point before the hit.
     *
     * @param beingHit The object that being hit by a ball.
     * @param hitter   The sprites.Ball that's doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (!beingHit.isBlockWithoutHitPoints() && beingHit.getHitPoints() == 1) {
            beingHit.removeFromGame(this.gameLevel);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}