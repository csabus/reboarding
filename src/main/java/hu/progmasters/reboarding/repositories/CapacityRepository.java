package hu.progmasters.reboarding.repositories;

import hu.progmasters.reboarding.models.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    static final int MIN_CAPACITY = 0;
    static final int MAX_CAPACITY = 250;
    static final int DEFAULT_CAPACITY = 250;

    Capacity findByDate(LocalDate date);
}
