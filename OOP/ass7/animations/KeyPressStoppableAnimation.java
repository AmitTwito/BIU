package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

/**
 * The KeyPressStoppableAnimation represents a key press stoppable animation object.
 *
 * @author Amit Twito
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;
    private boolean running;
    private boolean isAlreadyPressed;

    /**
     * A constructor for the KeyPressStoppableAnimation.
     *
     * @param animation The animation to stop.
     * @param key       The key thats stops the animation.
     * @param sensor    The keyboard sensor.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.running = true;
    }

    /**
     * Does one frame of the animation.
     *
     * @param d The drawsurface.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);

        if (!this.keyboard.isPressed(key)) {
            this.isAlreadyPressed = false;
        }

        if (this.keyboard.isPressed(key) && !this.isAlreadyPressed) {
            this.running = false;
        }


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
