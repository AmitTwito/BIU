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
import tasks.ExitTask;
import tasks.ShowHiScoresTask;
import tasks.StartGameTask;
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

    public static final int MAX_LIVES_NUMBER = 7;

    private GUI gui;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable highScoresTable;
    private List<LevelInformation> levelInformations;
    private Animation highScores;

    /**
     * A constructor for the GameFlow class.
     *
     * @param ar                The animation runner.
     * @param ks                The keyboard.
     * @param gui               The gui.
     * @param table             high scores table.
     * @param levelInformations level informations.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table,
                    List<LevelInformation> levelInformations, Animation highScores) {
        this.gui = gui;
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.scoreCounter = new Counter();
        this.livesCounter = new Counter();
        this.livesCounter.increase(MAX_LIVES_NUMBER);
        this.highScoresTable = table;
        this.levelInformations = levelInformations;
        this.highScores = highScores;
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
                        this.highScoresTable.add(new ScoreInfo(name, this.scoreCounter.getValue()));
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
                if(this.highScoresTable.getRank(this.scoreCounter.getValue()) == 1
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

    private void resetGame() {

        this.scoreCounter.decrease(this.scoreCounter.getValue());
        this.livesCounter.decrease(this.livesCounter.getValue());
        this.livesCounter.increase(MAX_LIVES_NUMBER);


        while (true) {
            Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", gui.getKeyboardSensor());

            menu.addSelection("s", "Start Game", new StartGameTask(this, this.levelInformations));
            menu.addSelection("h", "High Scores", new ShowHiScoresTask(this.animationRunner,
                    this.highScores, gui.getKeyboardSensor()));
            menu.addSelection("q", "Exit", new ExitTask());            this.animationRunner.run(menu);

            this.animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
}
