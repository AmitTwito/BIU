package tasks;

import biuoop.KeyboardSensor;
import interfaces.Menu;
import interfaces.Task;
import utility.AnimationRunner;


/**
 * The ShowSubMenuTask class Represents a task for showing the submenu.
 *
 * @author Amit Twito
 */
public class ShowSubMenuTask implements Task<Void> {
    private AnimationRunner runner;
    private Menu<Task<Void>> subMenu;
    private KeyboardSensor keyboardSensor;

    /**
     * A constructor for the ShowSubMenuTask class.
     *
     * @param runner  Animation runner.
     * @param subMenu Sub menu animation.
     */
    public ShowSubMenuTask(AnimationRunner runner, Menu<Task<Void>> subMenu) {
        this.runner = runner;
        this.subMenu = subMenu;
    }

    /**
     * Runs the task.
     *
     * @return Returns the object of the T type.
     */
    @Override
    public Void run() {

        runner.run(this.subMenu);
        // wait for user selection
        Task<Void> task = subMenu.getStatus();
        task.run();
        return null;
    }
}
