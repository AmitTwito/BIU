package tasks;

import animations.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.Task;
import utility.AnimationRunner;

/**
 * The ShowHiScoresTask class Represents a task for showing the scores table.
 *
 * @author Amit Twito
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private KeyboardSensor keyboardSensor;

    /**
     * A constructor for the ShowHiScoresTask class.
     *
     * @param runner              AnimationRunner.
     * @param highScoresAnimation HighScoresAnimation.
     * @param keyboardSensor      Keyboard.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, KeyboardSensor keyboardSensor) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.keyboardSensor = keyboardSensor;
    }

    /**
     * Runs the task.
     *
     * @return Returns the object of the T type.
     */
    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                this.highScoresAnimation));
        return null;
    }
}
