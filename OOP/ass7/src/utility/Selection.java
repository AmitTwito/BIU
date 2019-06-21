package utility;

/**
 * The Selection class represents a selection for the menu.
 *
 * @param <T> Type of returns value of the selection.
 * @author Amit Twito
 */
public class Selection<T> {

    private String key;
    private String message;
    private T returnVal;

    /**
     * A constructor for the Selection class.
     *
     * @param key       Key of selection.
     * @param message   Message.
     * @param returnVal Returning value.
     */
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * Returns the key.
     *
     * @return Selection's key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the message.
     *
     * @return Selection's message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the T value.
     *
     * @return The T value.
     */
    public T getReturnVal() {
        return returnVal;
    }
}
