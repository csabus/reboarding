package hu.progmasters.reboarding.excpetion;

/**
 * Thrown by: methods of classes in {@code controllers} package
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found: " + id);
    }
}
