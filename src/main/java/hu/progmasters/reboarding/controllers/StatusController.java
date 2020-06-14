package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.ReservationStatus;
import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "{date}/{id}")
    public ReservationStatus get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @PathVariable("id") Long id) {
        if (userRepository.findById(id).isPresent()) {
            int index = reservationRepository.getUserReservationState(id, date);
            return new ReservationStatus(date, index);
        } else {
            throw new UserNotFoundException(id);
        }
    }


}
