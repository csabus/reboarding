package hu.progmasters.reboarding.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "reservations")
@NamedStoredProcedureQuery(name = "Registration.getSTate",
        procedureName = "GET_RESERVATION_INDEX", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_id", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "day_date", type = LocalDate.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "state", type = Integer.class)})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservation_id;
    private Long user_id;
    @Column(name = "reservation_date", columnDefinition = "DATE")
    private LocalDate date;

    public Reservation() {
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
