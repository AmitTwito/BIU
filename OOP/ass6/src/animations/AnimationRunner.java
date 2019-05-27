package animations;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import interfaces.Animation;

public class AnimationRunner {

    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECONDS = 1000;
    public static final String GUI_TITLE = "Arkanoid";
    public static final int GUI_WIDTH = 800;
    public static final int GUI_HEIGHT = 600;

    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private KeyboardSensor keyboard;

    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.keyboard = this.gui.getKeyboardSensor();
        this.framesPerSecond = FRAMES_PER_SECOND;
        this.sleeper = new Sleeper();
    }

    public void run(Animation animation) {
        int millisecondsPerFrame = MILLISECONDS / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d);

            this.gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}