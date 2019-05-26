package game;

import animations.AnimationRunner;
import animations.GameLevel;
import biuoop.KeyboardSensor;
import interfaces.LevelInformation;
import utilities.Counter;

import java.util.List;


public class GameFlow {

    public static final int MAX_LIVES_NUMBER = 7;


    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;

    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(MAX_LIVES_NUMBER);
    }

    public void runLevels(List<LevelInformation> levels) {


        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.animationRunner,
					this.keyboardSensor, this.livesCounter, this.scoreCounter);

            level.initialize();

            while (level.getRemainingBlocks() != 0 && this.livesCounter.getValue() != 0){
                level.playOneTurn();
            }

            if (this.livesCounter.getValue() == 0){
                break;
            }

        }
    }
}
