package hu.progmasters.reboarding.error;

import hu.progmasters.reboarding.excpetion.CapacityNotSetException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CapacityNotSetAdvice {
    @ResponseBody
    @ExceptionHandler(CapacityNotSetException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String capacityNotSetHandler(CapacityNotSetException ex) {
        return ex.getMessage();
    }
}
