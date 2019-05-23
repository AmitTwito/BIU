package interfaces;

/**
 * The interfaces.HitNotifier interface indicate that objects that implement it send notifications when they are being hit.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public interface HitNotifier {

    /**
     * Adds a interfaces.HitListener as a listener to hit events.
     *
     * @param hl The interfaces.HitListener to add to the list of listeners.
     */
    void addHitListener(HitListener hl);


    /**
     * Removes hl from the list of listeners to hit events.
     *
     * @param hl The interfaces.HitListener to remove from the list of listeners list.
     */
    void removeHitListener(HitListener hl);
}