package hu.progmasters.reboarding.excpetion;

import java.time.LocalDate;

public class CapacityNotSetException extends RuntimeException {
    public CapacityNotSetException(LocalDate date) {
        super("Office capacity not set: " + date);
    }
}
