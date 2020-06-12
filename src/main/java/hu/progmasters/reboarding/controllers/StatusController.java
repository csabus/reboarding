package hu.progmasters.reboarding.controllers;

import hu.progmasters.reboarding.ReservationStatus;
import hu.progmasters.reboarding.Status;
import hu.progmasters.reboarding.models.Reservation;
import hu.progmasters.reboarding.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/status")
public class StatusController {
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping()
    @RequestMapping("{date}/{id}")
    public ReservationStatus get(@PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @PathVariable("id") Long id) {
        Optional<Reservation> reservation = reservationRepository.findByDateAndUserId(id, date);
        if (reservation.isPresent()) {
            return new ReservationStatus(Status.ACCEPTED, reservation.get().getReservation_id());
        } else {
            return new ReservationStatus(Status.NOT_REGISTERED, 0);
        }
    }


}
