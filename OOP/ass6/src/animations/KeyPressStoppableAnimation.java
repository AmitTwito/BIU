package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import interfaces.Animation;

public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;
    private boolean running;
    private boolean isAlreadyPressed;


    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.keyboard = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.running = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
		this.animation.doOneFrame(d);

		if (this.keyboard.isPressed(key)) {
			this.running = false;
		}
	}

    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}
