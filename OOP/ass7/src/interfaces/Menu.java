package interfaces;

/**
 * The menu interface represents menu objects.
 *
 * @param <T>
 * @author Amit Twito
 */
public interface Menu<T> extends Animation {
    /**
     * Adds a selection to the menu.
     *
     * @param key       Key to be pressed.
     * @param message   Message of the selection.
     * @param returnVal Return value of the selection.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Returns the current status of the menu.
     *
     * @return Current status.
     */
    T getStatus();

    /**
     * Adds a sub menu to the menu.
     *
     * @param key     Key to be pressed.
     * @param message Message of the submenu.
     * @param subMenu The submenu animation.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

}
