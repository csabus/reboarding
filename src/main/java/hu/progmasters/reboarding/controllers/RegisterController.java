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
import java.util.List;
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

    @GetMapping
    public List<Reservation> list() {
        return reservationRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationStatus create(@RequestBody final Reservation reservation) {
        if (userRepository.existsById(reservation.getUserId())) {
            LocalDate date = reservation.getDate();
            Long userId = reservation.getUserId();
            Optional<Capacity> capacity = capacityRepository.findByDate(reservation.getDate());

            if (capacity.isPresent()) {
                long dailyCapacity = capacity.get().getDailyCapacity();
                Optional<Reservation> existingReservation = reservationRepository.findByDateAndUserId(userId, date);
                Reservation newReservation;

                if (existingReservation.isEmpty()) {
                    newReservation = new Reservation();
                    BeanUtils.copyProperties(reservation, newReservation, "reservationId");
                    reservationRepository.saveAndFlush(newReservation);
                } else {
                    newReservation = existingReservation.get();
                }

                List<Reservation> reservationList = reservationRepository.findAllOpenByDate(date);
                int reservationIndex = reservationList.indexOf(newReservation);

                if (reservationIndex < dailyCapacity) {
                    return new ReservationStatus(date, reservationIndex + 1);
                } else {
                    return new ReservationStatus(date, dailyCapacity - reservationIndex - 1);
                }
            } else {
                throw new CapacityNotSetException(reservation.getDate());
            }
        } else {
            throw new UserNotFoundException(reservation.getUserId());
        }
    }

    @DeleteMapping(value = "{date}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       @PathVariable("userId") Long userId) {
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
