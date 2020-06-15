package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.ReservationStatus;
import hu.progmasters.reboarding.excpetion.ReservationNotFoundException;
import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.models.Capacity;
import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.CapacityRepository;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CapacityRepository capacityRepository;

    @GetMapping(path = "{date}/{userId}")
    public ReservationStatus get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @PathVariable("userId") Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(userId, date);
            if (reservation.isPresent()) {
                List<Reservation> reservationList = reservationRepository.findALlOpenByDate(date);
                int reservationIndex = reservationList.indexOf(reservation.get());
                Capacity capacity = capacityRepository.getOneByDate(date);
                long dailyCapacity = capacity.getDailyCapacity();
                if (reservationIndex < dailyCapacity) {
                    return new ReservationStatus(date, reservationIndex + 1);
                } else {
                    return new ReservationStatus(date, dailyCapacity - reservationIndex - 1);
                }
            } else {
                throw new ReservationNotFoundException(userId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }


}
