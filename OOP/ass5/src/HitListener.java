/**
 * The HitListener interface represents objects that want to be notified of hit events.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit The object that being hit by a ball.
     * @param hitter   The Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}