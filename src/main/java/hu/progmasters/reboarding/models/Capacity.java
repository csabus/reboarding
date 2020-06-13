package hu.progmasters.reboarding.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "capacity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Capacity {
    @Id
    @Column(name = "day", columnDefinition = "DATE")
    private LocalDate date;
    private Long capacity;

    public Capacity() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
}
