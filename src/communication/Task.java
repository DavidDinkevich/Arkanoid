// ID: 584698174

package communication;

/**
 * Represents a generic runnable task.
 * @param <T> the return type of the task
 */
public interface Task<T> {
   /**
    * Execute the task.
    * @return the return value of the task
    */
   T run();
}
