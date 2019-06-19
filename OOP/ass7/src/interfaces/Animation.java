package interfaces;

import biuoop.DrawSurface;

/**
 * The interfaces.Animation interface represents every animations.
 *
 * @author Amit Twito
 * @since 29.5.19
 */
public interface Animation {

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    void doOneFrame(DrawSurface d);

    /**
     * Returns whether the animation has stopped.
     *
     * @return If the animation has been stopped.
     */
    boolean shouldStop();
}
