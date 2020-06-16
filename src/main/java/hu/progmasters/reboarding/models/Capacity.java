package hu.progmasters.reboarding.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Represents the number of employees allowed to be at the office at the same time on a given day.
 */
@Entity(name = "capacity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Capacity {
    @Id
    @Column(name = "day", columnDefinition = "DATE")
    private LocalDate date;
    @Column(name = "capacity")
    private Long dailyCapacity;

    public Capacity() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getDailyCapacity() {
        return dailyCapacity;
    }

    public void setDailyCapacity(Long dailyCapacity) {
        this.dailyCapacity = dailyCapacity;
    }
}
