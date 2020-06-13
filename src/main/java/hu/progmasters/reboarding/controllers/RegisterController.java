package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.ReservationStatus;
import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping
    public ReservationStatus create(@RequestBody final Reservation reservation) {
        if (userRepository.existsById(reservation.getUser_id())) {
            Optional<Reservation> existingReservation = reservationRepository.findByDateAndUserId(reservation.getUser_id(), reservation.getDate());
            if (existingReservation.isEmpty()) {
                Reservation newReservation = new Reservation();
                BeanUtils.copyProperties(reservation, newReservation, "reservation_id");
                reservationRepository.saveAndFlush(newReservation);
            }
            int index = reservationRepository.getUserReservationState(reservation.getUser_id(), reservation.getDate());
            return new ReservationStatus(reservation.getDate(), index);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    @DeleteMapping(value = "{date}/{id}")
    public void delete(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       @PathVariable("id") Long userId) {
        Optional<Reservation> existingReservation = reservationRepository.findByDateAndUserId(userId, date);
        existingReservation.ifPresent(reservation -> reservationRepository.delete(reservation));
    }

}
