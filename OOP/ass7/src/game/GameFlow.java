package game;

import animations.*;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import interfaces.Animation;
import interfaces.LevelInformation;
import interfaces.Menu;
import interfaces.Task;
import score.HighScoresTable;
import score.ScoreInfo;

import utility.AnimationRunner;
import utility.Counter;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * The GameFlow class represents a game flow object.
 *
 * @author Amit Twito
 */
public class GameFlow {

    public static final int MAX_LIVES_NUMBER = 2;

    private GUI gui;
    private Menu<Task<Void>> menu;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable highScoresTable;
    private Animation highScores;


    /**
     * A constructor for the GameFlow class.
     *
     * @param ar         The animation runner.
     * @param ks         The keyboard.
     * @param gui        The gui.
     * @param table      high scores table.
     * @param highScores High scores table.
     * @param menu       Menu of the game.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table, Animation highScores,
                    Menu<Task<Void>> menu) {
        this.gui = gui;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(MAX_LIVES_NUMBER);
        this.highScoresTable = table;
        this.highScores = highScores;
        this.menu = menu;
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
                    if (this.highScoresTable.getRank(this.scoreCounter.getValue()) <= this.highScoresTable.size()) {
                        DialogManager dialog = gui.getDialogManager();
                        String name = dialog.showQuestionDialog("Enter Name", "What is your name?",
                                "Anonymous");
                        if (this.highScoresTable.getRank(this.scoreCounter.getValue()) == 1
                                || this.highScoresTable.getRank(this.scoreCounter.getValue()) ==
                                this.highScoresTable.size()) {
                            this.highScoresTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
                            File file = new File(HighScoresTable.FILE_NAME);
                            try {
                                this.highScoresTable.save(file);
                            } catch (IOException el) {
                                el.printStackTrace(System.err);
                            }
                        }
                    }


                    this.animationRunner.run(
                            new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                                    new GameOverAnimation(this.scoreCounter.getValue())));
                    if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                        resetGame();
                    }
                }

            }
            if (this.highScoresTable.getRank(this.scoreCounter.getValue()) <= this.highScoresTable.size()) {
                DialogManager dialog = gui.getDialogManager();
                String name = dialog.showQuestionDialog("Enter Name", "What is your name?", "Anonymous");
                if (this.highScoresTable.getRank(this.scoreCounter.getValue()) == 1
                        || this.highScoresTable.getRank(this.scoreCounter.getValue()) == this.highScoresTable.size()) {
                    this.highScoresTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
                    File file = new File(HighScoresTable.FILE_NAME);
                    try {
                        this.highScoresTable.save(file);
                    } catch (IOException el) {
                        el.printStackTrace(System.err);
                    }
                }
            }
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, this.keyboardSensor.SPACE_KEY,
                    new YouWinAnimation(this.scoreCounter.getValue())));
            if (this.keyboardSensor.isPressed(KeyboardSensor.SPACE_KEY)) {
                resetGame();
            }
        }
    }

    /**
     * Resets the game.
     */
    private void resetGame() {

        this.scoreCounter.decrease(this.scoreCounter.getValue());
        this.livesCounter.decrease(this.livesCounter.getValue());
        this.livesCounter.increase(MAX_LIVES_NUMBER);

        while (true) {
            this.animationRunner.run(this.menu);
            // wait for user selection
            Task<Void> task = this.menu.getStatus();
            task.run();
        }
    }
}
