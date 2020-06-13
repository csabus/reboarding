package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.ReservationRepository;
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

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Reservation update(@PathVariable Long id) {
        LocalDate today = LocalDate.now();
        Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(id, today);
        if (reservation.isPresent()) {
            reservation.get().setEnterTime(LocalDateTime.now());
            Reservation existingReservation = reservationRepository.getOne(reservation.get().getReservation_id());
            BeanUtils.copyProperties(reservation, existingReservation);
            return reservationRepository.saveAndFlush(existingReservation);
        }
        return null;
    }

}
