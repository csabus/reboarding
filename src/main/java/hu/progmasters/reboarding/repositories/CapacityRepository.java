package hu.progmasters.reboarding.repositories;

import hu.progmasters.reboarding.models.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    static final int MIN_CAPACITY = 0;
    static final int MAX_CAPACITY = 250;
    static final int DEFAULT_CAPACITY = 250;

    Optional<Capacity> findByDate(LocalDate date);

    Capacity getOneByDate(LocalDate date);
}
