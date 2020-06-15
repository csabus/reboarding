package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.excpetion.ReservationNotFoundException;
import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/entry")
public class EntryController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    public Reservation update(@PathVariable Long userId) {
        LocalDate today = LocalDate.now();
        Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(userId, today);
        if (userRepository.findById(userId).isPresent()) {
            if (reservation.isPresent()) {
                reservation.get().setEnterTime(LocalDateTime.now());
                Reservation existingReservation = reservationRepository.getOne(reservation.get().getReservationId());
                BeanUtils.copyProperties(reservation, existingReservation);
                return reservationRepository.saveAndFlush(existingReservation);
            } else {
                throw new ReservationNotFoundException(userId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }

}
