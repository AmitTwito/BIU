package tasks;

import game.GameFlow;
import interfaces.LevelInformation;
import interfaces.Task;

import java.util.List;

/**
 * The StartLevelTask class Represents a task for staring a level.
 *
 * @author Amit Twito
 */
public class StartLevelTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levelInformations;

    /**
     * A constructor for the StartLevelTask class.
     *
     * @param gameFlow          GameFlow.
     * @param levelInformations List of level informations.
     */
    public StartLevelTask(GameFlow gameFlow, List<LevelInformation> levelInformations) {
        this.gameFlow = gameFlow;
        this.levelInformations = levelInformations;
    }

    /**
     * Runs the task.
     *
     * @return Returns the object of the T type.
     */
    @Override
    public Void run() {
        this.gameFlow.runLevels(this.levelInformations);
        return null;
    }
}
