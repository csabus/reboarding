package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.ReservationStatus;
import hu.progmasters.reboarding.excpetion.CapacityNotSetException;
import hu.progmasters.reboarding.excpetion.ReservationNotFoundException;
import hu.progmasters.reboarding.excpetion.UserNotFoundException;
import hu.progmasters.reboarding.models.Capacity;
import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.CapacityRepository;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import hu.progmasters.reboarding.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private CapacityRepository capacityRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationStatus create(@RequestBody final Reservation reservation) {
        if (userRepository.existsById(reservation.getUserId())) {
            Optional<Reservation> existingReservation = reservationRepository.findByDateAndUserId(reservation.getUserId(), reservation.getDate());
            if (existingReservation.isEmpty()) {
                Optional<Capacity> capacity = capacityRepository.findByDate(reservation.getDate());
                if (capacity.isPresent()) {
                    Reservation newReservation = new Reservation();
                    BeanUtils.copyProperties(reservation, newReservation, "reservationId");
                    reservationRepository.saveAndFlush(newReservation);
                } else {
                    throw new CapacityNotSetException(reservation.getDate());
                }
            }
            int index = reservationRepository.getUserReservationState(reservation.getUserId(), reservation.getDate());
            return new ReservationStatus(reservation.getDate(), index);
        } else {
            throw new UserNotFoundException(reservation.getUserId());
        }
    }

    @DeleteMapping(value = "{date}/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       @PathVariable("id") Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(userId, date);
            if (reservation.isPresent()) {
                reservationRepository.delete(reservation.get());
            } else {
                throw new ReservationNotFoundException(userId);
            }
        } else {
            throw new UserNotFoundException(userId);
        }
    }

}
