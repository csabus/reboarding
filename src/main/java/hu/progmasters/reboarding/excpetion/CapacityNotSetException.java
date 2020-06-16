package hu.progmasters.reboarding.excpetion;

import java.time.LocalDate;
/**
 * Thrown by: methods of classes in {@code controllers} package
 */
public class CapacityNotSetException extends RuntimeException {
    public CapacityNotSetException(LocalDate date) {
        super("Office capacity not set: " + date);
    }
}
