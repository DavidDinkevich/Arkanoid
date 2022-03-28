// ID: 584698174

package game;

/**
 * Basic class that contains an integer value that may be increased
 * and decreased.
 * @author David Dinkevich
 */
public class Counter {
    /** The value of the counter. */
    private int value;

    /**
     * Constructs a new Counter with the given initial value.
     * @param num the initial value
     */
    public Counter(int num) {
        value = num;
    }

    /**
     * Adds a value to this Counter.
     * @param number the value to add to this counter
     */
    public void increase(int number) {
        value += number;
    }

    /**
     * Subtracts a value from this Counter.
     * @param number the value to subtract from this counter
     */
    public void decrease(int number) {
        value -= number;
    }

    /**
     * Returns the current value of this Counter.
     * @return the current value of this Counter
     */
    public int getValue() {
        return value;
    }
}