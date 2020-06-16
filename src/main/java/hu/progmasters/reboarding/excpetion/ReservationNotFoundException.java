package hu.progmasters.reboarding.excpetion;
/**
 * Thrown by: methods of classes in {@code controllers} package
 */
public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("Reservation not found: " + id);
    }
}
