package game;

import animations.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import interfaces.LevelInformation;
import utilities.Counter;

import java.util.List;


public class GameFlow {

    public static final int MAX_LIVES_NUMBER = 7;

    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;

    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui) {
        this.gui = gui;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(MAX_LIVES_NUMBER);
    }

    public void runLevels(List<LevelInformation> levels) {

        if (levels.size() != 0) {
            for (LevelInformation levelInfo : levels) {

                GameLevel level = new GameLevel(levelInfo, this.animationRunner,
                        this.keyboardSensor, this.livesCounter, this.scoreCounter);

                level.initialize();

                while (level.getRemainingBlocksNumber() != 0 && this.livesCounter.getValue() != 0) {
                    level.playOneTurn();
                }

                if (this.livesCounter.getValue() == 0) {
                    this.animationRunner.run(
                            new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                                    new GameOverAnimation(this.scoreCounter.getValue())));
                    if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                        this.gui.close();
                    }
                }

            }

            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                    new YouWinAnimation(this.scoreCounter.getValue())));
            if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                this.gui.close();
            }
        }

    }
}
