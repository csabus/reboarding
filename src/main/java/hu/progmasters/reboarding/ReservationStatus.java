package hu.progmasters.reboarding;

import java.time.LocalDate;

public class ReservationStatus {
    private Status status;
    private long index;
    private LocalDate date;

    public ReservationStatus(LocalDate date, long index) {
        this.date = date;
        this.index = index;
        if (index < 0) {
            this.status = Status.WAITING;
        } else if (index > 0) {
            this.status = Status.ACCEPTED;
        } else {
            this.status = Status.NOT_REGISTERED;
        }
        if (date == null) {
            this.status = Status.MISSING_CAPACITY;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getIndex() {
        return index >= 0 ? index : -index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
