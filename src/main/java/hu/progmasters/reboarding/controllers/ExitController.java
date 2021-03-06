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
@RequestMapping("/api/v1/exit")
public class ExitController {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Sets the {@code exitTime} field of the {@link Reservation} (belonging to this {@code User } at the current date) to the current time.
     * Returns the updated {@code Reservation}.
     * Throws an exception if {@code User} or {@code Reservation} not found.
     * @param userId the {@code long} value specifying this user.
     * @return the {@code Reservation} updated with the {@code exitTime} timestamp.
     * @throws ReservationNotFoundException
     * @throws UserNotFoundException
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.PUT)
    public Reservation update(@PathVariable Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            LocalDate today = LocalDate.now();
            Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(userId, today);

            if (reservation.isPresent()) {
                reservation.get().setExitTime(LocalDateTime.now());
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
