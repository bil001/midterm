package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int resId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Host host;
    private BigDecimal total;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "resId=" + resId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", guest=" + guest +
                ", host=" + host +
                ", total=" + total +
                '}';
    }
}
