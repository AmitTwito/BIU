package game;

import animations.GameOverAnimation;
import animations.GameLevel;
import animations.KeyPressStoppableAnimation;
import animations.YouWinAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import interfaces.LevelInformation;
import score.HighScoresTable;
import score.ScoreInfo;
import utility.AnimationRunner;
import utility.Counter;

import java.util.List;

/**
 * The GameFlow class represents a game flow object.
 *
 * @author Amit Twito
 */
public class GameFlow {

    public static final int MAX_LIVES_NUMBER = 7;

    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable highScoresTable;

    /**
     * A constructor for the GameFlow class.
     *
     * @param ar    The animation runner.
     * @param ks    The keyboard.
     * @param gui   The gui.
     * @param table high scores table.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table) {
        this.gui = gui;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(MAX_LIVES_NUMBER);
        this.highScoresTable = table;
    }

    /**
     * Runs the given list of levels.
     *
     * @param levels List of levels to run.
     */
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
					if(this.highScoresTable.getRank(this.scoreCounter.getValue()) <= this.highScoresTable.size()) {
						DialogManager dialog = gui.getDialogManager();
						String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "Anonymous");
						this.highScoresTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
					}


                    this.animationRunner.run(
                            new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                                    new GameOverAnimation(this.scoreCounter.getValue())));
                    if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                        this.gui.close();
                    }
                }

            }
			if(this.highScoresTable.getRank(this.scoreCounter.getValue()) <= this.highScoresTable.size()) {
				DialogManager dialog = gui.getDialogManager();
				String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "Anonymous");
				this.highScoresTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
			}
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                    new YouWinAnimation(this.scoreCounter.getValue())));
            if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                this.gui.close();
            }
        }

    }
}
