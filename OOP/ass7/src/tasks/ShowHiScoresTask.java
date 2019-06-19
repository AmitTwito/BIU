package tasks;

import animations.KeyPressStoppableAnimation;
import animations.MenuAnimation;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.Menu;
import interfaces.Task;
import utility.AnimationRunner;


public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    private KeyboardSensor keyboardSensor;

    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation, KeyboardSensor keyboardSensor) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
        this.keyboardSensor = keyboardSensor;
    }

    @Override
    public Void run() {
        this.runner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                this.highScoresAnimation));
        return null;
    }
}
