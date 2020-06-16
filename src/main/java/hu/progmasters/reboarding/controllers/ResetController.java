package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.repositories.CapacityRepository;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reset")
public class ResetController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CapacityRepository capacityRepository;

    /**
     * Clears all data from tables
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void delete() {
        reservationRepository.deleteAll();
        userRepository.deleteAll();
        capacityRepository.deleteAll();
    }

}
