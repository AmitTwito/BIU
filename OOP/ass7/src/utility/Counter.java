package utility;

/**
 * The utility.Counter class is a simple class that is used for counting things.
 *
 * @author Amit Twito
 * @since 15.5.19
 */
public class Counter {

    private int count;

    /**
     * A constructor for the utility.Counter class.
     */
    public Counter() {
        this.count = 0;
    }

    /**
     * Adds a given number to current count.
     *
     * @param number The number to add to the count.
     */
    public void increase(int number) {
        this.count += number;
    }

    /**
     * Decreases the current count by a given number.
     *
     * @param number The number to decrease the count by.
     */
    public void decrease(int number) {
        this.count -= number;
    }

    /**
     * Returns the current count.
     *
     * @return Current count.
     */
    public int getValue() {
        return this.count;
    }
}