package hu.progmasters.reboarding;

public class ReservationStatus {
    private Status status;
    private long number;

    public ReservationStatus(Status status, long number) {
        this.status = status;
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
