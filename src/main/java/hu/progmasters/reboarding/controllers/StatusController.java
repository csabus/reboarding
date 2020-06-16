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

    /**
     * Determines if the user specified by {@code userId} can enter the office at the given time
     * @param date the current date
     * @param userId the {@code long} value used to specify the {@link hu.progmasters.reboarding.models.User}
     * @return a {@link ReservationStatus} object holding the {@link hu.progmasters.reboarding.Status} value
     * @throws ReservationNotFoundException if {@code User} does not have a {@link Reservation} for this day
     * @throws UserNotFoundException if {@code User} is not registered
     */
    @GetMapping(path = "{date}/{userId}")
    public ReservationStatus get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @PathVariable("userId") Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(userId, date);

            if (reservation.isPresent()) {
                List<Reservation> reservationList = reservationRepository.findAllOpenByDate(date);
                Capacity capacity = capacityRepository.getOneByDate(date);

                int reservationIndex = reservationList.indexOf(reservation.get());
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
