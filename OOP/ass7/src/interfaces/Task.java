package interfaces;

/**
 * The Task interface represents task objects.
 *
 * @param <T> Type of Task.
 */
public interface Task<T> {
    /**
     * Runs the task.
     *
     * @return Returns the object of the T type.
     */
    T run();
}
