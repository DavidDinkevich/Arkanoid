// ID: 584698174

package communication;

/**
 * Allows HitListeners to "sign up" for notifications
 * whenever a collision occurs--and updates each one of them by calling
 * their hitEvent() function.
 * @author David Dinkevich
 */
public interface HitNotifier {
    /**
     * Adds a HitListener to the list of listeners that will be
     * updated on hit events.
     * @param hl the HitListener to be added
     */
    void addHitListener(HitListener hl);
    /**
     * Removes a HitListener from the list of listeners that will be
     * updated on hit events.
     * @param hl the HitListener to be removed
     */
    void removeHitListener(HitListener hl);
}
