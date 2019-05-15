/**
 * The ScoreTrackingListener class represents a HitListener that tracking the score,
 * and adding score by hitting objects.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;


    /**
     * A constructor fo the ScoreTrackingListener class.
     *
     * @param scoreCounter The score counter of the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * On hit, the score is being increased by 5, when the points of the block are 0 - additional 10.
     *
     * @param beingHit The object that being hit by a ball.
     * @param hitter   The Ball that's doing the hitting.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() > 1) {
            this.currentScore.increase(5);
        } else {
            this.currentScore.increase(15);
            beingHit.removeHitListener(this);
        }
    }
}