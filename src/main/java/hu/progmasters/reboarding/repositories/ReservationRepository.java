package hu.progmasters.reboarding.repositories;

import hu.progmasters.reboarding.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT r FROM hu.progmasters.reboarding.models.Reservation r where r.user_id = :id and r.date = :date")
    Optional<Reservation> findByDateAndUserId(@Param("id") Long id, @Param("date") LocalDate date);
}
