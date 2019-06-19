package animations;

import biuoop.DrawSurface;
import interfaces.Animation;


/**
 * The PauseScreen represents a pause screen animation object.
 *
 * @author Amit Twito
 */
public class PauseScreen implements Animation {
    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * Returns whether the animation has stopped.
     *
     * @return If the animation has been stopped.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}
