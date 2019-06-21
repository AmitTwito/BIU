package tasks;


import interfaces.Task;

/**
 * The ExitTask class Represents a task for exiting the game.
 *
 * @author Amit Twito
 */
public class ExitTask implements Task<Void> {
    /**
     * Runs the task.
     *
     * @return Returns the object of the T type.
     */
    @Override
    public Void run() {
        System.exit(0);
        return null;
    }
}
