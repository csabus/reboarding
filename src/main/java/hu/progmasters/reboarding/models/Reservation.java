package hu.progmasters.reboarding.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "reservations")
/*@NamedStoredProcedureQuery(name = "Registration.getState",
        procedureName = "GET_RESERVATION_INDEX", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "u_id", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "day_date", type = LocalDate.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "state", type = Integer.class)})*/
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "reservation_date", columnDefinition = "DATE")
    private LocalDate date;
    @Column(name = "enter_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime enterTime;
    @Column(name = "exit_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime exitTime;

    public Reservation() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(LocalDateTime enterTime) {
        this.enterTime = enterTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }
}
