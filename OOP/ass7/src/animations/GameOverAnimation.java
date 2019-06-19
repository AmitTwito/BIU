package animations;

import biuoop.DrawSurface;
import interfaces.Animation;

/**
 * The GameOverAnimation represents a game over animation object.
 *
 * @author Amit Twito
 */
public class GameOverAnimation implements Animation {
    private int score;

    /**
     * A constructor for the GameOveAnimation.
     *
     * @param score The score.
     */
    public GameOverAnimation(int score) {
        this.score = score;
    }

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "Game Over :( Your score is: " + this.score, 32);
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
