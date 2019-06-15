package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.SpriteCollection;
import interfaces.Animation;
import utility.AnimationRunner;

import java.awt.Color;
import java.util.concurrent.TimeUnit;


/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 *
 * @author Amit Twito
 */
public class CountdownAnimation implements Animation {
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean running;
    private long millisecondsPerCount;

    /**
     * A constructor for the CountdownAnimation.
     *
     * @param numOfSeconds The number of seconds.
     * @param countFrom    The counting from number.
     * @param gameScreen   The game screen of sprites.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.running = true;
        this.millisecondsPerCount = TimeUnit.SECONDS.toMillis((long) numOfSeconds) / countFrom;
    }

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        gameScreen.drawAllOn(d);
        d.setColor(Color.BLACK);
		d.drawText(d.getWidth() / 2 - 20, AnimationRunner.GUI_HEIGHT / 2, "" + this.countFrom, 80);
		d.setColor(Color.RED);
        d.drawText(d.getWidth() / 2 - 18, AnimationRunner.GUI_HEIGHT / 2 - 2, "" + this.countFrom, 70);

        Sleeper sleeper = new Sleeper();

        if (this.countFrom == 0) {
            this.running = false;
        }
        if (this.countFrom < GameLevel.COUNT_FROM) {
            sleeper.sleepFor(this.millisecondsPerCount);
        }
        this.countFrom = this.countFrom - 1;

    }

    /**
     * Returns whether the animation has stopped.
     *
     * @return If the animation has been stopped.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}
